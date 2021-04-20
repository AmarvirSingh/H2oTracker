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

import com.example.h2otracker.HelperClass.HelperClass;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
{
   ArrayList<HistoryClass> historyClassArrayList = null;
    Context context;
    HelperClass helperClass;
    LayoutInflater inflator;

    public HistoryAdapter(Context context,ArrayList<HistoryClass> historyClassArrayList,HelperClass helperClass)
    {

        this.context = context;
        this.historyClassArrayList = historyClassArrayList;
        this.helperClass = helperClass;
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

        holder.cell_time.setText(historyClassArrayList.get(position).getTime());
       holder.cell_amount.setText(historyClassArrayList.get(position).getAmount());
       holder.cell_type.setText(historyClassArrayList.get(position).getType());

    }

    @Override
    public int getItemCount() {
        return historyClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
{
    TextView cell_amount, cell_time, cell_type;



    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        cell_amount = itemView.findViewById(R.id.cell_amount);
        cell_time = itemView.findViewById(R.id.cell_time);
        cell_type = itemView.findViewById(R.id.cell_type);
    }
}
}
