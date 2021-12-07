package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pizza_classes.Order;
import pizza_classes.Pizza;
import pizza_classes.StoreOrders;

public class CurrentOrderActivity extends Activity {
    private ListView pizzaList;
    private StoreOrders storeOrders;
    private Order currOrder;
    private TextView phoneNumber, amountOfPizzas, subtotal, salesTax, orderTotal;
    private Button removePizzaButton, placeOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_activity);
        Intent intent = getIntent();
        Bundle data = getIntent().getExtras();
        currOrder = (Order) data.getSerializable("order");
        phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumber.setText(currOrder.getPhoneNumber());
        amountOfPizzas = findViewById(R.id.amountOfPizzas);
        amountOfPizzas.setText("Amount Of Pizzas: " + currOrder.size());
        setupPizzas();
        updateCostData();
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

    private void updateCostData(){
        subtotal = findViewById(R.id.subtotal);
        salesTax = findViewById(R.id.salesTax);
        orderTotal = findViewById(R.id.orderTotal);
        subtotal.setText(currOrder.getSubtotal());
        salesTax.setText(currOrder.getTax());
        orderTotal.setText(currOrder.getFinalPrice());
    }

    public void deletePizza(){
        updateCostData();
    }

    //Pass in the shits
    public void placeOrder(View view){
        Intent data = new Intent();
        data.putExtra("order", currOrder);
        // Activity finished return ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

}
