package com.learn.ankitkumar;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.learn.ankitkumar.Adapter.MessageAdapter;
import com.learn.ankitkumar.Model.MessageModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatArea extends AppCompatActivity {

    Query database;

    List<MessageModel> list;
    List<MessageModel> custom;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView title;
    RelativeLayout root;

    Button retry;
    int counter;

    ProgressDialog mDialog;

    boolean flag = false;

    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_area);

        counter = 1;
        mDialog = new ProgressDialog(this);
        list = new ArrayList<MessageModel>();
        custom = new ArrayList<MessageModel>();

        root = findViewById(R.id.internet);
        retry = findViewById(R.id.internetbutton);
        title = findViewById(R.id.title);
        title.setText("Untold Stories of Dragons");
        database = FirebaseDatabase.getInstance().getReference("stories/story1")
                .orderByChild("timestamp");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(ChatArea.this);
        recyclerView.setLayoutManager(layoutManager);

        checkInternetConnection();

        retry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                checkInternetConnection();
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            private int CLICK_ACTION_THRESHOLD = 200;
            private float startX;
            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();
                        if (isAClick(startX, endX, startY, endY)) {
                            // WE HAVE A CLICK!!
                            if (counter < custom.size()) {
//                                    list.add(custom.get(counter));
                                List<MessageModel> cus = new ArrayList<>();
                                cus.addAll(list);
                                cus.add(custom.get(counter));
                                adapter.update(cus);
                                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
//                                    adapter.notifyItemChanged(counter);
////                                    adapter.notifyItemRangeInserted(counter, counter + 1);
//                                    adapter.notifyDataSetChanged();
                                counter++;
                            }
                        }
                        break;
                }
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

            private boolean isAClick(float startX, float endX, float startY, float endY) {
                float differenceX = Math.abs(startX - endX);
                float differenceY = Math.abs(startY - endY);
                return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
            }
        });

    }


    public void checkInternetConnection() {

        if (isNetworkAvailable()) {
            flag = false;
            loadMessages();
            recyclerView.setVisibility(View.VISIBLE);
            root.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            root.setVisibility(View.VISIBLE);
        }
        if (flag) {
            Toast.makeText(ChatArea.this, "still offline", Toast.LENGTH_SHORT).show();
        }

    }

    public void loadMessages() {
        mDialog.setMessage("Loading...");
        mDialog.show();
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot shot : dataSnapshot.getChildren()) {


                    int i = 0;
                    Iterator<DataSnapshot> iterator = shot.getChildren().iterator();
                    int length = (int) shot.getChildrenCount();
                    String[] sample = new String[length];
                    while (i < length) {
                        sample[i] = iterator.next().getValue().toString();
                        i++;
                    }

                    MessageModel mModel = new MessageModel();
                    mModel.setSender(sample[0]);
                    mModel.setText(sample[1]);
                    mModel.setTimestamp(null);
                    custom.add(mModel);

                    System.out.println(list.size() - 1 + " mModel " + mModel.getText());

                }

                System.out.println(custom.size());
                System.out.println(custom.get(9).getText());
                list.add(custom.get(0));
                mDialog.dismiss();
                adapter = new MessageAdapter(list, ChatArea.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
