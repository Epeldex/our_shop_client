package logic.interfaces;

import java.util.List;
import logic.exceptions.LogicException;
import transfer.objects.Supplier;

/**
 * EJB Local Interface for managing Supplier entity CRUD operations.
 *
 * @author Alexander Epelde
 */
public interface SupplierManager {

    /**
     * Updates a supplier's information in the underlying application storage.
     *
     * @param supplier The {@link Supplier} object containing the updated
     * information.
     * @throws LogicException If there is any exception during processing.
     */
    public void updateSupplier(Supplier supplier) throws LogicException;

    /**
     * Deletes a supplier from the underlying application storage.
     *
     * @param supplierId The ID of the supplier to be deleted.
     * @throws LogicException If there is any exception during processing.
     */
    public void deleteSupplier(Integer supplierId) throws LogicException;

    /**
     * Retrieves a list of all suppliers from the application data storage.
     *
     * @return A List of {@link Supplier} objects.
     * @throws LogicException If there is any exception during processing.
     */
    public List<Supplier> selectAllSuppliers() throws LogicException;

    /**
     * Retrieves a supplier by its ID from the application data storage.
     *
     * @param supplierId The ID of the supplier to be retrieved.
     * @return The {@link Supplier} object containing supplier data.
     * @throws LogicException If there is any exception during processing.
     */
    public Supplier selectSupplierById(Integer supplierId) throws LogicException;

    /**
     * Inserts a new supplier into the underlying application storage.
     *
     * @param supplier The {@link Supplier} object containing the details of the
     * new supplier.
     * @throws LogicException If there is any exception during processing.
     */
    public void insertSupplier(Supplier supplier) throws LogicException;

}
