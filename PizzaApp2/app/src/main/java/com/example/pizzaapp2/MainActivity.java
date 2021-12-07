package com.example.pizzaapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.pizzaapp2.databinding.ActivityMainBinding;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import pizza_classes.Order;
import pizza_classes.StoreOrders;

/**
 * The Main Activity that manages all the available actions.
 * This is the homepage of the app.
 * @author Ethan Chang and Kevin Cubillos
 */
public class MainActivity extends AppCompatActivity {
    /** The list of orders to be managed **/
    private StoreOrders storeOrders = new StoreOrders();
    /** The current order being handled **/
    private Order currOrder;
    /** The static final variables for request code**/
    private static final int OPEN_ADD_PIZZA = 1, OPEN_CURRENT_ORDER = 2, OPEN_ALL_ORDERS = 3;

    private EditText phoneField;
    private ImageButton DeluxePizzaButton, PepperoniPizzaButton, HawaiianPizzaButton;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    /**
     * The onCreate method for the Main Activity.
     * Initializes needed information and initial Screen.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeluxePizzaButton = findViewById(R.id.deluxe_pizza_button);
        PepperoniPizzaButton = findViewById(R.id.pepperoni_pizza_button);
        HawaiianPizzaButton = findViewById(R.id.hawaiian_pizza_button);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
    }

    /**
     * Opens the Add Pizza Activity while passing in the current order.
     * @param view button being pressed
     */
    public void openAddPizzaActivity(View view) {
        if (validPhoneNumber()) {
            if (currOrder == null || !currOrder.getPhoneNumber().equals(this.getPhoneNumber())) {
                currOrder = new Order(this.getPhoneNumber());
            }
            if (storeOrders.contains(currOrder.getPhoneNumber())) {
                Toast.makeText(getApplicationContext(), "Customer Already has an Order", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent addPizza = new Intent(this, AddPizzaActivity.class);
            addPizza.putExtra("name", view.getContentDescription());
            addPizza.putExtra("order", currOrder);
            startActivityForResult(addPizza, OPEN_ADD_PIZZA);
        }
    }

    /**
     * Opens the Current Order Activity while passing in the current order.
     * @param view button being pressed
     */
    public void openCurrentOrderActivity(View view) {
        if (validPhoneNumber()) {
            if (currOrder == null || !currOrder.getPhoneNumber().equals(this.getPhoneNumber())) {
                currOrder = new Order(this.getPhoneNumber());
            }
            if (storeOrders.contains(currOrder.getPhoneNumber())) {
                Toast.makeText(getApplicationContext(), "Customer Already has an Order", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent currentOrder = new Intent(this, CurrentOrderActivity.class);
            currentOrder.putExtra("phoneNumber", getPhoneNumber());
            currentOrder.putExtra("store", storeOrders);
            currentOrder.putExtra("order", currOrder);
            startActivityForResult(currentOrder, OPEN_CURRENT_ORDER);
        }
    }

    /**
     * Opens the Store Orders Activity while passing in all store orders.
     * @param view button being pressed
     */
    public void openStoreOrdersActivity(View view) {
        Intent allOrders = new Intent(this, StoreOrdersActivity.class);
        allOrders.putExtra("store", storeOrders);
        startActivityForResult(allOrders, OPEN_ALL_ORDERS);
    }



    /**
     * Checks if customer phone number is valid, or filled out.
     * Needs to be a 10 digit number
     * @return true if valid, false if not
     */
    private boolean validPhoneNumber() {
        phoneField = findViewById(R.id.phone_field);
        String number = getPhoneNumber();
        try {
            if (number.equals("") || number.length() != Order.PHONE_NUMBER_LENGTH) {
                Toast.makeText(getApplicationContext(), "Please Enter a Valid Number First", Toast.LENGTH_SHORT).show();
                return false;
            }
            Long.parseLong(number);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Not a Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * This handles the Activity Results and updates Main Activity's data based on resultCode and
     * requestCode.
     * @param requestCode the request code for the result action
     * @param resultCode the code assigned to each activity call
     * @param data the intent data passed back from the result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }
        if(requestCode == OPEN_CURRENT_ORDER && resultCode == RESULT_OK){
            refreshDataAndAddOrder(data);
            return;
        }
        refreshDataOnly(data, requestCode);
    }

    /**
     * This refreshes just the data or either current order, or all orders based on requestCode
     * @param data the intent data passed from the result
     * @param requestCode the code assigned to each activity call
     */
    private void refreshDataOnly(Intent data, int requestCode){
        switch(requestCode){
            case OPEN_ADD_PIZZA:
                Toast.makeText(getApplication(), "Added Pizza!", Toast.LENGTH_SHORT).show();
                currOrder = (Order)data.getSerializableExtra("order");
                break;
            case OPEN_CURRENT_ORDER:
                Toast.makeText(getApplication(), "Order Still in Progress!",
                        Toast.LENGTH_SHORT).show();
                currOrder = (Order)data.getSerializableExtra("order");
                break;
            case OPEN_ALL_ORDERS:
                storeOrders = (StoreOrders) data.getSerializableExtra("storeOrder");
                break;
            default:
                break;

        }
    }

    /**
     * This refreshes the data and adds the order to all store orders
     * @param data the intent data passed from the result
     */
    private void refreshDataAndAddOrder(Intent data){
        Toast.makeText(getApplication(), "Order Placed!", Toast.LENGTH_SHORT).show();
        storeOrders.addOrder((Order)data.getSerializableExtra("order"));
    }


    /**
     * This gets the phone number from the phone field
     * @return the phone number as a string
     */
    private String getPhoneNumber(){
        return phoneField.getText().toString();
    }

}