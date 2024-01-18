package rest;

import java.text.MessageFormat;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import transfer.objects.User;

/**
 * Jersey REST client generated for REST resource: UserREST [users]<br>
 * USAGE:
 * <pre>
 * UserRESTClient client = new UserRESTClient();
 * Object response = client.XXX(...);
 * // do whatever with response
 * client.close();
 * </pre>
 *
 * This class provides a Jersey REST client for interacting with a RESTful web
 * service that deals with user entities. It extends GenericRESTClient, which is
 * assumed to contain common functionalities for REST clients.
 *
 * The web service is expected to be running on a Glassfish Server, and the
 * client is designed to perform various operations on user entities, including
 * user authentication and CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Daniel Barrios
 */
public class UserRESTClient extends GenericRESTClient {

    /**
     * Constructs a new UserRESTClient. It creates a RESTful client and
     * establishes the path of the WebTarget object associated with the client,
     * pointing to the "users" resource.
     */
    public UserRESTClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("users");
    }

    /**
     * Requests the symmetric key from the web service.
     *
     * @return The symmetric key as a String.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public String requestSymmetricKey() throws WebApplicationException {

        WebTarget resource = webTarget;
        resource = resource.path("key");
        return resource.request(MediaType.APPLICATION_XML).get(String.class);
    }

    /**
     * Retrieves a user entity's XML representation from the web service based
     * on the user's ID.
     *
     * @param responseType The Class object of the returning instance.
     * @param id The ID of the user entity to be retrieved.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T findUserById(Class<T> responseType, String id) throws WebApplicationException {

        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Retrieves a user entity's XML representation from the web service based
     * on the user's username.
     *
     * @param responseType The Class object of the returning instance.
     * @param username The username of the user entity to be retrieved.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T findUserByUsername(Class<T> responseType, String username) throws WebApplicationException {

        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("username/{0}", new Object[]{username}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }
    
    /**
     * Retrieves a list of all user entities' XML representation from the web
     * service.
     *
     * @param responseType The Class object of the returning instance.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T findAllUsers(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Creates a new user entity by sending a POST request to the web service
     * with the provided entity data.
     *
     * @param requestEntity The object containing data to be created.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void createUser(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), User.class);
    }

    /**
     * Updates an existing user entity by sending a PUT request to the web
     * service with the updated entity data.
     *
     * @param requestEntity The object containing data to be updated.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void updateUser(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), User.class);
    }

    /**
     * Deletes a user entity based on its ID by sending a DELETE request to the
     * web service.
     *
     * @param id The ID of the user entity to be deleted.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void deleteUser(String id) throws WebApplicationException {
        webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(User.class);
    }

    /**
     * Authenticates a user by sending a POST request to the web service with
     * the provided entity data.
     *
     * @param requestEntity The object containing data for user authentication.
     * @param responseType The Class object of the returning instance.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T signIn(Object requestEntity, Class<T> responseType) throws WebApplicationException {
        return webTarget.path("signin").request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), responseType);
    }

    /**
     * Retrieves a list of user entities' XML representation from the web
     * service based on the user's active status.
     *
     * @param responseType The Class object of the returning instance.
     * @param active The active status of the user entities to be retrieved.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T findUserByActive(GenericType<T> responseType, String active) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("active/{0}", new Object[]{active}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);

    }
}
