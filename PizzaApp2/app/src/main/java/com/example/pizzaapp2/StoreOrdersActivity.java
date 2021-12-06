package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import pizza_classes.Order;
import pizza_classes.StoreOrders;
import pizza_classes.Topping;

public class StoreOrdersActivity extends Activity {
    private StoreOrders storeOrder;
    private ListView allOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders_activity);
        Intent intent = getIntent();
        storeOrder = intent.getParcelableExtra("store");
    }

    private void setupOrders(Intent intent){
        ArrayList<Order> orders = storeOrder.getOrders();
        allOrders = findViewById(R.id.allOrders);
        ArrayAdapter<Order> allOrderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orders);
        allOrders.setAdapter(allOrderAdapter);
    }


}
