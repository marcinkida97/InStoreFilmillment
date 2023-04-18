package org.ISF;

import java.util.Comparator;

public class MyComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if(o2 == null) return -1;
        if(o1.getOrderPickingStartTime().compareTo(o2.getOrderPickingStartTime()) > 0) return 1;
        else if(o1.getOrderPickingStartTime().compareTo(o2.getOrderPickingStartTime()) < 0) return -1;
        else {
            if(o1.getPickingTime().compareTo(o2.getPickingTime()) > 0) return 1;
            else if(o1.getPickingTime().compareTo(o2.getPickingTime()) < 0) return -1;
            else return 0;
        }
    }
}
