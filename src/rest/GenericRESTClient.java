/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author alexa
 */
public class GenericRESTClient {

    protected static final String BASE_URI = ResourceBundle.getBundle("config/parameters").getString("URI");

    protected WebTarget webTarget;
    protected Client client;

    public void close() {
        client.close();
    }
}
