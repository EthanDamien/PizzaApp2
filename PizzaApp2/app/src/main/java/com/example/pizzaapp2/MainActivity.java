package com.example.pizzaapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.pizzaapp2.databinding.ActivityMainBinding;

import android.os.Parcelable;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;

import pizza_classes.Order;
import pizza_classes.StoreOrders;

public class MainActivity extends AppCompatActivity {
    private StoreOrders storeOrders = new StoreOrders();
    private Order currOrder;
    private EditText phoneField;
    private ImageButton DeluxePizzaButton, PepperoniPizzaButton, HawaiianPizzaButton;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeluxePizzaButton = findViewById(R.id.DeluxePizzaButton);
        PepperoniPizzaButton = findViewById(R.id.PepperoniPizzaButton);
        HawaiianPizzaButton = findViewById(R.id.HawaiianPizzaButton);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void openAddPizzaActivity(View view) {
        if(validPhoneNumber()) {
            if(currOrder == null || !currOrder.getPhoneNumber().equals(this.getPhoneNumber())){
                currOrder = new Order(this.getPhoneNumber());
            }
            if(storeOrders.contains(currOrder.getPhoneNumber())){
                Toast.makeText(getApplicationContext(), "Customer Already has an Order", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent addPizza = new Intent(this, AddPizzaActivity.class);
            addPizza.putExtra("phoneNumber", getPhoneNumber());
            addPizza.putExtra("name", view.getContentDescription());
            addPizza.putExtra("store", (Parcelable) storeOrders);
            addPizza.putExtra("currOrder",(Parcelable) currOrder);
            startActivity(addPizza);
        }
    }

    public void openStoreOrdersActivity(View view){
        Intent allOrders = new Intent(this, StoreOrdersActivity.class);
        allOrders.putExtra("store", (Parcelable) storeOrders);
        startActivity(allOrders);
    }

    public void openCurrentOrderActivity(View view){
        if(validPhoneNumber()) {
            Intent currOrder = new Intent(this, CurrentOrderActivity.class);
            currOrder.putExtra("phoneNumber", getPhoneNumber());
            currOrder.putExtra("store", (Parcelable) storeOrders);
            startActivity(currOrder);
        }
    }



    private boolean validPhoneNumber(){
        phoneField = findViewById(R.id.phoneField);
        String number = getPhoneNumber();
        try{
            if(number.equals("") || number.length() != Order.PHONE_NUMBER_LENGTH){
                Toast.makeText(getApplicationContext(), "Please Enter a Valid Number First", Toast.LENGTH_SHORT).show();
                return false;
            }
            Long.parseLong(number);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Not a Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String getPhoneNumber(){
        return phoneField.getText().toString();
    }

}