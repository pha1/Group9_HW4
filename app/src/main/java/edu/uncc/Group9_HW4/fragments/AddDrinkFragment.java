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
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.uncc.Group9_HW4.R;
import edu.uncc.Group9_HW4.databinding.FragmentAddDrinkBinding;
import edu.uncc.Group9_HW4.models.Drink;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDrinkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDrinkFragment extends Fragment {

    FragmentAddDrinkBinding binding;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "TITLE";

    private String title;
    public static double alcohol_percentage = 0;
    public static int drinkSize;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;

    public AddDrinkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment AddDrinkFragment.
     */
    public static AddDrinkFragment newInstance(String title) {
        AddDrinkFragment fragment = new AddDrinkFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddDrinkBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    SeekBar seekBar;
    TextView progress;
    RadioGroup drink_size_group;

    // Function codes go here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(title);

        // SEEKBAR/ALCOHOL PERCENTAGE
        seekBar = binding.seekBar;
        progress = binding.viewProgress;

        drink_size_group = binding.drinkSizeGroup;
        // Default drink size option
        drinkSize = 1;

        // When the user clicks on a new size, the drink size is updated
        drink_size_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // If 1 oz is checked
                if (checkedId == R.id.one_oz){
                    drinkSize = 1;
                }
                // If 5 oz is checked
                else if (checkedId == R.id.five_oz){
                    drinkSize = 5;
                }
                // If 12 oz is checked
                else if (checkedId == R.id.twelve_oz){
                    drinkSize = 12;
                }
            }
        });

        // Initiate the text value for the progress
        progress.setText("0%");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            // As the seekbar is used, the percentage displayed is updated
            // Sets the Drink object's alcohol percentage value
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress.setText(i + "%");
                alcohol_percentage = (double)i/100.0;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.addDrinkButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a date and time stamp for the object as it is created
                calendar = Calendar.getInstance();

                // Create Drink object with selected data
                Drink drink = new Drink(calendar.getTime(), drinkSize, alcohol_percentage);

                // Send the drink to Main Activity
                mListener.sendDrink(drink);
            }
        });

        // Listener for cancel button
        binding.cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancel();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddDrinkFragment.IListener){
            mListener = (AddDrinkFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    IListener mListener;

    public interface IListener{
        void sendDrink(Drink drink);
        void cancel();
    }
}