package com.frank.aws.mylambdaproject;

import java.util.LinkedList;
import java.util.List;

public class Inventory extends Teenager {

    private List<String> containers = new LinkedList<>();
    private List<String> icecream = new LinkedList<>();
    private List<String> toppings = new LinkedList<>();

    public List<String> getContainers() {
        containers.add("sugar cone, waffle cone, shoe, bowl, sundae cup, paper bag");
        return containers;
    }

    public void setContainers(List<String> containers) {
        this.containers = containers;
    }

    public List<String> getIcecream() {
        icecream.add("vanilla, chocolate, strawberry, rocky road, moosetracks, mint, preline");
        return icecream;
    }

    public void setIcecream(List<String> icecream) {
        this.icecream = icecream;
    }

    public List<String> getToppings() {
        toppings.add("pecans, coconut, chocolate syrup, sprinkles, pineapple chunks, oreo pieces, gummy bears");
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }

}
