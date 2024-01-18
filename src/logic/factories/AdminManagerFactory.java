package logic.factories;

import logic.business.AdminManagerImplementation;
import logic.interfaces.AdminManager;

/**
 *
 * @author alexa
 */
public class AdminManagerFactory {

    private static AdminManager obj = new AdminManagerImplementation();

    public static AdminManager getInstance() {
        return obj;
    }

}
