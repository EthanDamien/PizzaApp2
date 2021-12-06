package pizza_classes;

/**
 * Hawaiian pizza flavor which inherits the pizza class.
 * @author Kevin Cubillos, Ethan Chang
 */
public class HawaiianPizza extends Pizza {
    /** Amount of require toppings needed. **/
    private static final int HAWAIIAN_TOPPING_COUNT = 2;
    /** Initial price of hawaiian pizza. **/
    private static final double HAWAIIAN_INIT_PRICE = 10.99;

    /**
     * Makes an instance of HawaiianPizza starting with all required toppings and size small.
     */
    public HawaiianPizza() {
        toppings.add(Topping.HAM);
        toppings.add(Topping.PINEAPPLE);
        size = Size.SMALL;
    }

    /**
     * Calculates the price of pizza based on given toppings and size.
     * @return the price of the pizza
     */
    public double price(){
        double price = HAWAIIAN_INIT_PRICE;
        int extraToppings = toppings.size() - HAWAIIAN_TOPPING_COUNT;
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
     * String representation of HawaiianPizza.
     * @return the string representation
     */
    @Override
    public String toString(){
        return "Hawaiian " + super.toString();
    }
}
