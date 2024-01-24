package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Notification;
import com.example.myapplication.NotificationsClickListener;
import com.example.myapplication.R;

import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter <NotificationsViewHolder> {

    Context context;
    List<Notification> list;
    NotificationsClickListener listener;

    public NotificationsListAdapter(Context context, List<Notification> list, NotificationsClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationsViewHolder(LayoutInflater.from(context).inflate(R.layout.notification, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {

        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        holder.notification_container.setOnClickListener(view -> listener.onClick(list.get(holder.getAdapterPosition())));
        holder.notification_container.setOnLongClickListener(view -> {
            listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notification_container);
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList (List<Notification> filteredList) {
        list = filteredList;
    }
}

class NotificationsViewHolder extends RecyclerView.ViewHolder {

    CardView notification_container;
    TextView textView_title, textView_date;

    public NotificationsViewHolder(@NonNull View itemView) {
        super(itemView);

        notification_container = itemView.findViewById(R.id.notes_conteiner);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_date = itemView.findViewById(R.id.textView_date);
    }
}
