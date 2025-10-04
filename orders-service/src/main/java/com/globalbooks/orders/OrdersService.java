package com.globalbooks.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.javalin.Javalin;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OrdersService {
    // A simple in-memory map to store orders
    private static final Map<String, Order> orders = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Define the names for our queues
    private static final String PAYMENTS_QUEUE_NAME = "payments_queue";
    private static final String SHIPPING_QUEUE_NAME = "shipping_queue";

    public static void main(String[] args) throws Exception {
        // 1. Establish connection to RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 2. Declare the queues to ensure they exist
        channel.queueDeclare(PAYMENTS_QUEUE_NAME, false, false, false, null);
        channel.queueDeclare(SHIPPING_QUEUE_NAME, false, false, false, null);

        // 3. Start the Javalin web server
        Javalin app = Javalin.create().start(7070);

        // Endpoint to create a new order: POST /orders
        app.post("/orders", ctx -> {
            try {
                Order newOrderRequest = objectMapper.readValue(ctx.body(), Order.class);
                String newOrderId = UUID.randomUUID().toString();
                Order confirmedOrder = new Order(newOrderId, newOrderRequest.customerId(), newOrderRequest.items());

                orders.put(newOrderId, confirmedOrder);
                System.out.println(" [x] Order created: " + newOrderId);

                // 4. Publish messages to RabbitMQ after creating the order
                String paymentMessage = "{\"orderId\": \"" + newOrderId + "\"}";
                channel.basicPublish("", PAYMENTS_QUEUE_NAME, null, paymentMessage.getBytes("UTF-8"));
                System.out.println(" [x] Sent payment instruction for order: '" + newOrderId + "'");

                String shippingMessage = "{\"orderId\": \"" + newOrderId + "\"}";
                channel.basicPublish("", SHIPPING_QUEUE_NAME, null, shippingMessage.getBytes("UTF-8"));
                System.out.println(" [x] Sent shipping instruction for order: '" + newOrderId + "'");

                ctx.json(confirmedOrder);
                ctx.status(201);
            } catch (Exception e) {
                ctx.status(400).result("Invalid JSON format for order.");
                e.printStackTrace();
            }
        });

        // Endpoint to get an order by its ID: GET /orders/{id}
        app.get("/orders/{id}", ctx -> {
            String orderId = ctx.pathParam("id");
            Order order = orders.get(orderId);

            if (order != null) {
                ctx.json(order);
            } else {
                ctx.status(404).result("Order not found.");
            }
        });
    }
}