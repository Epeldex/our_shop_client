package rest;

import java.text.MessageFormat;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import transfer.objects.Admin;

/**
 * Jersey REST client generated for REST resource:AdminREST [admins]<br>
 * USAGE:
 * <pre>
 *        AdminRESTClient client = new AdminRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * This class provides methods to interact with a RESTful web service that
 * manages admin entities.
 *
 * @author Daniel Barrios
 */
public class AdminRESTClient extends GenericRESTClient {

    /**
     * Constructs an instance of AdminRESTClient. It creates a RESTful web
     * client and establishes the path of the WebTarget object associated with
     * the client.
     */
    public AdminRESTClient() {
        // Create RESTful client
        client = ClientBuilder.newClient();
        // Establish the path of the WebTarget object associated with the client
        webTarget = client.target(BASE_URI).path("admins");
    }

    /**
     * Creates an admin entity by sending a request to the admin RESTful web
     * service.
     *
     * @param requestEntity The object containing data to be created.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void createAdmin(Object requestEntity) throws WebApplicationException {
        // Make request to create an admin entity
        webTarget.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity,
                        MediaType.APPLICATION_XML), Admin.class);
    }

    /**
     * Signs in an admin by sending a request to the admin RESTful web service.
     *
     * @param requestEntity The object containing data for sign-in.
     * @param responseType The Class object of the returning instance.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T signIn(Object requestEntity, Class<T> responseType) throws WebApplicationException {
        return webTarget.path("signin")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity,
                        MediaType.APPLICATION_XML), responseType);
    }

    /**
     * Removes an admin entity by sending a request to the admin RESTful web
     * service.
     *
     * @param id The id of the admin entity to be removed.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void removeAdmin(String id) throws WebApplicationException {
        // Make request to remove an admin entity
        webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Admin.class);
    }

    /**
     * Updates the last access time of an admin entity by sending a request to
     * the admin RESTful web service.
     *
     * @param requestEntity The object containing data to be updated.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void updateAdmin(Object requestEntity) throws WebApplicationException {
        // Make request to update the last access time of an admin entity
        webTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(requestEntity,
                        MediaType.APPLICATION_XML), Admin.class);
    }
}
