package in.kiitscoop.kiitscoop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import in.kiitscoop.kiitscoop.adapter.CategoryAdapter;


public class MainActivity extends AppCompatActivity {
    ImageView about,kiit,chat;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        // Find the view pager that will allow the user to swipe between fragments

        chat =(ImageView) findViewById(R.id.chat);
        about = (ImageView) findViewById(R.id.about);
        kiit=(ImageView)findViewById(R.id.kiitlogo);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), chatActivity.class));
            }
        });
/*
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0,true);
                Toast.makeText(getApplicationContext(),"HOME",Toast.LENGTH_SHORT).show();
            }
        });*/

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), about.class));
            }
        });
        kiit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Teacher.class));

            }
        });



        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Set the com.example.android.miwok.adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        TextView tv= (TextView) findViewById(R.id.mytext);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,true);
            }
        });

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's com.example.android.miwok.adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
    }
    boolean twice;
    @Override
    public void onBackPressed() {
        if (twice==true&&viewPager.getCurrentItem()==0){
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
            finish();
            System.exit(0);
        }
        if (viewPager.getCurrentItem()!=0){
        viewPager.setCurrentItem(0,true);}
        Toast.makeText(getApplicationContext(),"PRESS TWICE",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
            }
        },2000);
        twice=true;
    }
}