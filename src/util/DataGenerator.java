package util;

import logic.exceptions.LogicException;
import logic.factories.SupplierManagerFactory;
import logic.factories.TagManagerFactory;
import transfer.objects.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Utility class for generating random data objects for testing purposes.
 */
public class DataGenerator {

    private static final Random random = new Random();

    private static final String CUSTOMER_USERNAME_PATTERN = "customer";
    private static final String CITY_PATTERN = "city";
    private static final String STREET_PATTERN = "street";

    /**
     * Generates a random Customer object.
     *
     * @return A randomly generated Customer object.
     */
    public static Customer getRandomCustomer() {
        Customer customer = new Customer();
        customer.setUsername(CUSTOMER_USERNAME_PATTERN + random.nextInt(10000));
        customer.setPassword("abcd*1234");
        customer.setActive(true);
        customer.setUserType(UserType.CUSTOMER);
        customer.setBalance(random.nextDouble() * random.nextInt(10000));
        customer.setCity(CITY_PATTERN + random.nextInt(1000));
        customer.setFullName(customer.getUsername());
        customer.setPhone(buildRandomPhone());
        customer.setPostalCode(random.nextInt(49999));
        customer.setStreet(STREET_PATTERN + random.nextInt(1000));
        customer.setEmail(customer.getUsername() + "@gmail.com");
        return customer;
    }

    /**
     * Generates a list of random Customer objects.
     *
     * @return A list of randomly generated Customer objects.
     */
    public static List<Customer> getRandomCustomerList() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            customers.add(getRandomCustomer());
        }
        return customers;
    }

    /**
     * Generates a random Supplier object.
     *
     * @return A randomly generated Supplier object.
     */
    public static Supplier getRandomSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("supplier" + random.nextInt(1000));
        supplier.setPhone(buildRandomPhone());
        supplier.setCountry("country" + random.nextInt(100));
        supplier.setZip(random.nextInt(99999));
        supplier.setCreateTimestamp(new Date());
        return supplier;
    }

    /**
     * Generates a random Product object.
     *
     * @return A randomly generated Product object.
     */
    public static Product getRandomProduct() {
        try {
            Product product = new Product();
            product.setProductNumber("PN" + random.nextInt());
            product.setBrand("brand" + random.nextInt(1000));
            product.setCreateTimestamp(new Date(random.nextLong() % (new Date(120, 11, 31).getTime() - new Date(90, 0, 1).getTime()) + new Date(90, 0, 1).getTime()));
            product.setModel("model" + random.nextInt(1000));
            product.setOtherInfo("Additional information for " + product.getProductNumber());
            product.setWeight(random.nextDouble() * random.nextInt(1000));
            product.setPrice(random.nextDouble() * random.nextInt(10000));

            List<Supplier> suppliers = SupplierManagerFactory.getInstance().selectAllSuppliers();
            product.setSupplier(suppliers.get(random.nextInt(suppliers.size())));
            List<Tag> tags = TagManagerFactory.getInstance().selectAllTags();
            product.setTag(tags.get(random.nextInt(tags.size())));
            product.setDescription(product.toString());

            return product;
        } catch (LogicException e) {
            // Handle or log the exception
            return null;
        }
    }

    private static String buildRandomPhone() {
        StringBuilder phone = new StringBuilder("+");

        for (int i = 0; i < 11; i++) {
            Integer j = random.nextInt(10); // Changed to 10 to include 0
            phone.append(j.toString());
        }

        return phone.toString();
    }
}
