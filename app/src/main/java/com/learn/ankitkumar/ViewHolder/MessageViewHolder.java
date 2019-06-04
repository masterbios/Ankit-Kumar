package com.learn.ankitkumar.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.learn.ankitkumar.R;

public class MessageViewHolder extends RecyclerView.ViewHolder  {

    public TextView leftmsg, rightmsg;


    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

        leftmsg = itemView.findViewById(R.id.message_incoming);
        rightmsg = itemView.findViewById(R.id.message_outgoing);

    }
}
