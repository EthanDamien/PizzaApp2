package com.example.pizzaapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.pizzaapp2.databinding.ActivityMainBinding;

import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import pizza_classes.Order;

public class MainActivity extends AppCompatActivity {
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
            Intent addPizza = new Intent(this, AddPizzaActivity.class);
            addPizza.putExtra("name", view.getContentDescription());
            startActivity(addPizza);
        }
    }



    private boolean validPhoneNumber(){
        phoneField = findViewById(R.id.phoneField);
        String number = phoneField.getText().toString();
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


}