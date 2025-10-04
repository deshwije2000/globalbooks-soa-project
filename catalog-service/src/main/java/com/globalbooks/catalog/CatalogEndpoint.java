package com.globalbooks.catalog;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="CatalogService", portName="CatalogPort", targetNamespace="http://globalbooks.com/catalog")
public class CatalogEndpoint {

    @WebMethod
    public PriceResponse lookupPrice(String isbn) {
        
        PriceResponse response = new PriceResponse();
        response.setIsbn(isbn);
        response.setPrice(19.99); 
        return response;
    }
}