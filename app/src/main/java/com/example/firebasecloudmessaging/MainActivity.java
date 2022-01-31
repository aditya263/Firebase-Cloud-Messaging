package com.example.firebasecloudmessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private EditText mTitle, mMessage;

    private static String serverKey = "AAAAWMNdUiY:APA91bFVkgZ_SZn3LmD4z9PoFdJFBsfapgwqf9JpH6JIeUGHhjZOAVrtggXUQ2m6m1FaHjfHzR703chj5hRlc8d1iRRTfIHSQjaYziHfXiQcRnijVJC4CTDdlu3mR03qX8ZsS3OHeUMb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FCMSend Initialization
        FCMSend.SetServerKey(serverKey);

        // Get Device Token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String token = task.getResult();
                        System.out.println("TOKEN " + token);
                    }
                });


        mTitle = findViewById(R.id.mTitle);
        mMessage = findViewById(R.id.mMessage);

        String toDeviceToken = "";

        findViewById(R.id.mSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitle.getText().toString().trim();
                String message = mMessage.getText().toString().trim();
                if (!title.equals("") && !message.equals("")) {
                    String result = FCMSend.pushNotification(
                            MainActivity.this,
                            toDeviceToken,
                            title,
                            message
                    );
                    System.out.println("RESULT " + result);
                }
            }
        });

    }
}