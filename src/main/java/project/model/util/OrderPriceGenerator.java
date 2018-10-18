package project.model.util;


import project.model.domain.CarType;
import project.model.exception.NoStreetWithSuchName;

import java.io.IOException;

/**
 * Generates price for order
 */
public class OrderPriceGenerator {

    /**
     * Calculates order price
     *
     * @param moneySpent        money spent by user
     * @param departureStreet   departure street
     * @param destinationStreet destination street
     * @param carType           car type
     * @return order price
     */
    public static Long getOrderPrice(Long moneySpent, String departureStreet, String destinationStreet, CarType carType) throws IOException, NoStreetWithSuchName {
        Long distance = Math.round(GeoCodingUtils.getRouteInformation(departureStreet, destinationStreet).stream().map(route -> route.getDistance() / 1000).findFirst().get());
        Long discount = getDiscountBasedOnMoneySpent(moneySpent) + carType.getDiscount();
        Long orderPrice = (carType.getStartingPrice() + (distance * carType.getPricePerKilometer()));
        if (discount.equals(0L)) {
            return orderPrice;
        }
        orderPrice = orderPrice - (orderPrice / 100) * discount;
        return orderPrice;
    }

    public static Long getOrderWaitingTime(String departureStreet, String destinationStreet) throws IOException, NoStreetWithSuchName {

        return Math.round(GeoCodingUtils.getRouteInformation(departureStreet, destinationStreet).stream().map(route -> route.getDuration() / 60).findFirst().get());
    }

    /**
     * Chooses discount depending on money spent by user
     *
     * @param moneySpent money spent by user
     * @return user discount
     */
    public static Long getDiscountBasedOnMoneySpent(Long moneySpent) {
        long discountRate;
        if (moneySpent <= 100000) {
            discountRate = 0L;
        } else if (moneySpent <= 250000) {
            discountRate = 5L;
        } else if (moneySpent <= 500000) {
            discountRate = 10L;
        } else {
            discountRate = 15L;
        }
        return discountRate;
    }
}
