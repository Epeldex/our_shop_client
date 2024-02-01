package logic.factories;

import logic.business.UserManagerImplementation;
import logic.interfaces.UserManager;

/**
 * Factory class for providing a singleton instance of UserManager. Implements
 * the Singleton pattern to maintain a single, global instance used throughout
 * the application.
 */
public class UserManagerFactory {

    private static UserManager obj = new UserManagerImplementation();

    /**
     * Retrieves the singleton instance of UserManager.
     *
     * @return Singleton instance of UserManager.
     */
    public static UserManager getInstance() {
        return obj;
    }
}
