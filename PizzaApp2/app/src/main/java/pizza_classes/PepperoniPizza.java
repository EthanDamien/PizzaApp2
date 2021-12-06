package pizza_classes;

/**
 * Pepperoni pizza flavor which inherits the pizza class.
 * @author Kevin Cubillos, Ethan Chang
 */
public class PepperoniPizza extends Pizza {

    /** Amount of require toppings needed. **/
    private static final int PEPPERONI_TOPPING_COUNT = 1;
    /** Initial price of pepperoni pizza. **/
    private static final double PEPPERONI_INIT_PRICE = 8.99;

    /**
     * Makes an instance of PepperoniPizza starting with all required toppings and size small.
     */
    public PepperoniPizza() {
        toppings.add(Topping.PEPPERONI);
        size = Size.SMALL;
    }

    /**
     * Calculates the price of pizza based on given toppings and size.
     * @return the price of the pizza
     */
    public double price(){
        double price = PEPPERONI_INIT_PRICE;
        int extraToppings = toppings.size() - PEPPERONI_TOPPING_COUNT;
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
     * String representation of PepperoniPizza.
     * @return the string representation
     */
    @Override
    public String toString(){
        return "Pepperoni " + super.toString();
    }
}
