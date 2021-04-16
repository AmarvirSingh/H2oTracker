package com.example.h2otracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    List<String> time;
    List<String> amount;
    Context context;
    LayoutInflater inflator;

    public Adapter(Context context,List<String> amount,List<String> time)
    {
        this.amount = amount;
        this.time = time;
        this.context = context;
        this.inflator = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.history_detail_cell,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cell_time.setText(time.get(position));
       holder.cell_amount.setText(amount.get(position));


    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
{
    TextView cell_amount, cell_time;
    ImageView gridIcon;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        cell_amount = itemView.findViewById(R.id.cell_amount);
        cell_time = itemView.findViewById(R.id.cell_time);

    }
}
}
