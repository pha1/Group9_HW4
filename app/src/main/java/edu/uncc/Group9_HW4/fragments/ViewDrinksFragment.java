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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.Group9_HW4.DrinkRecyclerViewAdapter;
import edu.uncc.Group9_HW4.R;
import edu.uncc.Group9_HW4.databinding.FragmentViewDrinksBinding;
import edu.uncc.Group9_HW4.models.Drink;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDrinksFragment extends Fragment implements DrinkRecyclerViewAdapter.IDrinkRecycler {

    FragmentViewDrinksBinding binding;

    final static String TAG = "test";

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

    RecyclerView recyclerViewDrinksList;
    LinearLayoutManager layoutManager;
    DrinkRecyclerViewAdapter adapter;

    // Function codes go here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recycler View
        recyclerViewDrinksList = binding.recyclerViewDrinksList;
        recyclerViewDrinksList.setHasFixedSize(true);

        // Layout Manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDrinksList.setLayoutManager(layoutManager);

        // Adapter
        adapter = new DrinkRecyclerViewAdapter(drinks, this);

        // Set the adapter
        recyclerViewDrinksList.setAdapter(adapter);

        // TODO Implement Ascending order by Alcohol percentage
        binding.ascendByAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Algorithm to sort
                Collections.sort(drinks, new Comparator<Drink>() {
                            @Override
                            public int compare(Drink drink, Drink t1) {
                                return Double.compare(drink.getAlcoholPercentage(), t1.getAlcoholPercentage());
                            }
                        });
                        // Notify data set change
                        adapter.notifyDataSetChanged();
            }
        });

        // TODO Implement Descending order by Alcohol percentage
        binding.descendByAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Algorithm to sort

                // Notify data set change
                adapter.notifyDataSetChanged();
            }
        });

        // TODO Implement Ascending order by Date
        binding.ascendByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Algorithm to sort

                // Notify data set change
                adapter.notifyDataSetChanged();
            }
        });

        // TODO Implement Descending order by Date
        binding.descendByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Algorithm to sort

                // Notify data set change
                adapter.notifyDataSetChanged();
            }
        });

        // The close button finishes the activity without returning any extras
        binding.closeViewDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeViewDrinks(drinks);
            }
        });
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

    @Override
    public void deleteDrink(Drink drink) {
        adapter.notifyDataSetChanged();
    }

    public interface IListener{
        void closeViewDrinks(ArrayList<Drink> drinks);
    }
}