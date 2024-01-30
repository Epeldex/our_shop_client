package rest;

import java.text.MessageFormat;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import transfer.objects.Customer;

/**
 * Jersey REST client generated for REST resource: CustomerREST [customers]<br>
 * USAGE:
 * <pre>
 *        CustomerRESTClient client = new CustomerRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * This class provides a Jersey REST client for interacting with a RESTful web
 * service that deals with customer entities. It extends GenericRESTClient,
 * which is assumed to contain common functionalities for REST clients.
 *
 * The web service is expected to be running on a Glassfish Server, and the
 * client is designed to perform CRUD (Create, Read, Update, Delete) operations
 * on customer entities.
 *
 * @author Alex Irusta
 */
public class CustomerRESTClient extends GenericRESTClient {

    /**
     * Constructs a new CustomerRESTClient. It creates a RESTful client and
     * establishes the path of the WebTarget object associated with the client,
     * pointing to the "customers" resource.
     */
    public CustomerRESTClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("customers");
    }

    /**
     * Updates personal information for a customer entity by sending a PUT
     * request to the web service with the provided entity data.
     *
     * @param requestEntity The object containing data to be updated.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void updatePersonalInfo(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Customer.class);
    }

    /**
     * Inserts a new customer entity by sending a POST request to the web
     * service with the provided entity data.
     *
     * @param requestEntity The object containing data to be created.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void insertCustomer(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Customer.class);
    }

    /**
     * Deletes a customer entity based on its ID by sending a DELETE request to
     * the web service.
     *
     * @param id The ID of the customer entity to be deleted.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void deleteCustomer(String id) throws WebApplicationException {
        webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Customer.class);
    }

    /**
     * Retrieves a customer entity's XML representation from the web service
     * based on the customer's ID.
     *
     * @param responseType The Class object of the returning instance.
     * @param userId The ID of the customer entity to be retrieved.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T getCustomer(Class<T> responseType, String userId) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{userId}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Manages the password recovery management
     *
     * @param responseType The Class object of the returning instance.
     * @param email The email of the customer entity's password to reset.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T resetPassword(Class<T> responseType, String email) throws WebApplicationException {
        WebTarget resource = webTarget;

        resource = resource.path(MessageFormat.format("customer/{0}", new Object[]{email}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);

    }
}
