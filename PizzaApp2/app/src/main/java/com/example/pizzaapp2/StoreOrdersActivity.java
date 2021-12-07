package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pizza_classes.Order;
import pizza_classes.Pizza;
import pizza_classes.StoreOrders;
import pizza_classes.Topping;

public class StoreOrdersActivity extends Activity {
    private ListView pizzaList;
    private StoreOrders storeOrder;
    private Spinner numberSpinner;
    private ListView allOrders;
    private int selectedOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders_activity);
        Intent intent = getIntent();
        storeOrder = (StoreOrders) intent.getSerializableExtra("store");
        pizzaList = findViewById(R.id.allOrders);
        setUpSpinner();
        setupPizzas(0);
    }

    private void setupPizzas(int i){
        if(numberSpinner.getCount() == 0){
            if(pizzaList.getCount() != 0){
                ((ArrayAdapter) pizzaList.getAdapter()).clear();
            }
            return;
        }
        ArrayList<String> pizzas = new ArrayList<>();
        for(Pizza pizza: storeOrder.getOrder(i).getPizzas()){
            pizzas.add(pizza.toString());
        }
        ArrayAdapter<String> pizzaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pizzas);
        pizzaList.setAdapter(pizzaAdapter);
    }

    private void setUpSpinner(){
        numberSpinner = findViewById(R.id.numberSpinner);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedOrder = i;
                setupPizzas(selectedOrder);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayList<String> numbers = new ArrayList<>();
        for(Order order : storeOrder.getOrders()){
            numbers.add(order.getPhoneNumber());
        }
        ArrayAdapter<String> numberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numbers);
        numberSpinner.setAdapter(numberAdapter);
    }

    public void cancelOrder(View view){
        ArrayAdapter list = (ArrayAdapter) numberSpinner.getAdapter();
        if(list.getCount() > 0){
            storeOrder.removeOrder(selectedOrder);
            list.remove(list.getItem(selectedOrder));
            selectedOrder = selectedOrder > list.getCount() - 1 ? selectedOrder - 1 : selectedOrder;
            setupPizzas(selectedOrder);
        }
    }


}
