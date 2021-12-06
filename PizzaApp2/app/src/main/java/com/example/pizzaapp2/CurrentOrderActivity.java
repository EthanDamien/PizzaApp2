package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pizza_classes.Order;
import pizza_classes.Pizza;
import pizza_classes.StoreOrders;

public class CurrentOrderActivity extends Activity {
    private ListView pizzaList;
    private StoreOrders storeOrders;
    private Order currOrder;
    private TextView phoneNumber, amountOfPizzas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_activity);
        Intent intent = getIntent();
        Bundle data = getIntent().getExtras();
        currOrder = (Order) data.getSerializable("order");
        storeOrders = intent.getParcelableExtra("store");
        phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumber.setText(currOrder.getPhoneNumber());
        amountOfPizzas = findViewById(R.id.amountOfPizzas);
        amountOfPizzas.setText("Amount Of Pizzas: " + currOrder.size());
        setupPizzas();
    }

    private void setupPizzas(){
        pizzaList = findViewById(R.id.pizzaList);
        ArrayList<String> pizzas = new ArrayList<>();
        for(Pizza pizza: currOrder.getPizzas()){
            pizzas.add(pizza.toString());
        }
        ArrayAdapter<String> pizzaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pizzas);
        pizzaList.setAdapter(pizzaAdapter);
    }

}
