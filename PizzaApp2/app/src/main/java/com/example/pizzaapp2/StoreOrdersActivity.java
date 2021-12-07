package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import pizza_classes.Order;
import pizza_classes.Pizza;
import pizza_classes.StoreOrders;

/**
 * The Android activity that takes care of all Store Orders.
 * Sets up and manages the Orders placed.
 * @author Ethan Chang and Kevin Cubillos
 */
public class StoreOrdersActivity extends Activity {
    /** The index of the selected Order **/
    private int selectedOrder;
    /** The Store orders object that contains all orders **/
    private StoreOrders storeOrder;

    private ListView pizzaList;
    private Spinner numberSpinner;

    /**
     * The onCreate method for the Store Orders Activity.
     * Initializes needed information for this activity.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders_activity);
        Intent intent = getIntent();
        storeOrder = (StoreOrders) intent.getSerializableExtra("store");
        pizzaList = findViewById(R.id.all_orders);
        setUpSpinner();
        setupPizzas(0);
    }

    /**
     * Sets up the pizzas based on the number specified on the spinner
     * @param i index of spinner
     */
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

    /**
     * Sets up the spinner based on the available orders in the store
     */
    private void setUpSpinner(){
        numberSpinner = findViewById(R.id.number_spinner);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Whenever a new order is selected, update the pizza list and selected index.
             */
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

    /**
     * Cancels the order in which the spinner is on
     * @param view button that is pressed
     */
    public void cancelOrder(View view){
        ArrayAdapter list = (ArrayAdapter) numberSpinner.getAdapter();
        if(list.getCount() > 0){
            storeOrder.removeOrder(selectedOrder);
            list.remove(list.getItem(selectedOrder));
            Toast.makeText(getApplicationContext(), "Order Cancelled" , Toast.LENGTH_SHORT).show();
            selectedOrder = selectedOrder > list.getCount() - 1 ? selectedOrder - 1 : selectedOrder;
            setupPizzas(selectedOrder);
        }
    }

    /**
     * This Overrides the onBackPressed method to update the store orders, when going back
     */
    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("storeOrder", storeOrder);
        // Activity finished return ok, return the data
        setResult(RESULT_OK, data);
        finish();
        super.onBackPressed();
    }
}
