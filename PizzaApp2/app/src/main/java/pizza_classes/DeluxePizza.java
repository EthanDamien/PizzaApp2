package pizza_classes;

/**
 * Deluxe pizza flavor which inherits the pizza class.
 * @author Kevin Cubillos, Ethan Chang
 */
public class DeluxePizza extends Pizza {

    /** Amount of require toppings needed. **/
    private static final int DELUXE_TOPPING_COUNT = 5;
    /** Initial price of deluxe pizza. **/
    private static final double DELUXE_INIT_PRICE = 12.99;

    /**
     * Makes an instance of DeluxePizza starting with all required toppings and size small.
     */
    public DeluxePizza() {
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
        toppings.add(Topping.BACON);
        toppings.add(Topping.HAM);
        size = Size.SMALL;
    }

    /**
     * Calculates the price of pizza based on given toppings and size.
     * @return the price of the pizza
     */
    public double price(){
        double price = DELUXE_INIT_PRICE;
        int extraToppings = toppings.size() - DELUXE_TOPPING_COUNT;
        if(extraToppings > Pizza.NO_EXTRA_TOPPINGS){
            price += extraToppings * ADD_TOPPING_PRICE;
        }
        switch(size){
            case SMALL:
                break;
            case MEDIUM:
                price += SIZE_INCREASE_UPCHARGE;
                break;
            case LARGE:
                price += SIZE_INCREASE_UPCHARGE + SIZE_INCREASE_UPCHARGE;
                break;
        }
        return price;
    }

    /**
     * String representation of DeluxePizza.
     * @return the string representation
     */
    @Override
    public String toString(){
        return "Deluxe " + super.toString();
    }
}
