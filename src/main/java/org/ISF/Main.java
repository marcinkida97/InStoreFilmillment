package org.ISF;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        //---variables declaration---//
        String src;             //.json file source
        String jsonString;      //file converted to json string
        JsonNode node;          //json node
        Store store;            //store object
        Order[] orders;         //orders array

        //---this block loads store configuration---//
        try {
            //src = "E:\\Programming\\Java\\zadanie-java\\self-test-data\\advanced-optimize-order-count\\store.json";
            src = "E:\\Programming\\Java\\zadanie-java\\self-test-data\\advanced-allocation\\store.json";
            jsonString = Json.readJson(src);
            node = Json.parse(jsonString);
            store = Json.fromJson(node, Store.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //---this block reads orders list---//
        try {
            //src = "E:\\Programming\\Java\\zadanie-java\\self-test-data\\advanced-optimize-order-count\\orders.json";
            src = "E:\\Programming\\Java\\zadanie-java\\self-test-data\\advanced-allocation\\orders.json";
            jsonString = Json.readJson(src);
            node = Json.parse(jsonString);
            orders = Json.fromJson(node, Order[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (Order order : orders) {
            order.setOrderPickingStartTime(Order.calculateOrderPickingStartTime(order.getCompleteBy(), order.getPickingTime()));
        }

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