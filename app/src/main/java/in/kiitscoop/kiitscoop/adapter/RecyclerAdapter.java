package in.kiitscoop.kiitscoop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.kiitscoop.kiitscoop.R;

/**
 * Created by KSHITIJ SHARMA on 07-06-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<Word> words = new ArrayList<>();
    Context context;
    public RecyclerAdapter(ArrayList<Word> words,Context context) {
        this.words =words; this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Word currentWord = words.get(i);
        viewHolder.deptTextView.setText(currentWord.getmDepartmentId());
        viewHolder.defaultTextView.setText(currentWord.getDefaultdeptnameId());
        /*viewHolder.image1View.setImageBitmap(currentWord.getImage1ResourceId());
        viewHolder.image2View.setImageBitmap(currentWord.getImage2ResourceId());*/
        Picasso.with(context).load(currentWord.getImage1ResourceId()).into(viewHolder.image1View);
        Picasso.with(context).load(currentWord.getImage2ResourceId()).into(viewHolder.image2View);
        viewHolder.newsView.setText(currentWord.getmNewsResourceId());
        viewHolder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri data = Uri.parse(currentWord.getmNewsUrl());
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deptTextView,defaultTextView,newsView;
        ImageView image1View,image2View;
        Button b;
        public ViewHolder(View itemView) {
            super(itemView);
            deptTextView = (TextView) itemView.findViewById(R.id.department);
            defaultTextView = (TextView) itemView.findViewById(R.id.deptname);
            image1View = (ImageView) itemView.findViewById(R.id.image1);
            image2View = (ImageView) itemView.findViewById(R.id.image2);
            newsView =(TextView) itemView.findViewById(R.id.news);
            b = (Button) itemView.findViewById(R.id.linkbutton);
        }
    }
}
