/**
 * Homework 4
 * Group9_HW4
 * Phi Ha
 * Srinath Dittakavi
 */

package edu.uncc.Group9_HW4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uncc.Group9_HW4.databinding.ActivityMainBinding;
import edu.uncc.Group9_HW4.fragments.AddDrinkFragment;
import edu.uncc.Group9_HW4.fragments.BACFragment;
import edu.uncc.Group9_HW4.fragments.SetProfileFragment;
import edu.uncc.Group9_HW4.fragments.ViewDrinksFragment;
import edu.uncc.Group9_HW4.models.Drink;
import edu.uncc.Group9_HW4.models.Profile;

public class MainActivity extends AppCompatActivity implements BACFragment.IListener, SetProfileFragment.IListener, AddDrinkFragment.IListener, ViewDrinksFragment.IListener{
    ActivityMainBinding binding;

    public static ArrayList<Drink> drinks = new ArrayList<>();
    Profile profile;

    BACFragment bacCalculator = new BACFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new BACFragment())
                .commit();
    }

    /**
     * This method implements the changeFragmentListener through the various interfaces in the Fragments
     * included in this project. It communicates with the Main Activity to replace the current Fragment
     * with the desired Fragment
     * @param fragment the String parameter that specifies which Fragment to change to
     */
    @Override
    public void changeFragmentListener(String fragment) {

        // If Set Profile String
        if (fragment.equals("Set Profile"))
        {
            // Change Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, SetProfileFragment.newInstance("Set Weight/Gender"), "Set Profile Fragment")
                    .addToBackStack(null)
                    .commit();
        }

        // If View Drinks String
        if (fragment.equals("View Drinks"))
        {
            if (drinks.size() > 0){
                // Change Fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerView, ViewDrinksFragment.newInstance(drinks), "View Drinks Fragment")
                        .addToBackStack(null)
                        .commit();
            }
            // If no drinks in the ArrayList - Toast Message "You've had no drinks."
            else {
                Log.d("TEST", "onClick: No drinks");
                Toast.makeText(MainActivity.this, "You have no drinks", Toast.LENGTH_SHORT).show();
            }
        }

        // If Add Drinks String
        if (fragment.equals("Add Drink"))
        {
            // Change Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, AddDrinkFragment.newInstance("Add Drink"), "Add Drinks Fragment")
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * This method implements the deleteDrink method from ViewDrinksFragment
     * It receives the Drink object to be deleted from the Arraylist of Drinks
     * @param drink The Drink object passed on from ViewDrinksFragment
     */
    @Override
    public void deleteDrink(Drink drink) {
        this.drinks.remove(drink);
    }

    /**
     * This method implements the emptyList method from ViewDrinksFragment
     * It receives the drinks list from ViewDrinksFragment and stores it in the Main Activity,
     * then send the updated drink list to BAC Calculator Fragment
     * @param drinks ArrayList of Drink objects
     */
    @Override
    public void emptyList(ArrayList<Drink> drinks) {
        this.drinks = drinks;
        bacCalculator = (BACFragment) getSupportFragmentManager().findFragmentByTag("BAC Calculator Fragment");
        bacCalculator.updateDrinksList(this.drinks);
        getSupportFragmentManager().popBackStack();
    }

    /**
     * This method receives the drink list when the user closes the View Drinks Fragment, stores the
     * drink list to the Main Activity, and sends the updated ArrayList to BAC Calculator Fragment
     * @param drinks ArrayList of Drink objects
     */
    @Override
    public void closeViewDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
        bacCalculator = (BACFragment) getSupportFragmentManager().findFragmentByTag("BAC Calculator Fragment");
        bacCalculator.updateDrinksList(this.drinks);
        getSupportFragmentManager().popBackStack();
    }

    /**
     * This method receives the Profile from SetProfileFragment and stores it in the Main Activity,
     * then sends it to the BAC Calculator Fragment
     * @param profile The Profile object used to store the user's weight and gender
     */
    @Override
    public void sendProfile(Profile profile) {
        this.profile = profile;
        bacCalculator = (BACFragment) getSupportFragmentManager().findFragmentByTag("BAC Calculator Fragment");
        bacCalculator.updateProfile(this.profile);
        bacCalculator.unlockButtons();
        getSupportFragmentManager().popBackStack();
    }

    /**
     * This method receives the Drink object from AddDrinkFragment and adds it into the drinks
     * ArrayList in the Main Activity, then updates the BAC Calculator's drink list
     * @param drink The Drink object to be added to the Main Activity's ArrayList
     */
    @Override
    public void sendDrink(Drink drink) {
        drinks.add(drink);
        bacCalculator =(BACFragment) getSupportFragmentManager().findFragmentByTag("BAC Calculator Fragment");
        bacCalculator.updateDrinksList(drinks);
        getSupportFragmentManager().popBackStack();
    }

    /**
     * This method tells the Main Activity to pop the BackStack to retrieve the last Fragment
     * in the stack
     */
    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }
}