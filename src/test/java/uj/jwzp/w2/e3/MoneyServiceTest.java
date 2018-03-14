package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class MoneyServiceTest {
    @Mock
    private PersistenceLayer persistenceLayer;

    @Mock
    private DiscountService discountService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    final static int TEST_CUSTOMER_ID = 1;
    final static String TEST_CUSTOMER_NAME = "Customer";
    final static String TEST_CUSTOMER_ADDRESS = "Kraków, ?ojasiewicza";

    final static String TEST_ITEM_NAME = "i";
    final static BigDecimal TEST_ITEM_PRICE = BigDecimal.valueOf(3);;

    @Test
    public void notSell() {
        //given
        MoneyService uut = new MoneyService(persistenceLayer, new DiscountService());
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item(TEST_ITEM_NAME, TEST_ITEM_PRICE);
        Customer c = new Customer(TEST_CUSTOMER_ID, TEST_CUSTOMER_NAME, TEST_CUSTOMER_ADDRESS);

        //when
        boolean sold = uut.sell(i, 7, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.getMoney(c));
    }

    @Test
    public void sell() {
        //given
        MoneyService uut = new MoneyService(persistenceLayer, new DiscountService());
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item(TEST_ITEM_NAME, TEST_ITEM_PRICE);
        Customer c = new Customer(TEST_CUSTOMER_ID, TEST_CUSTOMER_NAME, TEST_CUSTOMER_ADDRESS);

        //when
        boolean sold = uut.sell(i, 1, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.getMoney(c));
    }

    @Test
    public void sellALot() {
        //given
        MoneyService uut = new MoneyService(persistenceLayer, discountService);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item(TEST_ITEM_NAME, TEST_ITEM_PRICE);
        Customer c = new Customer(TEST_CUSTOMER_ID, TEST_CUSTOMER_NAME, TEST_CUSTOMER_ADDRESS);
        uut.addMoney(c, new BigDecimal(990));

        Mockito.when(discountService.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Mockito.when(discountService.getDiscountFor(i, c)).thenReturn(BigDecimal.ZERO);

        //when
        boolean sold = uut.sell(i, 10, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(970), uut.getMoney(c));
    }
}
