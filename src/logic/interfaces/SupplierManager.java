package logic.interfaces;

import java.util.List;
import logic.exceptions.LogicException;
import transfer.objects.Supplier;

/**
 * Interface for managing {@link Supplier} . This interface defines methods for
 * updating, deleting, retrieving, and inserting suppliers in the underlying
 * application storage.
 *
 * @author Alexander Epelde
 */
public interface SupplierManager {

    /**
     * Updates a supplier's information in the underlying application storage.
     *
     * @param supplier The {@link Supplier} object containing the updated
     * information.
     * @throws LogicException If there is any exception during the update
     * process.
     */
    public void updateSupplier(Supplier supplier) throws LogicException;

    /**
     * Deletes a supplier from the underlying application storage.
     *
     * @param supplierId The ID of the supplier to be deleted.
     * @throws LogicException If there is any exception during the delete
     * process.
     */
    public void deleteSupplier(Integer supplierId) throws LogicException;

    /**
     * Retrieves a list of all suppliers from the application data storage.
     *
     * @return A List of {@link Supplier} objects.
     * @throws LogicException If there is any exception during the retrieval
     * process.
     */
    public List<Supplier> selectAllSuppliers() throws LogicException;

    /**
     * Retrieves a supplier by its ID from the application data storage.
     *
     * @param supplierId The ID of the supplier to be retrieved.
     * @return The {@link Supplier} object containing supplier data.
     * @throws LogicException If there is any exception during the retrieval
     * process.
     */
    public Supplier selectSupplierById(Integer supplierId) throws LogicException;

    /**
     * Inserts a new supplier into the underlying application storage.
     *
     * @param supplier The {@link Supplier} object containing the details of the
     * new supplier.
     * @throws LogicException If there is any exception during the insertion
     * process.
     */
    public void insertSupplier(Supplier supplier) throws LogicException;

}
