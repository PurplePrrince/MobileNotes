package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.WelcomeClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WelcomeAdapter extends RecyclerView.Adapter <WelcomeViewHolder> {

    Context context;

    WelcomeClickListener listener;

    public WelcomeAdapter(Context context, WelcomeClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WelcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WelcomeViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeViewHolder holder, int position) {
        holder.fab_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class WelcomeViewHolder extends RecyclerView.ViewHolder {


    TextView welcome_name;
    ImageView welcome_img;

    FloatingActionButton fab_go;

    public WelcomeViewHolder(@NonNull View itemView) {
        super(itemView);

        fab_go = itemView.findViewById(R.id.fab_go);
        welcome_name = itemView.findViewById(R.id.welcome_name);
        welcome_img = itemView.findViewById(R.id.welcome_img);
    }
}
