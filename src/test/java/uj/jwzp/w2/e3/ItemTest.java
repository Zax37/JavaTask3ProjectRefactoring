package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ItemTest {
    final static String TEST_ITEM_NAME = "i";
    final static BigDecimal TEST_ITEM_PRICE = BigDecimal.valueOf(3);

    @Test
    public void shouldReturnSameAsInput() {
        Item i = new Item(TEST_ITEM_NAME, TEST_ITEM_PRICE);

        Assert.assertEquals(TEST_ITEM_NAME, i.getName());
        Assert.assertEquals(TEST_ITEM_PRICE, i.getPrice());
    }
}
