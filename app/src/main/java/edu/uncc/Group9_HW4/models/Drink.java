/**
 * Homework 4
 * Group9_HW4
 * Phi Ha
 * Srinath Dittakavi
 */

package edu.uncc.Group9_HW4.models;

import java.util.Date;

public class Drink {
    Date addedOn;
    double drinkSize, alcoholPercentage;

    public Drink() {
    }

    public Drink(Date addedOn, double drinkSize, double alcoholPercentage) {
        this.addedOn = addedOn;
        this.drinkSize = drinkSize;
        this.alcoholPercentage = alcoholPercentage;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "addedOn=" + addedOn +
                ", drinkSize=" + drinkSize +
                ", alcoholPercentage=" + alcoholPercentage +
                '}';
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public double getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(double drinkSize) {
        this.drinkSize = drinkSize;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }
}
