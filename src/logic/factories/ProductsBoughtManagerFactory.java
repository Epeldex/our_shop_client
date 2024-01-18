/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.factories;

import logic.business.ProductsBoughtManagerImplementation;
import logic.interfaces.ProductsBoughtManager;

/**
 *
 * @author alexa
 */
public class ProductsBoughtManagerFactory {
        private static ProductsBoughtManager obj = new ProductsBoughtManagerImplementation();

    public static ProductsBoughtManager getInstance() {
        return obj;
    }
    
}
