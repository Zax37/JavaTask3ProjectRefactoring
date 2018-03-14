package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;

import java.math.BigDecimal;

public class DiscountService {
    public BigDecimal getDiscountFor(Item item, Customer customer) {
        return DiscountsConfig.getDiscountForItem(item, customer);
    }

    public boolean isWeekendPromotion() {
        return DiscountsConfig.isWeekendPromotion();
    }
}
