package edu.uncc.Group9_HW4;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.Group9_HW4.models.Drink;

public class DrinkRecyclerViewAdapter extends RecyclerView.Adapter<DrinkRecyclerViewAdapter.DrinkViewHolder> {

    final static String TAG = "test";

    ArrayList<Drink> drinks;
    SimpleDateFormat simpleDateFormat;
    IDrinkRecycler mListener;

    public DrinkRecyclerViewAdapter(ArrayList<Drink> data, IDrinkRecycler mListener) {
        this.drinks = data;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_row_item, parent, false);

        // Create ViewHolder with view
        DrinkViewHolder drinkViewHolder = new DrinkViewHolder(view, mListener);

        return drinkViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {

        Drink drink = drinks.get(position);

        holder.textViewPercent.setText(String.valueOf(drink.getAlcoholPercentage()));

        // Get the Date and Time data and format it into a String
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
        String date = simpleDateFormat.format(drink.getAddedOn());
        holder.dateAndTime.setText(date);
        holder.position = position;
        holder.drink = drink;
        holder.drinks = drinks;
        holder.drinkSize.setText(String.format("%.0f", drink.getDrinkSize()));

    }

    @Override
    public int getItemCount() {
        return this.drinks.size();
    }

    public interface IDrinkRecycler {
        void deleteDrink(Drink drink);
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPercent;
        TextView dateAndTime;
        TextView drinkSize;

        View rootView;
        IDrinkRecycler mListener;

        int position;
        ArrayList<Drink> drinks;
        Drink drink;
        Drink removedDrink;

        public DrinkViewHolder(@NonNull View itemView, IDrinkRecycler mListener) {
            super(itemView);

            rootView = itemView;
            this.mListener = mListener;

            textViewPercent = itemView.findViewById(R.id.textViewPercent);
            dateAndTime = itemView.findViewById(R.id.dateAndTime);
            drinkSize = itemView.findViewById(R.id.textViewDrinkSize);

            itemView.findViewById(R.id.trashIcon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: TrashIcon was clicked for " + getAdapterPosition());

                    // Click the trash icon to delete the current drink and then show the previous drink
                    // Remove the current drink, send and delete it from Main Activity
                    removedDrink = drinks.remove(position);
                    Log.d(TAG, "onClick: " + removedDrink.getDrinkSize());
                    mListener.deleteDrink(removedDrink);
                }
            });
        }
    }
}
