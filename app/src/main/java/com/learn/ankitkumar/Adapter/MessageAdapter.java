package com.learn.ankitkumar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.ankitkumar.Model.MessageModel;
import com.learn.ankitkumar.R;
import com.learn.ankitkumar.Util.MyDiffUtilCallback;
import com.squareup.picasso.Picasso;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context)
               .inflate(R.layout.chatbubble, viewGroup, false);
       ViewHolder myHolder = new ViewHolder(view);

        return myHolder;
    }

    public void update(List<MessageModel> updateList) {
        MyDiffUtilCallback difRef = new MyDiffUtilCallback(list, updateList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(difRef);

        list.clear();
        list.addAll(updateList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

//        System.out.println("adapter " + i);

        MessageModel model = list.get(i);
        viewHolder.left.setText(model.getText());
        if(model.getSender().equals("left")){
            viewHolder.left.setText(model.getText());
            viewHolder.right.setVisibility(View.GONE);
            viewHolder.middle.setVisibility(View.GONE);
            viewHolder.img.setVisibility(View.GONE);
        } else if(model.getSender().equals("right")){
            viewHolder.right.setText(model.getText());
            viewHolder.middle.setVisibility(View.GONE);
            viewHolder.left.setVisibility(View.GONE);
            viewHolder.img.setVisibility(View.GONE);
        } else if (model.getSender().equals("pic")) {
            if (!model.getText().equals("")) {
//                System.out.println(model.getText());
                viewHolder.middle.setVisibility(View.GONE);
                viewHolder.left.setVisibility(View.GONE);
                viewHolder.right.setVisibility(View.GONE);
                Picasso.get().load(model.getText()).into(viewHolder.img);
            } else {
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
            }
        } else {
            viewHolder.middle.setText(model.getText());
            viewHolder.left.setVisibility(View.GONE);
            viewHolder.right.setVisibility(View.GONE);
            viewHolder.img.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView left, right, middle;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.image);
            left = itemView.findViewById(R.id.message_incoming);
            right = itemView.findViewById(R.id.message_outgoing);
            middle = itemView.findViewById(R.id.message_middle);

        }
    }
}
