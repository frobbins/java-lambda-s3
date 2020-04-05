package com.frank.aws.mylambdaproject;

import java.util.LinkedList;
import java.util.List;

public class Dessert {

    private String name;
    private List<String> containers = new LinkedList<>();
    private List<String> icecream = new LinkedList<>();
    private List<String> toppings = new LinkedList<>();

    public Dessert() {
    }

    public Dessert(String value) {
        name = value;
    }

    public void setName(String value) {
        name = value;
    }

    public String getName() {
        return name;
    }

    public List<String> getContainers() {
        return containers;
    }

    public void setContainers(List<String> value) {
        this.containers = value;
    }

    public List<String> getIcecream() {
        return icecream;
    }

    public void setIcecream(List<String> icecream) {
        this.icecream = icecream;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> value) {
        this.toppings = value;
    }

    @Override
    public String toString() {
        return "Dessert{" +
                "containers='" + containers + '\'' +
                ", icecream=" + icecream +
                ", toppings=" + toppings +
                '}';
    }
}