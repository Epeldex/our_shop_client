package logic.factories;

import logic.business.SupplierManagerImplementation;
import logic.interfaces.SupplierManager;

/**
 * Factory class for providing a singleton instance of SupplierManager. Utilizes
 * the Singleton design pattern for global and consistent access.
 */
public class SupplierManagerFactory {

    private static SupplierManager obj = new SupplierManagerImplementation();

    /**
     * Returns the singleton instance of SupplierManager.
     *
     * @return Singleton instance of SupplierManager.
     */
    public static SupplierManager getInstance() {
        return obj;
    }
}
