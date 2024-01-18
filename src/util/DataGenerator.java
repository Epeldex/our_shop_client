/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import logic.encryption.EncriptionManager;
import logic.encryption.EncriptionManagerFactory;
import transfer.objects.*;
import rest.*;

/**
 *
 * @author alexa
 */
public class DataGenerator {

    private static final EncriptionManager em = EncriptionManagerFactory.getInstance();

    public static List<Customer> getRandomCustomerList() throws Exception {
        List<Customer> customers = new ArrayList();
        for (int i = 1; i < 10; i++) {
            Customer customer = new Customer();
            customer.setUsername("customer" + new Random().nextInt(10000));
            customer.setPassword(Base64.getEncoder().encodeToString(em.encryptMessage(em.hashMessage("abcd*1234"))));
            customer.setActive(true);
            customer.setUserType(UserType.CUSTOMER);
            customer.setBalance(new Random().nextDouble() * i * new Random().nextInt(2048));
            customer.setCity("city" + i);
            customer.setFullName(customer.getUsername());
            customer.setPhone(buildRandomPhone());
            customer.setPostalCode(new Random().nextInt(49999));
            customer.setStreet("street" + i);
            customer.setEmail(customer.getUsername() + "@gmail.com");
            customers.add(customer);
        }
        return customers;
    }

    private static String buildRandomPhone() {
        String phone = new String();
        for (int i = 0; i < 9; i++) {
            Integer j = new Random().nextInt(9);
            phone = phone + j.toString();
        }
        return phone;
    }
}
