/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import logic.exceptions.LogicException;
import logic.factories.SupplierManagerFactory;
import logic.factories.TagManagerFactory;
import transfer.objects.*;

/**
 *
 * @author alexa
 */
public class DataGenerator {

    public static Customer getRandomCustomer() {
        Customer customer = new Customer();
        customer.setUsername("customer" + new Random().nextInt(10000));
        customer.setPassword("abcd*1234");
        customer.setActive(true);
        customer.setUserType(UserType.CUSTOMER);
        customer.setBalance(new Random().nextDouble() * new Random().nextInt(10000));
        customer.setCity("city" + new Random().nextInt(1000));
        customer.setFullName(customer.getUsername());
        customer.setPhone(buildRandomPhone());
        customer.setPostalCode(new Random().nextInt(49999));
        customer.setStreet("street" + new Random().nextInt(1000));
        customer.setEmail(customer.getUsername() + "@gmail.com");
        return customer;
    }

    public static List<Customer> getRandomCustomerList() {
        List<Customer> customers = new ArrayList();
        for (int i = 1; i < 10; i++) {
            customers.add(getRandomCustomer());
        }
        return customers;
    }

    public static Supplier getRandomSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("supplier" + new Random().nextInt(1000));
        supplier.setPhone(buildRandomPhone());
        supplier.setCountry("country" + new Random().nextInt(100));
        supplier.setZip(new Random().nextInt(99999));
        supplier.setCreateTimestamp(new Date());
        return supplier;
    }


    public static Product getRandomProduct() throws LogicException {
        Product product = new Product();
        product.setProductNumber("PN" + new Random().nextInt());
        product.setBrand("brand" + new Random().nextInt(1000));
        product.setCreateTimestamp(new Date(new Random().nextLong() % (new Date(120, 11, 31).getTime() - new Date(90, 0, 1).getTime()) + new Date(90, 0, 1).getTime()));
        product.setModel("model" + new Random().nextInt(1000));
        product.setOtherInfo("Additional information for " + product.getProductNumber());
        product.setWeight(new Random().nextDouble() * new Random().nextInt(1000));
        product.setPrice(new Random().nextDouble() * new Random().nextInt(10000));

        List<Supplier> suppliers = SupplierManagerFactory.getInstance().selectAllSuppliers();
        product.setSupplier(suppliers.get(new Random().nextInt(suppliers.size())));
        List<Tag> tags = TagManagerFactory.getInstance().selectAllTags();
        product.setTag(tags.get(new Random().nextInt(tags.size())));
        product.setDescription(product.toString());

        return product;
    }

    private static String buildRandomPhone() {
        StringBuilder phone = new StringBuilder("+");

        for (int i = 0; i < 11; i++) {
            Integer j = new Random().nextInt(10); // Cambiado a 10 para incluir el 0
            phone.append(j.toString());
        }

        return phone.toString();
    }

}
