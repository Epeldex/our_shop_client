package logic.factories;

import logic.business.ProductManagerImplementation;
import logic.interfaces.ProductManager;

/**
 * ProductManagerFactory is a factory class used for obtaining instances of
 * ProductManager. It employs the Singleton design pattern to ensure that only a
 * single instance of ProductManager is created and reused across the
 * application.
 *
 * This design choice centralizes the management of ProductManager instances,
 * providing a consistent approach and efficient resource utilization.
 *
 */
public class ProductManagerFactory {

    private static ProductManager obj = new ProductManagerImplementation();

    /**
     * Retrieves the singleton instance of ProductManager. This method
     * guarantees that the same instance of ProductManager is returned on every
     * call, in line with the Singleton design pattern. This ensures a
     * consistent and centralized management of the ProductManager instance.
     *
     * @return the singleton instance of ProductManager.
     */
    public static ProductManager getInstance() {
        return obj;
    }

}
