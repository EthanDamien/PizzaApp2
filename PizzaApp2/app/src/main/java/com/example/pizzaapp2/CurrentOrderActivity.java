package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pizza_classes.Order;
import pizza_classes.Pizza;

/**
 * The Android activity that takes care of the current order.
 * Manages the current order being processed.
 * @author Ethan Chang and Kevin Cubillos
 */
public class CurrentOrderActivity extends Activity {
    /** The index of the selected Pizza **/
    private int selectedPizza;
    /** The current order being handled **/
    private Order currOrder;

    private ListView pizzaList;
    private TextView phoneNumber, amountOfPizzas, subtotal, salesTax, orderTotal;
    private Button removePizzaButton, placeOrder;

    /**
     * The onCreate method for the currentOrder activity.
     * Initializes needed information on this activity.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_activity);
        Intent intent = getIntent();
        Bundle data = getIntent().getExtras();
        currOrder = (Order) data.getSerializable("order");
        subtotal = findViewById(R.id.subtotal);
        salesTax = findViewById(R.id.sales_tax);
        orderTotal = findViewById(R.id.order_total);
        phoneNumber = findViewById(R.id.phone_number);
        phoneNumber.setText(currOrder.getPhoneNumber());
        amountOfPizzas = findViewById(R.id.amount_of_pizzas);
        amountOfPizzas.setText("Amount Of Pizzas: " + currOrder.size());
        setupPizzas();
        updateCostData();
    }

    /**
     * This method sets up all the Pizzas in the listView
     */
    private void setupPizzas(){
        pizzaList = findViewById(R.id.pizza_list);
        pizzaList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * When a pizza is selected on ListView, update the selected index
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPizza = i;
            }

            /**
             * Place holder
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayList<String> pizzas = new ArrayList<>();
        for(Pizza pizza: currOrder.getPizzas()){
            pizzas.add(pizza.toString());
        }
        ArrayAdapter<String> pizzaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pizzas);
        pizzaList.setAdapter(pizzaAdapter);
    }

    /**
     * This method updates the cost data for each of the fields
     */
    private void updateCostData(){
        subtotal.setText(currOrder.getSubtotal());
        salesTax.setText(currOrder.getTax());
        orderTotal.setText(currOrder.getFinalPrice());
    }

    /**
     * This method deletes the selected Pizza in the ListView
     * @param view button that is pressed
     */
    public void deletePizza(View view){
        ArrayAdapter list = (ArrayAdapter) pizzaList.getAdapter();
        if(list.getCount() > 0){
            currOrder.removePizza(selectedPizza);

            list.remove(list.getItem(selectedPizza));
            updateCostData();
        }
    }


    /**
     * This places the order and returns to the previous screen while passing current order to
     * be added
     * @param view button that is pressed
     */
    public void placeOrder(View view){
        Intent data = new Intent();
        data.putExtra("order", currOrder);
        // Activity finished return ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * This Overrides the onBackPressed method to account for just updating the current order
     */
    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("order", currOrder);
        // Activity finished return Cancelled, return the data
        setResult(RESULT_CANCELED, data);
        finish();
        super.onBackPressed();
    }
}
