package com.example.safood.Fargments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safood.Common.Common;
import com.example.safood.Model.Notification;
import com.example.safood.NotificationInfoActivity;
import com.example.safood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class NotificationsHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryAdapter.HistoryViewClickListener listener;
    private DatabaseReference databaseReference;
    private DatabaseReference notifications;
    private ArrayList<Notification> relevantNotifications = new ArrayList<>();
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications_history, container, false);
        mContext = getContext();
        recyclerView = v.findViewById(R.id.NotificationHistoryRecycler);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,this)
                .addToBackStack(null)
                .commit();

        getDataFromUserProfile();
        return v;
    }



    private void getDataFromUserProfile() {

        Query query = databaseReference.child("Nontifications");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String [] subjects = Common.currentUser.getChoice().split(" ");
                Log.d("size",subjects.length+" -------------------------------------------------------");
                boolean isVegan = false, isInfant = false, isGlutenFree = false, isPets = false, isLactoseFree = false;

                for(int i =0; i<subjects.length;i++){
                    if(subjects[i].equals(getString(R.string.Vegetarianism))){
                        isVegan = true;
                    }
                    if(subjects[i].equals(getString(R.string.Infant))){
                        isInfant = true;
                    }

                    if(subjects[i].equals("Gluten")){
                        isGlutenFree = true;
                    }

                    if(subjects[i].equals(getString(R.string.Pets))){
                        isPets = true;
                    }

                    if(subjects[i].equals("Lactose")){
                        isLactoseFree = true;
                    }
                }

                for(DataSnapshot snapshot1: snapshot.getChildren() ) {
                    Notification notification = snapshot1.getValue(Notification.class);
                    if(isVegan && notification.getSubject().equals(getString(R.string.Vegetarianism))||
                    isInfant && notification.getSubject().equals(getString(R.string.Infant))||
                            isGlutenFree && notification.getSubject().equals(getString(R.string.Gluten_free))||
                            isLactoseFree && notification.getSubject().equals(getString(R.string.Lactose_free))||
                            isPets &&notification.getSubject().equals(getString(R.string.Pets))){
                        relevantNotifications.add(notification);
                    }
                }
                 adapter = new HistoryAdapter(mContext.getApplicationContext(), relevantNotifications, listener);
                 recyclerView.setAdapter(adapter);
                 adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }


        });
    }
}
class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter .ViewHolder> {

    public interface HistoryViewClickListener{
        void onClick(int position);
    }

    private static final String Tag = "RecyclerView";
    private Context nContext;
    private ArrayList<Notification> historyNotificationsList;
    private HistoryViewClickListener listener;


    public HistoryAdapter(Context nContext, ArrayList<Notification> relevantNotificationsList,HistoryViewClickListener listener) {
        this.nContext = nContext;
        this.historyNotificationsList = relevantNotificationsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewnotification, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subject.setText(historyNotificationsList.get(position).getSubject());
        holder.contentNotification.setText(historyNotificationsList.get(position).getNotificationContent());

    }

    @Override
    public int getItemCount() {
        return historyNotificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subject,contentNotification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.currentNotification = historyNotificationsList.get(getAdapterPosition());
                    Common.isHistory=true;
                    Intent intent = new Intent(v.getContext(), NotificationInfoActivity.class);
                    v.getContext().startActivity(intent);
                  /*  if (gamesList.get(getAdapterPosition()).getMaxPlayers() != gamesList.get(getAdapterPosition()).getCurrentPlayers()) {

                    } else {
                        Toast.makeText(nContext, nContext.getResources().getString(R.string.GameIsFullErr), Toast.LENGTH_SHORT).show();
                    }*/
                }
            });


            subject = itemView.findViewById(R.id.titleOfNotification);
            contentNotification = itemView.findViewById(R.id.contentOfNotification);

        }
    }

}