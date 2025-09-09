package com.lteitservices.tinytotsstate;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lteitservices.tinytotsstate.utils.Constants;
import com.lteitservices.tinytotsstate.utils.Utility;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {

    public LinearLayout libraryBtn,course_performance,reset_quiz,logout,offlinePayment;
    public ImageView backBtn;
    public TextView titleTV;
    protected FrameLayout mDrawerLayout, actionBar;
    public Map<String, String>  headers = new HashMap<String, String>();
    public Map<String, String> params = new Hashtable<String, String>();
    public String defaultDateFormat, currency,currency_price;
    String device_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));
        setContentView(R.layout.base_activity);

        backBtn = findViewById(R.id.actionBar_backBtn);
        mDrawerLayout = findViewById(R.id.container);
        actionBar = findViewById(R.id.actionBarSecondary);
        titleTV = findViewById(R.id.actionBar_title);
        libraryBtn = findViewById(R.id.baseActivity_libraryBtn);
        course_performance = findViewById(R.id.course_performance);
        reset_quiz = findViewById(R.id.reset_quiz);
        logout = findViewById(R.id.logout);


            defaultDateFormat = Utility.getSharedPreferences(getApplicationContext(), "dateFormat");
            currency = Utility.getSharedPreferences(getApplicationContext(), Constants.currency);
          currency_price =  Utility.getSharedPreferences(getApplicationContext(), Constants.currency_price);

        decorate();

        backBtn.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.no_animation,  R.anim.slide_rightleft);
        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task ->{
            if (!task.isSuccessful()) {
                Log.w("TokenRetrieval", "Fetching FCM registration token failed", task.getException());
                return;
            }

            device_token = task.getResult();
            Log.e(" logout DEVICE TOKEN", device_token);
        });

    }

    private void decorate() {
        actionBar.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
    }

}
