package org.ISF;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalTime;
import java.util.*;

import static org.ISF.Json.parse;

public class Main {
    public static void main(String[] args) {

        //---variables declaration---//
        String src;             //.json file source
        Store store;            //store object
        Order[] orders;         //orders array

        //---this block loads store configuration and orders list---//
        try {
            src = "E:\\Programming\\Java\\zadanie-java\\self-test-data\\advanced-allocation\\store.json";
            store = parse(src, Store.class);
            src = "E:\\Programming\\Java\\zadanie-java\\self-test-data\\advanced-allocation\\orders.json";
            orders = parse(src, Order[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //---calculating when picker must start to pick order to finish it on time---//
        Arrays.stream(orders).forEach(Order::setOrderPickingStartTime);

        //---sort by pickingtime---//
        //---sort by picking start time---//
        Arrays.sort(orders, new MyComparator());

        //---creating HashMap of pickers to easily choose the one which is available---//
        HashMap<String, LocalTime> pickersAvailabilityHashMap = new HashMap<>();
        for(String picker : store.getPickers()) {
            pickersAvailabilityHashMap.put(picker, store.getPickingStartTime());
        }

        //---assigning orders to pickers---//
        for (Order order : orders) {
            String earliestAvailablePickerKey = null;
            LocalTime earliestAvailablePickerTime = null;

            for (Map.Entry<String, LocalTime> picker : pickersAvailabilityHashMap.entrySet()) {
                if (earliestAvailablePickerTime == null || picker.getValue().isBefore(earliestAvailablePickerTime)) {
                    earliestAvailablePickerKey = picker.getKey();
                    earliestAvailablePickerTime = picker.getValue();
                }
            }

            assert earliestAvailablePickerTime != null;
            if (!earliestAvailablePickerTime.isAfter(order.getOrderPickingStartTime())) {
                System.out.println(earliestAvailablePickerKey + " " + order.getOrderId() + " " + earliestAvailablePickerTime);
                LocalTime newPickerTime = earliestAvailablePickerTime.plus(order.getPickingTime());
                pickersAvailabilityHashMap.put(earliestAvailablePickerKey, newPickerTime);
            }
        }
    }
}