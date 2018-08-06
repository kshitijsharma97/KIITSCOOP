package in.kiitscoop.kiitscoop.TABS;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.kiitscoop.kiitscoop.News;
import in.kiitscoop.kiitscoop.R;
import in.kiitscoop.kiitscoop.adapter.RecyclerAdapter;
import in.kiitscoop.kiitscoop.adapter.Word;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;


public class DEPARTMENT extends Fragment {

    DatabaseReference mfirebase;
    RecyclerView listView;
    final ArrayList<Word> words = new ArrayList<Word>();
    Bitmap bmp1,bmp2;
        public DEPARTMENT() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.word_list, container, false);
            mfirebase = FirebaseDatabase.getInstance().getReference();
            listView = (RecyclerView) rootView.findViewById(R.id.list);
            listView.setHasFixedSize(true);
            listView.setLayoutManager(new LinearLayoutManager(getContext()));
            words.removeAll(words);
           /* listView.setHasFixedSize(true);
            listView.setLayoutManager(new LinearLayoutManager(getContext()));
            listView.setAdapter(new RecyclerAdapter(words,getContext()));*/
          //  words.add(new Word("Red", "lal", R.drawable.r1,R.drawable.r2,"dcsdc"));
            // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
            // adapter knows how to create list items for each item in the list.
         //   WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.primary_color);

            // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
            // There should be a {@link ListView} with the view ID called list, which is declared in the
            // word_list.xml layout file.

            // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
            // {@link ListView} will display list items for each {@link Word} in the list.
            //listView.setAdapter(adapter);


            return rootView;
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
                    if (str!=null && str.equals("DEPARTMENT")){
                         //  bmp1 = getBitmapFromURL(news.getImageUrl());
                          // bmp2 = getBitmapFromURL(news.getLogoUrl());
                                   //BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        words.add(new Word(news.getSource(), news.getNewsTitle(), news.getLogoUrl() , news.getImageUrl(), news.getNewsContent(),news.getNewsLink()));
                    }}


                listView.setAdapter(new RecyclerAdapter(words,getContext()));

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