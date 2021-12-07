package com.example.pizzaapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pizza_classes.Order;
import pizza_classes.PepperoniPizza;
import pizza_classes.Pizza;
import pizza_classes.PizzaMaker;
import pizza_classes.Size;
import pizza_classes.Topping;

/**
 * The Android activity that takes care of the current Pizza.
 * Manages the new pizza can be added
 * @author Ethan Chang and Kevin Cubillos
 */
public class AddPizzaActivity extends AppCompatActivity{
    /** The current Pizza **/
    private Pizza pizza;
    /** The current Order **/
    private Order currOrder;
    /** The available pizza sizes based on the Enum **/
    private Size[] sizes = Size.values();
    /** The required toppings based on pizza type **/
    private ArrayList<Topping> requiredToppings = new ArrayList<>();

    private ListView unusedToppings, currentToppings;
    private ImageView pizzaPicture;
    private ImageButton orderUpButton;
    private TextView pizzaName, price;
    private Spinner pizzaSizesDropdown;


    /**
     * The onCreate method for the AddPizza activity.
     * Initializes needed information on this activity.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza_activity);
        Intent intent = getIntent();
        Bundle data = getIntent().getExtras();
        currOrder = (Order) data.getSerializable("order");
        currentToppings = findViewById(R.id.currentToppings);
        unusedToppings = findViewById(R.id.unusedToppings);
        orderUpButton = findViewById(R.id.orderUpButton);
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

    /**
     * Sets up the dropdown with the available sizes
     */
    private void setupDropdown(){
        pizzaSizesDropdown = findViewById(R.id.pizzaSizes);
        pizzaSizesDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Selects an item on the spinner with sizes listed.
             * When a size is selected, update data accordingly.
             * @param adapterView the adapter view
             * @param view the view
             * @param i the index of selected item
             * @param l the id
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Size selected = (Size) ((ArrayAdapter)pizzaSizesDropdown.getAdapter()).getItem(i);
                pizza.setSize(selected);
                price.setText(pizza.priceFormatted());
            }

            /**
             * Place holder
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<Size> pizzaSizesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        pizzaSizesDropdown.setAdapter(pizzaSizesAdapter);
    }

    /**
     * Sets up the description based on the data passed into this Activity
     * @param intent
     */
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

    /**
     * Fills in the toppings in both List Views
     */
    private void updateToppings(){
        ArrayList<Topping> uTop = new ArrayList<Topping>();
        ArrayList<Topping> cTop = new ArrayList<>();
        cTop.addAll(pizza.getToppings());
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

    /**
     * Adds the pizza to the order and passes in the new order information back to the parent
     * @param view button being pressed
     */
    public void orderUp(View view){
        currOrder.addPizza(pizza);
        Toast.makeText(getApplicationContext(), "Added : "+ currOrder.getPizzas().get(0).toString(), Toast.LENGTH_SHORT).show();
        Intent data = new Intent();
        data.putExtra("order", currOrder);
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * This Overrides the onBackPressed method to pass nothing back since nothing is done.
     */
    @Override
    public void onBackPressed() {
        // Activity finished return Cancelled, return the data
        setResult(RESULT_CANCELED, null);
        finish();
        super.onBackPressed();
    }
}
