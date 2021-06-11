package com.example.safood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safood.Common.Common;
import com.example.safood.Fargments.NotificationsHistoryFragment;
import com.squareup.picasso.Picasso;

public class NotificationInfoActivity extends AppCompatActivity {
    private TextView mTitle,mContent;
    private ImageView mImg;
    private Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_info);

        mTitle = findViewById(R.id.subject_info);
        mContent = findViewById(R.id.contentInfoNotification);
        mImg = findViewById(R.id.img_info_notification);

        mBackBtn = findViewById(R.id.backNotification);

        mTitle.setText(Common.currentNotification.getSubject());
        mContent.setText(Common.currentNotification.getNotificationContent());
        Picasso.with(getApplicationContext()).load(Common.currentNotification.getImg()).into(mImg);

        mContent.setMovementMethod(new ScrollingMovementMethod());

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.isHistory){
                    startActivity(new Intent(NotificationInfoActivity.this, HomeSafood.class));
                }else{
                    startActivity(new Intent(NotificationInfoActivity.this,AllNotificationsActivity.class));
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Common.isHistory){
            startActivity(new Intent(NotificationInfoActivity.this, HomeSafood.class));
        }else{
            startActivity(new Intent(NotificationInfoActivity.this,AllNotificationsActivity.class));
        }
        finish();
    }
}