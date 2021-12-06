package pizza_classes;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represents a Pizza.
 * @author Kevin Cubillos, Ethan Chang
 */
public abstract class Pizza {

    /** The amount of price upcharge for a size increase. **/
    protected static final double SIZE_INCREASE_UPCHARGE = 2.00;
    /** Price of an additional topping. **/
    protected static final double ADD_TOPPING_PRICE = 1.49;
    /** When there are no extra toppings **/
    protected static final int NO_EXTRA_TOPPINGS = 0;
    /** Max amount of toppings a pizza can have. **/
    private static final int MAX_TOPPINGS = 7;
    /** The list of toppings currently with pizza. **/
    protected ArrayList<Topping> toppings = new ArrayList<>();
    /** Current size of pizza. **/
    protected Size size;

    /**
     * Abstract method implemented by pizza flavor classes to calculate the price.
     * @return the price of the pizza
     */
    public abstract double price();

    /**
     * Adds a topping to the pizza.
     * @param topping The topping to be added.
     * @return True if topping was added, false if topping was not added.
     */
    public boolean addTopping(Topping topping){
        if(toppings.size() < MAX_TOPPINGS){
            toppings.add(topping);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Removes a topping from this pizza
     * @param topping the topping to remove
     */
    public void removeTopping(Topping topping){
        toppings.remove(topping);
    }

    /**
     * Changes the size of the pizza.
     * @param size The new size of the pizza.
     */
    public void setSize(Size size){
        this.size = size;
    }

    /**
     *This builds a string representation of the pizza with the toppings
     * @return a string representation of the pizza
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Pizza: ");
        sb.append(size.toString()).append(", ");
        for (Topping topping : toppings) {
            sb.append(topping).append(", ");
        }
        sb.append("Cost: $").append(priceFormatted());
        return sb.toString();
    }

    /**
     * Getter for the list of toppings.
     * @return list of toppings
     */
    public ArrayList<Topping> getToppings(){
        return toppings;
    }

    /**
     * Gives the price in dollar format.
     * @return dollar formatted price
     */
    public String priceFormatted(){
        return Order.DECIMAL_FORMAT.format(price());
    }

}
