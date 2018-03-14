package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {
    final static int TEST_CUSTOMER_ID = 1;
    final static String TEST_CUSTOMER_NAME = "Customer";
    final static String TEST_CUSTOMER_ADDRESS = "Kraków, ?ojasiewicza";

    @Test
    public void shouldReturnSameAsInput() {
        Customer c = new Customer(TEST_CUSTOMER_ID, TEST_CUSTOMER_NAME, TEST_CUSTOMER_ADDRESS);

        Assert.assertEquals(TEST_CUSTOMER_ID, c.getId());
        Assert.assertEquals(TEST_CUSTOMER_NAME, c.getName());
        Assert.assertEquals(TEST_CUSTOMER_ADDRESS, c.getAddress());
    }
}
