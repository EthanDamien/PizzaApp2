package com.example.pizzaapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.pizzaapp2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton DeluxePizzaButton, PepperoniPizzaButton, HawaiianPizzaButton;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeluxePizzaButton = findViewById(R.id.DeluxePizzaButton);
        PepperoniPizzaButton = findViewById(R.id.pizzaPicture);
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
        Intent addPizza = new Intent(this, AddPizzaActivity.class);
        addPizza.putExtra("name", view.getContentDescription());
        startActivity(addPizza);
    }


}