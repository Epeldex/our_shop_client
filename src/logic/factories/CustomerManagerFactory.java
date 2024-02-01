package logic.factories;

import logic.business.CustomerManagerImplementation;
import logic.interfaces.CustomerManager;

/**
 * CustomerManagerFactory is a factory class for obtaining instances of
 * CustomerManager. It implements the Singleton design pattern to ensure only
 * one instance of CustomerManager is created and used throughout the
 * application.
 *
 * This centralized approach for instance management helps in maintaining a
 * consistent state and efficient resource usage for CustomerManager instances.
 */
public class CustomerManagerFactory {

    private static CustomerManager obj = new CustomerManagerImplementation();

    /**
     * Retrieves the singleton instance of CustomerManager. This static method
     * provides a global point of access to the CustomerManager and ensures that
     * only one instance of CustomerManager is created and used across the
     * application.
     *
     * @return the singleton instance of CustomerManager.
     */
    public static CustomerManager getInstance() {
        return obj;
    }
}
