package rest;

import java.text.MessageFormat;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import transfer.objects.ProductsBought;

/**
 * Jersey REST client generated for REST resource: ProductsBoughtREST
 *
 * This class provides a Jersey REST client for interacting with a RESTful web
 * service that deals with products bought entities. It extends
 * GenericRESTClient, which is assumed to contain common functionalities for
 * REST clients.
 *
 * The web service is expected to be running on a Glassfish Server, and the
 * client is designed to perform operations related to products bought entities.
 *
 * @author Alex Irusta
 */
public class ProductsBoughtRESTClient extends GenericRESTClient {

    /**
     * Constructs a new ProductsBoughtRESTClient. It creates a RESTful client
     * and establishes the path of the WebTarget object associated with the
     * client, pointing to the "productsBought" resource.
     */
    public ProductsBoughtRESTClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("productsBought");
    }

    /**
     * Purchases a product by sending a POST request to the web service with the
     * provided entity data.
     *
     * @param requestEntity The object containing data for the product purchase.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void purchaseProduct(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), ProductsBought.class);
    }

    /**
     * Retrieves products bought by a customer based on the customer's ID.
     *
     * @param responseType The Class object of the returning instance.
     * @param customerId The ID of the customer for whom the products were
     * bought.
     * @return The object containing the data.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public <T> T getProductsBought(GenericType<T> responseType, String customerId) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{customerId}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Updates the amount of a product bought by sending a PUT request to the
     * web service with the updated entity data.
     *
     * @param requestEntity The object containing data to be updated.
     * @throws WebApplicationException If there is an error while processing.
     * The error is wrapped in an HTTP error response.
     */
    public void updateAmount(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), ProductsBought.class);
    }
}
