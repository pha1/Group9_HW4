package edu.uncc.Group9_HW4;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.Group9_HW4.models.Drink;

public class DrinkRecyclerViewAdapter extends RecyclerView.Adapter<DrinkRecyclerViewAdapter.DrinkViewHolder> {

    ArrayList<Drink> drinks;
    SimpleDateFormat simpleDateFormat;

    public DrinkRecyclerViewAdapter(ArrayList<Drink> data){
        this.drinks = data;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_row_item, parent, false);

        // Create ViewHolder with view
        DrinkViewHolder drinkViewHolder = new DrinkViewHolder(view);

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

        holder.drinkSize.setText(String.valueOf(drink.getDrinkSize()));

    }

    @Override
    public int getItemCount() {
        return this.drinks.size();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPercent;
        TextView dateAndTime;
        TextView drinkSize;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPercent = itemView.findViewById(R.id.textViewPercent);
            dateAndTime = itemView.findViewById(R.id.dateAndTime);
            drinkSize = itemView.findViewById(R.id.textViewDrinkSize);
        }
    }
}
