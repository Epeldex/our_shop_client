/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.factories;

import logic.business.CustomerManagerImplementation;
import logic.interfaces.CustomerManager;

/**
 *
 * @author alexa
 */
public class CustomerManagerFactory {
        private static CustomerManager obj = new CustomerManagerImplementation();

    public static CustomerManager getInstance() {
        return obj;
    }
}
