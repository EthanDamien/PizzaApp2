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
        switch(name) {
            case "Deluxe Pizza":
                pizzaPicture.setImageDrawable(getDrawable(R.drawable.deluxe_pizza));
                break;
            case "Pepperoni Pizza":
                pizzaPicture.setImageDrawable(getDrawable(R.drawable.pepperoni_pizza));
                break;
            case "Hawaiian Pizza":
                pizzaPicture.setImageDrawable(getDrawable(R.drawable.hawaiian_pizza));
                break;
        }

    }

}
