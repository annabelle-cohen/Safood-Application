package com.example.safood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safood.Common.Common;
import com.example.safood.Model.Article;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Article> articles;
    private DatabaseReference databaseReference;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter.RecyclerViewClickListener listener;
    public static final String ACTIVITY_RESULT_KEY_SECOND="Article";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        recyclerView= findViewById(R.id.articalesRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        articles = new ArrayList<>();
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

        Query query = databaseReference.child("Articales");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren() ) {
                    Article article = snapshot1.getValue(Article.class);
                    articles.add(article);

                }
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(),articles,listener);
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
        Toast.makeText(this, (CharSequence) articles.get(position),Toast.LENGTH_SHORT).show();
        recyclerAdapter.notifyDataSetChanged();
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    public interface RecyclerViewClickListener{
        void onClick(int position);
    }

    private static final String Tag = "RecyclerView";
    private Context nContext;
    private ArrayList<Article> articList;
    private RecyclerViewClickListener listener;



    public RecyclerAdapter(Context nContext, ArrayList<Article> articlesList, RecyclerViewClickListener listener) {
        this.nContext = nContext;
        this.articList = articlesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.title.setText(articList.get(position).getTitle());
        Picasso.with(nContext.getApplicationContext()).load(articList.get(position).getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return articList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Common.currentArticle = articList.get(getAdapterPosition());
                        Intent intent = new Intent(v.getContext(), ArticleInfo.class);
                        v.getContext().startActivity(intent);

                }
            });


            title = itemView.findViewById(R.id.titleOfArticle);
            img = itemView.findViewById(R.id.img);
        }
    }
}