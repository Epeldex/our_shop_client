package rest;

import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * A generic REST client providing a base for interacting with RESTful web
 * services.
 *
 * This class serves as a foundation for creating specialized REST clients by
 * defining the base URI, managing the WebTarget, and closing the client
 * connection. Subclasses can extend this class to build specific REST clients
 * for different services.
 *
 * @author Alex Epelde
 */
public class GenericRESTClient {

    /**
     * The base URI for the RESTful web service, obtained from the
     * "config/parameters" ResourceBundle. It is a key-value pair where the key
     * is "URI."
     */
    protected static final String BASE_URI = ResourceBundle.getBundle("config/parameters").getString("URI");

    /**
     * The WebTarget object associated with the RESTful client. Subclasses can
     * use this to construct specific paths for their services.
     */
    protected WebTarget webTarget;

    /**
     * The RESTful client used to make requests to the web service.
     */
    protected Client client;

    /**
     * Closes the RESTful client. Should be called when the client is no longer
     * needed.
     */
    public void close() {
        client.close();
    }
}
