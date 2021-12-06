package pizza_classes;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represents an order.
 * @author Ethan Chang and Kevin Cubillos
 */
public class Order {

    /** When there are no pizzas in order **/
    public static final int EMPTY = 0;
    /** Length of phone number that is valid **/
    public static final int PHONE_NUMBER_LENGTH = 10;
    /** The dollar format. **/
    private static final String FORMAT = "##,##0.00";
    /** Formatter that formats a number to the dollar format. **/
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(FORMAT);
    /** Sales tax amount. **/
    private static final double SALES_TAX = 0.06625;
    /** When an order is empty. **/
    private static final double EMPTY_ORDER_TOTAL = 0.00;
    /** Phone number with the order. **/
    private String phoneNumber;
    /** List of pizzas with the order. **/
    private ArrayList<Pizza> pizzas;
    /** Current price of order. **/
    private double currentPrice;

    /**
     * Constructor for Order.
     * @param phoneNumber The phone number of the customer.
     */
    public Order(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.pizzas = new ArrayList<>();
        this.currentPrice = EMPTY_ORDER_TOTAL;
    }

    /**
     * Gets the phone number of the customer.
     * @return The phone number of the customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the pizzas in the order.
     * @return The pizzas in the order.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Phone Number: ").append(phoneNumber).append("\n");
        for (Pizza pizza : pizzas) {
            sb.append(pizza.toString()).append("\n");
        }
        sb.append("Amount of Pizzas: ").append(pizzas.size()).append("\n");
        sb.append("Subtotal: $").append(getSubtotal()).append("\t Sales Tax: $").append(getTax()).append("\n");
        sb.append("Total: $").append(getFinalPrice()).append("\n");
        return sb.toString();
    }

    /**
     * Adds a pizza to the order.
     * @param pizza The pizza to add.
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        currentPrice += pizza.price();
    }

    /**
     * Remove a pizza from the order.
     * @param i The pizza to remove.
     */
    public void removePizza(int i) {
        Pizza removed = pizzas.remove(i);
        currentPrice -= removed.price();
        if(currentPrice < 0){
            currentPrice = 0;
        }
    }

    /**
     * Calculates the total of the order with tax.
     * @return The total of the order with tax applied.
     */
    public String getFinalPrice() {
        return DECIMAL_FORMAT.format(currentPrice + currentPrice * SALES_TAX);
    }

    /**
     * Get the subtotal of order.
     * @return subtotal
     */
    public String getSubtotal(){
        return DECIMAL_FORMAT.format(currentPrice);
    }

    /**
     * Get the sales tax total of order.
     * @return amount of sales tax
     */
    public String getTax(){
        return DECIMAL_FORMAT.format(currentPrice * SALES_TAX);
    }

    /**
     * Get the list of pizzas from order.
     * @return list of pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Get the amount of pizzas in order.
     * @return amount of pizzas
     */
    public int size(){
        return pizzas.size();
    }
}
