package woo.core.Payments;

import woo.core.Client;
import woo.core.clientClassification;
import woo.core.interfaces.PaymentMode;

public class ContainerMode implements PaymentMode {


    public double computePayment(Client c, double baseValue, int date, int deadline) {
        int n = 8;
        int x = deadline - date;
        int y = date - deadline;
        double toPayValue = baseValue;
        
        if (x >= n)
            toPayValue = baseValue - 0.10 * baseValue;
            c.addPoints(toPayValue * 10);
        if (0 <= x && n > x) {
            if (c.getClassification().equals("ELITE"))
                toPayValue = baseValue - 0.10 * baseValue;
            if (c.getClassification().equals("SELECTION") && x >= 2)
                toPayValue = baseValue - 0.05 * baseValue;
            else if (c.getClassification().equals("SELECTION") || c.getClassification().equals("NORMAL")){
                toPayValue = baseValue;
            } 
            c.addPoints(toPayValue * 10);
        }
        if (0 < y && n >= y) {
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
        if (y > n) {
            if (c.getClassification().equals("ELITE"))
                toPayValue = baseValue;
            if (c.getClassification().equals("NORMAL"))
                toPayValue = baseValue + 0.10 * baseValue * y;
            if (c.getClassification().equals("SELECTION"))
                toPayValue = baseValue + 0.05 * baseValue * y;
        }
        if (y > 2 && c.getClassification().equals("SELECTION")) {
            double toRemove = c.getPoints() * 0.9;
            c.removePoints(toRemove);
            c.setClassification(clientClassification.NORMAL);
        }
        if (y > 15 && c.getClassification().equals("ELITE")) {
            double toRemove = c.getPoints() * 0.75;
            c.removePoints(toRemove);
            c.setClassification(clientClassification.SELECTION);
        }
        return toPayValue;
    } 
}

