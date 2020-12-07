package woo.core.interfaces;

import woo.core.Client;

public interface PaymentMode {
    double computePayment(Client c, double baseValue, int date, int deadline);
}
