package com.learn.ankitkumar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learn.ankitkumar.Adapter.MessageAdapter;
import com.learn.ankitkumar.Model.MessageModel;
import com.learn.ankitkumar.ViewHolder.MessageViewHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatArea extends AppCompatActivity {

    DatabaseReference database;

    List<MessageModel> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_area);

        title = findViewById(R.id.title);
        title.setText("Untold Stories of Dragons");
        database = FirebaseDatabase.getInstance().getReference("stories/story1");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<MessageModel>();
                for(DataSnapshot shot : dataSnapshot.getChildren()){

                    int i = 0;
                    Iterator<DataSnapshot> iterator = shot.getChildren().iterator();
                    int length = (int) shot.getChildrenCount();
                    String[] sample = new String[length];
                    while (i < length){
                        sample[i] = iterator.next().getValue().toString();
                        i++;
                    }

                    MessageModel mModel = new MessageModel();
                    mModel.setSender(sample[0]);
                    mModel.setText(sample[1]);
                    mModel.setTimestamp(null);
                    list.add(mModel);
                }

                System.out.println(list.size());

                MessageAdapter adapter = new MessageAdapter(list, ChatArea.this);
                layoutManager = new LinearLayoutManager(ChatArea.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        String x = String.valueOf(list.size());
//        System.out.println(list);
//
//        Toast.makeText(ChatArea.this, x, Toast.LENGTH_SHORT).show();

//        for(MessageModel m : list){
//            System.out.println(m.getText() + " " + m.getSender());
//
//        }


//        loadChats();

    }

//    private void loadChats() {
//
//        adapter = new FirebaseRecyclerAdapter<MessageModel, MessageViewHolder>
//                (MessageModel.class,R.layout.recieverlayout,MessageViewHolder.class, database) {
//            @Override
//            protected void populateViewHolder(MessageViewHolder viewHolder, MessageModel model, int position) {
//
////                Message.Pack pRef = model.getList();
//
//            }
//        };

//    }
}
