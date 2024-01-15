package logic.interfaces;

import exceptions.UpdateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.CreateException;
import entities.Supplier;

import javax.ejb.Local;
import java.util.List;

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
     * @throws UpdateException If there is any exception during processing.
     */
    public void updateSupplier(Supplier supplier) throws UpdateException;

    /**
     * Deletes a supplier from the underlying application storage.
     *
     * @param supplierId The ID of the supplier to be deleted.
     * @throws DeleteException If there is any exception during processing.
     */
    public void deleteSupplier(Integer supplierId) throws DeleteException;

    /**
     * Retrieves a list of all suppliers from the application data storage.
     *
     * @return A List of {@link Supplier} objects.
     * @throws ReadException If there is any exception during processing.
     */
    public List<Supplier> selectAllSuppliers() throws ReadException;

    /**
     * Retrieves a supplier by its ID from the application data storage.
     *
     * @param supplierId The ID of the supplier to be retrieved.
     * @return The {@link Supplier} object containing supplier data.
     * @throws ReadException If there is any exception during processing.
     */
    public Supplier selectSupplierById(Integer supplierId) throws ReadException;

    /**
     * Inserts a new supplier into the underlying application storage.
     *
     * @param supplier The {@link Supplier} object containing the details of the
     * new supplier.
     * @throws CreateException If there is any exception during processing.
     */
    public void insertSupplier(Supplier supplier) throws CreateException;

}
