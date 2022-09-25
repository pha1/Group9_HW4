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
import android.widget.TextView;
import android.widget.Toast;

import edu.uncc.Group9_HW4.R;
import edu.uncc.Group9_HW4.databinding.FragmentSetProfileBinding;
import edu.uncc.Group9_HW4.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetProfileFragment extends Fragment {
    FragmentSetProfileBinding binding;

    final static String TAG = "test";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "title";

    private String title;
    private Profile profile;
    private String gender;
    private double weight;

    public SetProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title The title of the Fragment
     * @return A new instance of fragment SetProfileFragment.
     */
    public static SetProfileFragment newInstance(String title) {
        SetProfileFragment fragment = new SetProfileFragment();
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
        binding =  FragmentSetProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    TextView editWeight;

    // Function codes go here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(title);

        // default gender selection
        gender = "Female";

        // Check to see which radio button is checked
        // and apply the gender to the display
        binding.genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == binding.radioFemale.getId()){
                    gender = "Female";
                }
                else if (checkedId == binding.radioMale.getId()){
                    gender = "Male";
                }
            }
        });

        editWeight = binding.editWeight;

        // On Click Listener for Set
        binding.setWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Check if the value can be parsed into an int
                    weight = Double.parseDouble(editWeight.getText().toString());
                    Log.d(TAG, "onClick: " + weight);

                    // If the number is less than 0, show the Toast message
                    if (weight < 0.0) {
                        throw new IllegalArgumentException();
                    }

                    // Create the profile if conditions are met
                    Profile profile = new Profile(weight, gender);

                    // If made it here, the profile was successfully created
                    // Used as a breaking point when sending the profile to see where the error
                    // was occurring
                    Log.d("TEST", "onClick: Profile created");

                    // Send profile to Main Activity
                    mListener.sendProfile(profile);

                } catch (Exception e)
                {
                    // Toast message when a positive number is not entered
                    Toast.makeText(getActivity(), "Please enter a valid positive" +
                            " number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // On Click Listener for Cancel
        binding.cancelSetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancel();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SetProfileFragment.IListener){
            mListener = (SetProfileFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    IListener mListener;

    public interface IListener{
        void sendProfile(Profile profile);
        void cancel();
    }
}