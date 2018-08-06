package in.kiitscoop.kiitscoop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import in.kiitscoop.kiitscoop.adapter.RecyclerAdapter;
import in.kiitscoop.kiitscoop.adapter.Word;

public class Teacher extends AppCompatActivity {

    DatabaseReference mfirebase;
    RecyclerView listView;
    final ArrayList<Word> words = new ArrayList<Word>();
    Bitmap bmp1,bmp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        words.removeAll(words);
        mfirebase = FirebaseDatabase.getInstance().getReference();
        listView = (RecyclerView) findViewById(R.id.list);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        }

    @Override
    public void onStart() {
        super.onStart();
        mfirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot newssnapshot : dataSnapshot.getChildren()) {
                    News news = newssnapshot.getValue(News.class);
                    String str = news.getCategory();
                    if (str!=null && str.equals("TEACHERS")){
                        //bmp1 = getBitmapFromURL(news.getImageUrl());
                        //bmp2 = getBitmapFromURL(news.getLogoUrl());
                        words.add(new Word(news.getName(), news.getNewsTitle(),news.getLogoUrl(), news.getImageUrl(), news.getNewsContent(),news.getNewsLink()));
                    }}
                listView.setAdapter(new RecyclerAdapter(words,getApplicationContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    }
