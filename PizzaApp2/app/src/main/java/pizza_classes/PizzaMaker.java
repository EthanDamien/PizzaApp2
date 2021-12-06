package pizza_classes;

/**
 * Class that handles instantiation of correct pizza flavor.
 * @author Kevin Cubillos, Ethan Chang
 */
public class PizzaMaker {

    /**
     * Creates an instance of the correct pizza flavor
     * @param flavor Flavor of pizza
     * @return the instance of pizza flavor
     */
    public static Pizza createPizza(String flavor) {
        Pizza pizza;
        switch (flavor){
            case "Deluxe Pizza":
                pizza = new DeluxePizza();
                return pizza;
            case "Hawaiian Pizza":
                pizza = new HawaiianPizza();
                return pizza;
            case "Pepperoni Pizza":
                pizza = new PepperoniPizza();
                return pizza;
            default:
                pizza = null;
                return pizza;
        }
    }
}
