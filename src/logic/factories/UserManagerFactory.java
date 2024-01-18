/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.factories;

import logic.business.UserManagerImplementation;
import logic.interfaces.UserManager;

/**
 *
 * @author alexa
 */
public class UserManagerFactory {

    private static UserManager obj = new UserManagerImplementation();

    public static UserManager getInstance() {
        return obj;
    }

}
