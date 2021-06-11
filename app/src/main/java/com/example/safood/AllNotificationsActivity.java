package com.example.safood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safood.Common.Common;
import com.example.safood.Model.Article;
import com.example.safood.Model.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllNotificationsActivity extends AppCompatActivity  implements RecyclerNotificationAdapter.RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Notification> notificationList;
    private DatabaseReference databaseReference;
    private RecyclerNotificationAdapter recyclerAdapter;
    private RecyclerNotificationAdapter.RecyclerViewClickListener listener;
    public static final String ACTIVITY_RESULT_KEY_SECOND="Notifications";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notifications);
        recyclerView= findViewById(R.id.allNotificationsRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        notificationList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(recyclerAdapter != null){
            recyclerAdapter.notifyDataSetChanged();
        }

        getDataFromFirebase();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromFirebase();
    }


    private void getDataFromFirebase() {

        Query query = databaseReference.child("Nontifications");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren() ) {
                    Notification notification = snapshot1.getValue(Notification.class);
                    notificationList.add(notification);

                }
                recyclerAdapter = new RecyclerNotificationAdapter(getApplicationContext(),notificationList,listener);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeSafood.class));
        finish();
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, (CharSequence) notificationList.get(position),Toast.LENGTH_SHORT).show();
        recyclerAdapter.notifyDataSetChanged();
    }
}

class RecyclerNotificationAdapter extends RecyclerView.Adapter<RecyclerNotificationAdapter.ViewNotificationHolder>{

    public interface RecyclerViewClickListener{
        void onClick(int position);
    }

    private static final String Tag = "RecyclerView";
    private Context nContext;
    private ArrayList<Notification> noticList;
    private RecyclerViewClickListener listener;



    public RecyclerNotificationAdapter(Context nContext, ArrayList<Notification> notificationsList, RecyclerViewClickListener listener) {
        this.nContext = nContext;
        this.noticList = notificationsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerNotificationAdapter.ViewNotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewnotification, parent, false);
        return new ViewNotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerNotificationAdapter.ViewNotificationHolder holder, int position) {
        holder.subject.setText(noticList.get(position).getSubject());
        holder.contentNotification.setText(noticList.get(position).getNotificationContent());
    }

    @Override
    public int getItemCount() {
        return noticList.size();
    }
    public class ViewNotificationHolder extends RecyclerView.ViewHolder{
        private TextView subject,contentNotification;


        public ViewNotificationHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.currentNotification = noticList.get(getAdapterPosition());
                    Common.isHistory=false;
                     Intent intent = new Intent(v.getContext(), NotificationInfoActivity.class);
                     v.getContext().startActivity(intent);
                }
            });


            subject = itemView.findViewById(R.id.titleOfNotification);
            contentNotification = itemView.findViewById(R.id.contentOfNotification);
        }
    }
}