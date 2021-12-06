package com.example.pizzaapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pizza_classes.Order;
import pizza_classes.Pizza;
import pizza_classes.PizzaMaker;
import pizza_classes.Topping;

public class AddPizzaActivity extends AppCompatActivity{
    private Pizza pizza;
    private Order currOrder;
    private ListView unusedToppings, currentToppings;
    private ImageView pizzaPicture;
    private TextView pizzaName, price;
    private Spinner pizzaSizesDropdown;
    private String[] sizes = new String[]{"Small", "Medium", "Large"};
    private ArrayList<Topping> requiredToppings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza_activity);
        Intent intent = getIntent();
        currOrder = (Order) intent.getParcelableExtra("currOrder");
        currentToppings = findViewById(R.id.currentToppings);
        unusedToppings = findViewById(R.id.unusedToppings);
        currentToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Topping selected = (Topping) ((ArrayAdapter)currentToppings.getAdapter()).getItem(i);
                if(selected == null || requiredToppings.contains(selected)){
                    Toast.makeText(getApplicationContext(),
                            "Cannot remove default toppings", Toast.LENGTH_SHORT).show();
                    return;
                }
                pizza.removeTopping(selected);
                ((ArrayAdapter)unusedToppings.getAdapter()).add(selected);
                ((ArrayAdapter)currentToppings.getAdapter()).remove(selected);
                price.setText(pizza.priceFormatted());
            }
        });
        unusedToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Topping selected = (Topping) ((ArrayAdapter)unusedToppings.getAdapter()).getItem(i);
                if(pizza.addTopping(selected)){
                    ((ArrayAdapter)currentToppings.getAdapter()).add(selected);
                    ((ArrayAdapter)unusedToppings.getAdapter()).remove(selected);
                    price.setText(pizza.priceFormatted());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Max Toppings Reached", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        ArrayList<Topping> uTop = new ArrayList<Topping>();
        ArrayList<Topping> cTop = pizza.getToppings();
        requiredToppings.addAll(pizza.getToppings());
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

//    public void addTopping(View view){
//        price = findViewById(R.id.price);
//        Topping selected = (Topping) currentToppings.getSelectedItem();
//        if(selected == null){
//            return;
//        }
//        if(pizza.addTopping(selected)){
//            ((ArrayAdapter)currentToppings.getAdapter()).add(selected);
//            ((ArrayAdapter)unusedToppings.getAdapter()).remove(selected);
//            price.setText(pizza.priceFormatted());
//        }
//        else{
//            Toast.makeText(getApplicationContext(), "Max Toppings Reached", Toast.LENGTH_SHORT).show();
//        }
//    }
}
