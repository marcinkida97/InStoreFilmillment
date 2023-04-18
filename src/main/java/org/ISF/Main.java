package org.ISF;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalTime;
import java.util.*;

import static org.ISF.Json.parse;

public class Main {
    public static void main(String[] args) {

        //---variables declaration---//
        String path;             //.json file source
        Store store;            //store object
        Order[] orders;         //orders array

        //---this block loads store configuration and orders list---//
        try {
            path = "store.json path";               //replace path to store.json
            store = parse(path, Store.class);
            path = "orders.json path";              //replace path to orders.json
            orders = parse(path, Order[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //---calculating when picker must start to pick order to finish it on time---//
        Arrays.stream(orders).forEach(Order::setOrderPickingStartTime);

        //---sort by pickingTime and by orderPickingStartTime---//
        Arrays.sort(orders, new MyComparator());

        //---creating HashMap of pickers to easily choose the one which is available---//
        Map<String, LocalTime> pickersHashMap = new HashMap<>();
        for(String picker : store.getPickers()) {
            pickersHashMap.put(picker, store.getPickingStartTime());
        }

        //---assigning orders to pickers---//
        for (Order order : orders) {
            Map.Entry<String, LocalTime> picker = pickersHashMap.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .orElse(null);

            if (!picker.getValue().isAfter(order.getOrderPickingStartTime())) {
                System.out.println(picker.getKey() + " " + order.getOrderId() + " " + picker.getValue());
                LocalTime newPickerTime = picker.getValue().plus(order.getPickingTime());
                pickersHashMap.replace(picker.getKey(), newPickerTime);
            }
        }
    }
}