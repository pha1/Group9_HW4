/**
 * Homework 4
 * Group9_HW4
 * Phi Ha
 * Srinath Dittakavi
 */

package edu.uncc.Group9_HW4.models;

public class Profile {
    double weight;
    String gender;

    public Profile(double weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "weight=" + weight +
                ", gender='" + gender + '\'' +
                '}';
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
