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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.Group9_HW4.R;
import edu.uncc.Group9_HW4.databinding.FragmentViewDrinksBinding;
import edu.uncc.Group9_HW4.models.Drink;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDrinksFragment extends Fragment {

    FragmentViewDrinksBinding binding;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_DRINKS = "drinks_list";

    public static ArrayList<Drink> drinks = new ArrayList<Drink>();

    public int current = 0;
    public Drink drink = new Drink();
    public Drink removedDrink = new Drink();

    TextView currentDrink;
    TextView totalDrinks;
    TextView drinkSize;
    TextView alcoholPercentage;
    TextView date;

    public ViewDrinksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param drinks_list Parameter 1.
     * @return A new instance of fragment ViewDrinkFragment.
     */
    public static ViewDrinksFragment newInstance(ArrayList<Drink> drinks_list) {
        ViewDrinksFragment fragment = new ViewDrinksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DRINKS, drinks_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            drinks = (ArrayList<Drink>) getArguments().getSerializable(ARG_PARAM_DRINKS);
        }
        getActivity().setTitle(getResources().getString(R.string.view_drinks));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentViewDrinksBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    // Function codes go here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initial Drink display
        drink = drinks.get(current);
        updateUI();

        // Click next to get the next drink in the ArrayList
        // If the current drink is the last drink then show the first drink next
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check to see if current drink is the last drink in the list
                // if it is move to the first drink
                if (drinks.get(current).equals(drinks.get(drinks.size()-1))){
                    current = 0;
                    drink = drinks.get(0);
                }
                // if not, next drink
                else {
                    current++;
                    drink = drinks.get(current);
                }
                // Update UI based on new drink position
                updateUI();
            }
        });

        // Click the trash icon to delete the current drink and then show the previous drink
        binding.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the current drink, send and delete it from Main Activity
                removedDrink = drinks.remove(current);
                mListener.deleteDrink(removedDrink);

                Log.d("TEST", "onClick: Number of drinks: " + drinks.size());

                // AFTER REMOVING THE DRINK, CHECK THE ARRAY TO SEE IF THERE ARE MORE DRINKS

                // If there are drinks in the list
                if (drinks.size() > 0) {
                    // One drink left, set current to 0 index
                    if (drinks.size() ==  1) {
                        current = 0;
                        // If there are more than one drink left
                    } else if (drinks.size() > 1) {
                        // If the current drink is the first drink, show the last
                        if (current == 0) {
                            current = drinks.size() - 1;
                            // Show the previous drink
                        } else {
                            current--;
                        }
                    }
                    drink = drinks.get(current);
                    // Update UI based on new drink position
                    updateUI();
                }
                // If there are no drinks in the list
                // Send the updated drink list (empty list)
                else {
                    mListener.emptyList(drinks);
                }

            }
        });

        // Click the previous button to get the previous drink in the ArrayList
        // If the current drink is the first drink, show the last drink
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If this is the first drink
                // Display the last drink in the list
                if(drinks.get(current).equals(drinks.get(0))){
                    current = drinks.size() - 1;
                    drink = drinks.get(drinks.size()-1);
                }
                // Show the previous drink
                else{
                    current--;
                    drink = drinks.get(current);
                }
                // Update the UI based on new position
                updateUI();
            }
        });

        // The close button finishes the activity without returning any extras
        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeViewDrinks(drinks);
            }
        });
    }

    /**
     * Update the TextView values to the new position
     */
    public void updateUI(){

        currentDrink = binding.textviewCurrentDrink;
        currentDrink.setText(String.valueOf(current + 1));

        totalDrinks = binding.textViewTotalDrinks;
        totalDrinks.setText(String.valueOf(drinks.size()));

        drinkSize = binding.textviewDrinkSize;
        drinkSize.setText(String.valueOf(drink.getDrinkSize()));

        alcoholPercentage = binding.textViewPercentage;
        alcoholPercentage.setText(String.valueOf(drink.alcohol_percentage));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
        String dateAddedOn = simpleDateFormat.format(drink.getAddedOn());

        date = binding.textViewDateTime;
        date.setText(dateAddedOn);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ViewDrinksFragment.IListener){
            mListener = (ViewDrinksFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    IListener mListener;

    public interface IListener{
        void deleteDrink(Drink drink);
        void emptyList(ArrayList<Drink> drinks);
        void closeViewDrinks(ArrayList<Drink> drinks);
    }
}