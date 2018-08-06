package in.kiitscoop.kiitscoop.TABS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.kiitscoop.kiitscoop.R;
import in.kiitscoop.kiitscoop.adapter.RecyclerAdapter;
import in.kiitscoop.kiitscoop.adapter.Word;

import java.util.ArrayList;


public class NOTICE extends Fragment {
    public NOTICE() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        //WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.primary_color);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        RecyclerView listView = (RecyclerView) rootView.findViewById(R.id.list);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(new RecyclerAdapter(words,getContext()));

        words.removeAll(words);
        return rootView;
    }
}