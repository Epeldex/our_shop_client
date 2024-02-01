package logic.factories;

import logic.business.AdminManagerImplementation;
import logic.interfaces.AdminManager;

/**
 * AdminManagerFactory is a factory class used for obtaining instances of
 * AdminManager. This class follows the Singleton design pattern to ensure that
 * only one instance of AdminManager is created and reused throughout the
 * application.
 *
 * The class provides a static method to get the instance of AdminManager. This
 * approach centralizes the management of AdminManager instances, promoting
 * consistency and efficiency in resource usage.
 *
 */
public class AdminManagerFactory {

    private static AdminManager obj = new AdminManagerImplementation();

    /**
     * Retrieves the singleton instance of AdminManager. This method ensures
     * that the same instance of AdminManager is returned for every call,
     * aligning with the Singleton design pattern.
     *
     * @return the singleton instance of AdminManager.
     */
    public static AdminManager getInstance() {
        return obj;
    }

}
