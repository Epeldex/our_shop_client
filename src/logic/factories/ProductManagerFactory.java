/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.factories;

import logic.business.ProductManagerImplementation;
import logic.interfaces.ProductManager;

/**
 *
 * @author alexa
 */
public class ProductManagerFactory {

    private static ProductManager obj = new ProductManagerImplementation();

    public static ProductManager getInstance() {
        return obj;
    }

}
