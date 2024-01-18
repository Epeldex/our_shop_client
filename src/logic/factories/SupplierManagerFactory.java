/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.factories;

import logic.business.SupplierManagerImplementation;
import logic.interfaces.SupplierManager;

/**
 *
 * @author alexa
 */
public class SupplierManagerFactory {

    private static SupplierManager obj = new SupplierManagerImplementation();

    public static SupplierManager getInstance() {
        return obj;
    }

}
