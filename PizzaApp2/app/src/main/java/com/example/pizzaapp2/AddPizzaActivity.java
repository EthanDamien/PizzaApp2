package com.example.pizzaapp2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddPizzaActivity extends AppCompatActivity {
    private ImageView pizzaPicture;
    private TextView pizzaName;
    private Spinner pizzaSizesDropdown;
    private String[] sizes = new String[]{"Small", "Medium", "Large"};
    private ArrayAdapter pizzaSizesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza_activity);
        Intent intent = getIntent();
        setupDropdown();
        setupDescription(intent);
    }

    private void setupDropdown(){
        pizzaSizesDropdown = findViewById(R.id.pizzaSizes);
        ArrayAdapter<String> pizzaSizesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        pizzaSizesDropdown.setAdapter(pizzaSizesAdapter);
    }

    private void setupDescription(Intent intent){
        pizzaPicture = findViewById(R.id.pizzaPicture);
        pizzaName = findViewById(R.id.pizzaName);
        String name = intent.getStringExtra("name");
        pizzaName.setText(name);
        String uri = "marios_pizza.jpg";
        switch(name) {
            case "Deluxe Pizza":
                uri = "@drawable/deluxe_pizza.jpg";
                break;
            case "Pepperoni Pizza":
                uri = "@drawable/pepperoni_pizza.jpg";
                break;
            case "Hawaiian Pizza":
                uri = "@drawable.hawaiian_pizza.jpg";
                break;
        }
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        pizzaPicture.setImageDrawable(res);

    }

}
