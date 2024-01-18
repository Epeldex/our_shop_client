package logic.business;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import logic.exceptions.LogicException;
import logic.interfaces.SupplierManager;
import rest.SupplierRESTClient;
import transfer.objects.Supplier;

/**
 * This class implements {@link SupplierManager} business logic interface using
 * a RESTful web client to access business logic in a Java EE application
 * server.
 *
 * @author Alexander Epelde
 */
public class SupplierManagerImplementation implements SupplierManager {

    // REST supplier web client
    private SupplierRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("your.package.name");

    /**
     * Create a SupplierManagerImplementation object. It constructs a web client
     * for accessing a RESTful service that provides business logic in an
     * application server.
     */
    public SupplierManagerImplementation() {
        webClient = new SupplierRESTClient();
    }

    /**
     * Updates an existing supplier by sending a PUT request to the RESTful web
     * service.
     *
     * @param supplier The {@link Supplier} object to be updated.
     * @throws LogicException If there is any error while processing the update
     * operation.
     */
    @Override
    public void updateSupplier(Supplier supplier) throws LogicException {
        try {
            LOGGER.log(Level.INFO, "SupplierManager: Updating supplier {0}.", supplier.getSupplier_id());
            webClient.update(supplier);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception updating supplier, {0}", ex.getMessage());
            throw new LogicException("Error updating supplier:\n" + ex.getMessage());
        }
    }

    /**
     * Deletes an existing supplier by sending a DELETE request to the RESTful
     * web service.
     *
     * @param supplierId The ID of the supplier to be deleted.
     * @throws LogicException If there is any error while processing the delete
     * operation.
     */
    @Override
    public void deleteSupplier(Integer supplierId) throws LogicException {
        try {
            LOGGER.log(Level.INFO, "SupplierManager: Deleting supplier {0}.", supplierId);
            webClient.delete(String.valueOf(supplierId));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception deleting supplier, {0}", ex.getMessage());
            throw new LogicException("Error deleting supplier:\n" + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of all suppliers by sending a GET request to the RESTful
     * web service.
     *
     * @return List of {@link Supplier} objects representing all suppliers.
     * @throws LogicException If there is any error while processing the select
     * operation.
     */
    @Override
    public List<Supplier> selectAllSuppliers() throws LogicException {
        List<Supplier> suppliers = null;
        try {
            LOGGER.info("SupplierManager: Finding all suppliers from REST service (XML).");
            // Ask webClient for all suppliers' data.
            suppliers = webClient.findAll(new GenericType<List<Supplier>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception finding all suppliers, {0}", ex.getMessage());
            throw new LogicException("Error finding all suppliers:\n" + ex.getMessage());
        }
        return suppliers;
    }

    /**
     * Retrieves supplier information by ID by sending a GET request to the
     * RESTful web service.
     *
     * @param supplierId The ID of the supplier to retrieve.
     * @return The {@link Supplier} object representing the supplier with the
     * specified ID.
     * @throws LogicException If there is any error while processing the select
     * operation.
     */
    @Override
    public Supplier selectSupplierById(Integer supplierId) throws LogicException {
        Supplier supplier = null;
        try {
            LOGGER.log(Level.INFO, "SupplierManager: Finding supplier by ID {0} from REST service (XML).", supplierId);
            // Ask webClient for supplier data by ID.
            supplier = webClient.find(Supplier.class, String.valueOf(supplierId));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception finding supplier by ID, {0}", ex.getMessage());
            throw new LogicException("Error finding supplier by ID:\n" + ex.getMessage());
        }
        return supplier;
    }

    /**
     * Inserts a new supplier by sending a POST request to the RESTful web
     * service.
     *
     * @param supplier The {@link Supplier} object to be inserted.
     * @throws LogicException If there is any error while processing the insert
     * operation.
     */
    @Override
    public void insertSupplier(Supplier supplier) throws LogicException {
        try {
            LOGGER.log(Level.INFO, "SupplierManager: Inserting new supplier {0}.", supplier.getSupplier_id());
            // Send supplier data to web client for creation.
            webClient.create(supplier);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "SupplierManager: Exception inserting supplier, {0}", ex.getMessage());
            throw new LogicException("Error inserting supplier:\n" + ex.getMessage());
        }
    }
}
