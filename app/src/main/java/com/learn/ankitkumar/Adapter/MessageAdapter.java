package com.learn.ankitkumar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learn.ankitkumar.Model.MessageModel;
import com.learn.ankitkumar.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    List<MessageModel> list;
    Context context;

    public MessageAdapter(List<MessageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context)
               .inflate(R.layout.chatbubble, viewGroup, false);
       ViewHolder myHolder = new ViewHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        MessageModel model = list.get(i);
        if(model.getSender().equals("left")){
            viewHolder.left.setText(model.getText());
            viewHolder.right.setVisibility(View.GONE);
            viewHolder.middle.setVisibility(View.GONE);
        } else if(model.getSender().equals("right")){
            viewHolder.right.setText(model.getText());
            viewHolder.middle.setVisibility(View.GONE);
            viewHolder.left.setVisibility(View.GONE);
        } else {
            viewHolder.middle.setText(model.getText());
            viewHolder.left.setVisibility(View.GONE);
            viewHolder.right.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView left, right, middle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            left = itemView.findViewById(R.id.message_incoming);
            right = itemView.findViewById(R.id.message_outgoing);
            middle = itemView.findViewById(R.id.message_middle);

        }
    }
}
