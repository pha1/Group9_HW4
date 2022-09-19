/**
 * Homework 4
 * Group9_HW4
 * Phi Ha
 * Srinath Dittakavi
 */

package edu.uncc.Group9_HW4.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uncc.Group9_HW4.R;
import edu.uncc.Group9_HW4.databinding.FragmentBacBinding;
import edu.uncc.Group9_HW4.models.Drink;
import edu.uncc.Group9_HW4.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BACFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BACFragment extends Fragment {
    FragmentBacBinding binding;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "title";

    private String title;
    private Profile profile;
    private ArrayList<Drink> drinks = new ArrayList<>();

    public BACFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title The title of the Fragment.
     * @return A new instance of fragment BacCalculatorFragment.
     */
    public static BACFragment newInstance(String title) {
        BACFragment fragment = new BACFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM_TITLE);
        }
    }

    TextView weightDisplay;
    TextView numDrinkDisplay;
    TextView status;
    TextView bacLevel;

    public static double bac = 0;

    Boolean enableViewDrinks = false;
    Boolean enableAddDrink = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBacBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    // Function codes go here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the Title of the Action
        getActivity().setTitle(title);

        weightDisplay = binding.weightDisplay;
        numDrinkDisplay = binding.numDrinkDisplay;
        bacLevel = binding.bacLevel;

        // Initial Display
        weightDisplay.setText(getResources().getString(R.string.weight_display));
        bacLevel.setText(getResources().getString(R.string.BAC_num));

        // Allow the interface to listen to click events
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.changeFragmentListener("Set Profile");
            }
        });

        //
        binding.viewDrinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.changeFragmentListener("View Drinks");
            }
        });

        binding.addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.changeFragmentListener("Add Drink");
            }
        });

        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    // When returning to the BAC Calculator Fragment, update the UI as nededed base on given
    // changes
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.app_name));

        // A profile is created only when weight and gender is set
        // The profile is default at 0, so any changes will indicate there is a profile set
        if (profile.getWeight() > 0){
            // Displays the weight and gender of the profile
            String weightGender;
            weightGender = profile.getWeight() + " lbs (" + profile.getGender() + ")";
            weightDisplay.setText(weightGender);
        }

        // If there is a drink in the ArrayList, update the BAC UI
        if (drinks.size() > 0) {
            bac = calculateBAC(profile, drinks);
            updateBacUI(bac);
        }

        // Display the number of drinks
        numDrinkDisplay.setText(String.valueOf(drinks.size()));

        // If a profile is set, then the "View Drinks button" is enabled
        if (enableViewDrinks) {
            binding.viewDrinksButton.setEnabled(true);
        }

        // If a profile is set the "Add Drink" button becomes enabled
        // If the BAC Level falls below 0.25, the "Add Drink" button will be enabled
        if (enableAddDrink) {
            binding.addDrinkButton.setEnabled(true);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BACFragment.IListener){
            mListener = (IListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    IListener mListener;

    /**
     * This method updates the profile object in this fragment from the Main Activity
     * @param profile The updated profile of the user
     */
    public void updateProfile(Profile profile) {
        this.profile = profile;
    }

    public interface IListener {
        void changeFragmentListener(String fragment);
    }

    /**
     * This method updates the drinks list from the Main Activity
     * @param drinks The updated drinks list
     */
    public void updateDrinksList(ArrayList<Drink> drinks){
        this.drinks = drinks;
    }

    /**
     * Used to unlock the Add Drink and View Drinks buttons under a certain condition
     */
    public void unlockButtons(){
        this.enableViewDrinks = true;
        this.enableAddDrink = true;
    }

    public void enableAddDrinks(Boolean enableAddDrinks){
        this.enableAddDrink = enableAddDrinks;
    }

    /**
     * This method resets all stored values on the BAC Calculator
     */
    public void reset() {

        // Clear the User Profile
        profile.setGender("");
        profile.setWeight(0.0);

        // Set components' values back to default
        weightDisplay.setText(getResources().getText(R.string.weight_display));
        numDrinkDisplay.setText(getResources().getText(R.string.num_drinks));

        // Disable buttons
        binding.viewDrinksButton.setEnabled(false);
        binding.addDrinkButton.setEnabled(false);

        // Boolean values
        enableViewDrinks = false;
        enableAddDrink = false;

        // Calls clearUI method
        clearUI();

        Log.d("TEST", "onClick: clearUI successful");
        Log.d("TEST", "onClick: Drinks: " + drinks.size() + ", Profile weight: " +
                profile.getWeight() + ", Profile gender: " + profile.getGender());
    }

    /**
     * This method clears the UI when setting the profile
     * Number of drinks, BAC Level, BAC status message
     */
    public void clearUI(){
        // Clear Drinks List
        drinks.clear();

        // Set status message to default via strings.xml
        status = binding.status;
        status.setText(getResources().getText(R.string.status));
        status.setBackgroundColor(getResources().getColor(R.color.green));

        // Set BAC Level to 0.000 via strings.xml
        bacLevel = binding.bacLevel;
        bacLevel.setText(getResources().getText(R.string.BAC_num));
        bac = 0;
    }

    /**
     * This method calculates the BAC Level with the given formula
     * % BAC = A * 5.14 / Weight * r
     * @param profile the profile of the user
     * @param drinks the list of drinks
     * @return double value of the % BAC
     */
    public double calculateBAC(Profile profile, ArrayList<Drink> drinks){
        double bac;
        double r;
        double a = 0;

        // Set the r value for female
        if (profile.getGender().equals("Female")){
            r = .66;
        }
        // Set the r value for male
        else {
            r = .73;
        }

        for (Drink drink : drinks){
            a += drink.getDrinkSize() * drink.getAlcoholPercentage();
        }

        // BAC Formula
        bac = (a * 5.14) / (profile.getWeight() * r);

        return bac;
    }

    /**
     * This method checks the BAC Level to determine the status message and updates the UI
     * @param bac double value used to determine the status
     */
    public void updateBacUI(double bac){

        TextView numDrinks = binding.numDrinkDisplay;
        TextView status = binding.status;

        // NUMBER OF DRINKS AND BAC LEVEL
        numDrinks.setText(String.valueOf(drinks.size()));
        bacLevel.setText(String.format("%.3f", bac));

        // STATUS MESSAGE

        // If the bac drops to 0.08 or lower, status is set to green and text: "You're safe."
        if (0 <= bac && bac <= 0.08) {
            status.setText(getResources().getText(R.string.status));
            status.setBackgroundColor(getResources().getColor(R.color.green));
            enableAddDrinks(true);
            Log.d("TEST", "updateBacUI: Green successful");
        }
        // Sets the status to "Be careful." and changes the color to orange
        else if (0.08 < bac && bac <= 0.2){
            status.setText(getResources().getText(R.string.status2));
            status.setBackgroundColor(getResources().getColor(R.color.orange));
            enableAddDrinks(true);
            Log.d("TEST", "updateBacUI: Orange successful");
        }

        // Sets the status to "Over the limit!" and changes the color to red
        else {
            status.setText(getResources().getText(R.string.status3));
            status.setBackgroundColor(getResources().getColor(R.color.red));

            // Once the BAC reaches over 0.25, display Toast Message "No more drinks for you"
            // This will disable the "Add Drink" button
            if (bac >= 0.25) {
                Toast.makeText(getActivity(), "No more drinks for you.", Toast.LENGTH_LONG).show();
                enableAddDrinks(false);
                Log.d("TEST", "updateBacUI: Red successful");
            }
            // If the bac drops below .25 enable "Add Drink" button
            else {
                enableAddDrinks(true);
            }
        }
    }
}