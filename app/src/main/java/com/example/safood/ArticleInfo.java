package com.example.safood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safood.Common.Common;
import com.squareup.picasso.Picasso;

public class ArticleInfo extends AppCompatActivity {

    private TextView mTitle,mContent;
    private ImageView mImg;
    private Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_info);

        mTitle = findViewById(R.id.title_info);
        mContent = findViewById(R.id.content);
        mImg = findViewById(R.id.img_info);

        mBackBtn = findViewById(R.id.back);

        mTitle.setText(Common.currentArticle.getTitle());
        mContent.setText(Common.currentArticle.getContent());
        Picasso.with(getApplicationContext()).load(Common.currentArticle.getImage()).into(mImg);

        mContent.setMovementMethod(new ScrollingMovementMethod());

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleInfo.this,ArticlesActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ArticleInfo.this,ArticlesActivity.class));
        finish();
    }
}