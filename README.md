## ISF

In Store Fulfillment

Application which helps you to distributing orders to pickers effectivety


## How does the ISF work?

The ISF app is taking store configuration and orders list as an input and returns you orders assigned to pickers with time when picker should start collecting given order.


## How to use the ISF app

To use the application you need to have two files:

store.json: {
"pickers": [
"P1",
"P2"
],
"pickingStartTime": "09:00",
"pickingEndTime": "11:00"
}

orders.json [
{
"orderId": "order-1",
"orderValue": "0.00",
"pickingTime": "PT15M",
"completeBy": "09:15"
},
{
"orderId": "order-2",
"orderValue": "0.00",
"pickingTime": "PT30M",
"completeBy": "09:30"
}...
]

Then you need to provide paths in Main.java in lines 20 and 22.\
The output will look like: pickerId orderId pickingStartTime\
P1 order-1 09:00\
P2 order-2 09:00\
...

## About code

The project itself consist of 6 files representing Java classes:

pom.xml – Maven file with dependencies related to Lombok and Jackson
Store.java – store configuration loaded from store.json. The class consists of 3 variables: List<String> pickers, LocalTime pickingStartTime, LocalTime pickingEndTime;
OrdersList.java – It contains List<Order> orders. loaded from orders.json.
Order.java – represents single order from orders.json. The class consists of 5 variables: String orderId, BigDecimal orderValue, Duration pickingTime, LocalTime completeBy, LocalTime orderPickingStartTime;
Json.java – containing json file reader and parser to java objects
Main.java – main function and application logic

Classes Store and Order matches exactly parameters in .json files

Class Order has also a variable orderPickingStartTime to store when the orders should start to be collected by picker and a function to calculate it.

The json parser uses Jackson library which is added by Maven dependencies.


## What is the logic of application?

1. Read store.json file and configure store
2. Read orders.json and create orders list
3. Calculate by when the order should start to be collected (orderPickingStartTime)
4. Sort by pickingTime and by orderPickingStartTime
5. Creating hash map of pickers with pickerId and the time when picker is available
6. Loop with assigning orders to pickers
