import Services.LibraryServiceImpl;

import javax.xml.ws.Endpoint;

public class LibraryWebServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/library", new LibraryServiceImpl());
        System.out.println("Library SOAP Web Service is running at http://localhost:8080/library?wsdl");
    }
}
