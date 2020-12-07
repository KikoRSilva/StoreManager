package woo.core.Payments;

import woo.core.Client;
import woo.core.interfaces.PaymentMode;

public class ContainerMode implements PaymentMode {

    public double computePayment(Client c, double baseValue, int date, int deadline) {
        int x = deadline - date;
        int y = date - deadline;
        double toPayValue = 0;

        if (x >= 8)
            toPayValue = baseValue - 0.10 * baseValue;
        if (0 <= x && 8 > x) {
            if (c.getClassification().equals("ELITE"))
                toPayValue = baseValue - 0.10 * baseValue;
            if (c.getClassification().equals("SELECTION") && x >= 2)
                toPayValue = baseValue - 0.05 * baseValue;
            else if (c.getClassification().equals("SELECTION") || c.getClassification().equals("NORMAL")){
                toPayValue = baseValue;
            } 
        }
        if (0 < y && 8 >= y) {
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
        if (y > 8) {
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
