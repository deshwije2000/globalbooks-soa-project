package com.globalbooks.payments;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class PaymentsService {
    // Define the name of the queue we will be listening to
    private final static String QUEUE_NAME = "payments_queue";

    public static void main(String[] argv) throws Exception {
        // 1. Set up the connection to the RabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("rabbitmq");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 2. Declare the queue
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for payment messages. To exit press CTRL+C");

        // 3. Define the callback for what to do when a message is received
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received payment instruction: '" + message + "'");

            
        };

        // 4. Start consuming messages from the queue
        
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}