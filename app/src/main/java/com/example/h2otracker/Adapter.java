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
    List<String> titles;
    List<Integer> images;
    Context context;
    LayoutInflater inflator;

    public Adapter(Context context,List<String> titles,List<Integer> images)
    {
        this.titles = titles;
        this.images = images;
        this.context = context;
        this.inflator = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_grid_layout,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+titles.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
{
    TextView title;
    ImageView gridIcon;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.textView2);
        gridIcon = itemView.findViewById(R.id.imageView5);

    }
}
}
