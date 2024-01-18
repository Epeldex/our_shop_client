/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.business;

import logic.factories.*;
import logic.interfaces.*;

/**
 *
 * @author alexa
 */
public class EntityManager {

    public static AdminManager adminManager;
    public static CustomerManager customerManager;
    public static ProductManager productManager;
    public static ProductsBoughtManager productsBoughtManager;
    public static SupplierManager supplierManager;
    public static TagManager tagManager;
    public static UserManager userManager;

    public EntityManager() {
        adminManager = AdminManagerFactory.getInstance();
        customerManager = CustomerManagerFactory.getInstance();
        productManager = ProductManagerFactory.getInstance();
        productsBoughtManager = ProductsBoughtManagerFactory.getInstance();
        supplierManager = SupplierManagerFactory.getInstance();
        tagManager = TagManagerFactory.getInstance();
        userManager = UserManagerFactory.getInstance();
    }
}
