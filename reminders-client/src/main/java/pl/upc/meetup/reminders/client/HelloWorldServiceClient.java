package pl.upc.meetup.reminders.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.upc.meetup.reminders.model.Saying;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.sun.jersey.core.util.MultivaluedMapImpl;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;

public class HelloWorldServiceClient {
    private static String helloWorldService =
            "http://localhost:8082/";
    private static String helloWorldResource =
            "hello-world";
    
    public static String hello(String name) {
        Saying saying = null;
        ClientConfig config = new DefaultClientConfig();
        
        Client c = Client.create(config);
        
        WebResource r = c.resource(helloWorldService);
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        
        params.add("name", name);
        
        String response = r.
                path(helloWorldResource).
                queryParams(params).
                get(String.class);
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            saying = mapper.readValue(response, Saying.class);
        } catch (IOException ex) {
            Logger.getLogger(HelloWorldServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (saying != null) {
            return saying.getContent();
        } else {
            return null;
        }
    }
    
    public static void main(String args[]) {
        String response = hello("Patryk Najda");
        System.out.println(response);
    }
}
