package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;
import java.util.HashMap;

public class MoneyService {
    private HashMap<Customer, BigDecimal> balances = new HashMap<>();
    private final PersistenceLayer persistenceLayer;
    private final DiscountService discountService;
    private final static BigDecimal INITIAL_BALANCE = BigDecimal.TEN;

    public MoneyService(PersistenceLayer persistenceLayer, DiscountService discountService) {
        this.persistenceLayer = persistenceLayer;
        this.discountService = discountService;
    }

    public BigDecimal getMoney(Customer customer) {
        if (balances.containsKey(customer)) {
            return balances.get(customer);
        } else {
            balances.put(customer, INITIAL_BALANCE);
            persistenceLayer.saveCustomer(customer);
            return balances.get(customer);
        }
    }

    public boolean pay(Customer customer, BigDecimal amount) {
        BigDecimal money = getMoney(customer);
        if (money.compareTo(amount) >= 0) {
            balances.put(customer, money.subtract(amount));
            persistenceLayer.saveCustomer(customer);
            return true;
        }
        return false;
    }

    public void addMoney(Customer customer, BigDecimal amount) {
        BigDecimal money = getMoney(customer);
        persistenceLayer.saveCustomer(customer);
        balances.put(customer, money.add(amount));
    }

    public boolean sell(Item item, int quantity, Customer customer) {
        BigDecimal money = getMoney(customer);
        BigDecimal price = item.getPrice().subtract(
                discountService.getDiscountFor(item, customer)).multiply(BigDecimal.valueOf(quantity)
        );
        if (discountService.isWeekendPromotion() && price.compareTo(BigDecimal.valueOf(5)) > 0) {
            price = price.subtract(BigDecimal.valueOf(3));
        }
        boolean sold = pay(customer, price);
        if (sold) {
            return persistenceLayer.saveTransaction(customer, item, quantity);
        }
        return false;
    }
}
