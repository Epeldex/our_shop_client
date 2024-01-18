package rest;

import java.text.MessageFormat;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import transfer.objects.Product;

/**
 * Jersey REST client generated for REST resource: SupplierREST [suppliers]<br>
 * USAGE:
 * <pre>
 *        SupplierRESTClient client = new SupplierRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * This class provides a Jersey REST client for interacting with a RESTful web
 * service that deals with supplier entities. It extends GenericRESTClient,
 * which is assumed to contain common functionalities for REST clients.
 *
 * The web service is expected to be running on a Glassfish Server, and the
 * client is designed to perform CRUD (Create, Read, Update, Delete) operations
 * on supplier entities.
 *
 * @author Alexander Epelde
 */
public class SupplierRESTClient extends GenericRESTClient {

    /**
     * Constructs a new SupplierRESTClient. It creates a RESTful client and
     * establishes the path of the WebTarget object associated with the client,
     * pointing to the "suppliers" resource.
     */
    public SupplierRESTClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("suppliers");
    }

    /**
     * Retrieves a supplier entity's XML representation from the web service
     * based on the supplier's ID.
     *
     * @param responseType The Class object of the returning instance.
     * @param id The ID of the supplier entity to be retrieved.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T find(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Creates a new supplier entity by sending a POST request to the web
     * service with the provided entity data.
     *
     * @param requestEntity The object containing data to be created.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void create(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Product.class);
    }

    /**
     * Updates an existing supplier entity by sending a PUT request to the web
     * service with the updated entity data.
     *
     * @param requestEntity The object containing data to be updated.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void update(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Product.class);
    }

    /**
     * Deletes a supplier entity based on its ID by sending a DELETE request to
     * the web service.
     *
     * @param id The ID of the supplier entity to be deleted.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void delete(String id) throws WebApplicationException {
        webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Product.class);
    }

    /**
     * Retrieves a list of supplier entities' XML representation from the web
     * service.
     *
     * @param responseType The GenericType object of the returning instance.
     * @return A generic type, normally a list, containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T findAll(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }
}
