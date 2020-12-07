package woo.core.Payments;

import woo.core.Client;
import woo.core.interfaces.PaymentMode;

public class BoxMode implements PaymentMode {

    // private int _n = 5;
    // private int _date;
    // private int _deadline;
    // private Client _client;
    // private double _baseValue;
    // private double _toPayValue;

    // public BoxMode(Client c, int baseValue, int date, int deadline) {
    //     _date = date;
    //     _deadline = deadline;
    //     _client = c;
    //     _baseValue = baseValue;
    // }

    public double computePayment(Client c, double baseValue, int date, int deadline) {
        int x = deadline - date;
        int y = date - deadline;
        double toPayValue = 0;

        if (x >= 5)
            toPayValue = baseValue - 0.10 * baseValue;
        if (0 <= x && 5 > x) {
            if (c.getClassification().equals("ELITE"))
                toPayValue = baseValue - 0.10 * baseValue;
            if (c.getClassification().equals("SELECTION") && x >= 2)
                toPayValue = baseValue - 0.05 * baseValue;
            else if (c.getClassification().equals("SELECTION") || c.getClassification().equals("NORMAL")){
                toPayValue = baseValue;
            } 
        }
        if (0 < y && 5 >= y) {
            if (c.getClassification().equals("ELITE"))
                toPayValue = baseValue - 0.05 * baseValue;
            if (c.getClassification().equals("NORMAL")) {
                toPayValue = baseValue + 0.05 * baseValue * y;
            }
            if (c.getClassification().equals("SELECTION"))
                if (y <= 1)
                    toPayValue = baseValue;
                toPayValue = baseValue + baseValue * 0.02 * y;
        }
        if (y > 5) {
            if (c.getClassification().equals("ELITE"))
                toPayValue = baseValue;
            if (c.getClassification().equals("NORMAL"))
                toPayValue = baseValue + 0.10 * baseValue * y;
            if (c.getClassification().equals("SELECTION"))
                toPayValue = baseValue + 0.05 * baseValue * y;
        }
        return toPayValue;
    } 
}
