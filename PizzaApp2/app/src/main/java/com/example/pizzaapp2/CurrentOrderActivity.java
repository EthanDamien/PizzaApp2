package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import pizza_classes.Order;
import pizza_classes.StoreOrders;

public class CurrentOrderActivity extends Activity {
    private StoreOrders storeOrders;
    private Order currOrder;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_activity);
        Intent intent = getIntent();
        storeOrders = intent.getParcelableExtra("store");
        phoneNumber = intent.getStringExtra("phoneNumber");
    }


}
