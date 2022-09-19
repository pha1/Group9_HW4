/**
 * Homework 4
 * Group9_HW4
 * Phi Ha
 * Srinath Dittakavi
 */

package edu.uncc.Group9_HW4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.uncc.Group9_HW4.databinding.ActivityMainBinding;
import edu.uncc.Group9_HW4.fragments.BACFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new BACFragment())
                .commit();
    }
}