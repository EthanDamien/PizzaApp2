package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pizza_classes.Order;
import pizza_classes.Pizza;
import pizza_classes.StoreOrders;
import pizza_classes.Topping;

public class StoreOrdersActivity extends Activity {
    private ListView orderList;
    private StoreOrders storeOrder;
    private ListView allOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders_activity);
        Intent intent = getIntent();
        storeOrder = (StoreOrders) intent.getSerializableExtra("store");
        setupOrders();
    }

    private void setupOrders(){
        orderList = findViewById(R.id.allOrders);
        ArrayList<String> orders = new ArrayList<>();
        for(Order order: storeOrder.getOrders()){
            orders.add(order.toString());
        }
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orders);
        orderList.setAdapter(orderAdapter);
    }


}
