package com.example.pizzaapp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pizza_classes.Pizza;
import pizza_classes.PizzaMaker;
import pizza_classes.Topping;

public class AddPizzaActivity extends AppCompatActivity {
    private Pizza pizza;
    private ListView unusedToppings, currentToppings;
    private ImageView pizzaPicture;
    private TextView pizzaName, price;
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
        updateToppings();
    }

    private void setupDropdown(){
        pizzaSizesDropdown = findViewById(R.id.pizzaSizes);
        ArrayAdapter<String> pizzaSizesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        pizzaSizesDropdown.setAdapter(pizzaSizesAdapter);
    }

    private void setupDescription(Intent intent){
        pizzaPicture = findViewById(R.id.pizzaPicture);
        price = findViewById(R.id.price);
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
        pizza = PizzaMaker.createPizza(name);
        price.setText(pizza.priceFormatted());

    }

    private void updateToppings(){
        currentToppings = findViewById(R.id.currentToppings);
        unusedToppings = findViewById(R.id.unusedToppings);
        ArrayList<Topping> uTop = new ArrayList<Topping>();
        ArrayList<Topping> cTop = pizza.getToppings();
        for(Topping t : Topping.values()){
            if(!cTop.contains(t)){
                uTop.add(t);
            }
        }
        ArrayAdapter<Topping> unusedToppingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, uTop);
        ArrayAdapter<Topping> currToppingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cTop);
        unusedToppings.setAdapter(unusedToppingAdapter);
        currentToppings.setAdapter(currToppingAdapter);

    }

    public void addTopping(){
        price = findViewById(R.id.price);
        Topping selected = (Topping) currentToppings.getSelectedItem();
        if(selected == null){
            return;
        }
        if(pizza.addTopping(selected)){
            ((ArrayAdapter)currentToppings.getAdapter()).add(selected);
            ((ArrayAdapter)unusedToppings.getAdapter()).remove(selected);
            price.setText(pizza.priceFormatted());
        }
        else{
            Toast.makeText(getApplicationContext(), "Max Toppings Reached", Toast.LENGTH_SHORT).show();
        }
    }
}
