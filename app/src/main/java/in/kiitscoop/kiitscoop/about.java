package in.kiitscoop.kiitscoop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by RATISH on 5/15/2017.
 */

public class about extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        ImageView linkedin1 = (ImageView) findViewById(R.id.linkedin1);
        ImageView linkedin2 = (ImageView) findViewById(R.id.linkedin2);
        ImageView fb1 = (ImageView) findViewById(R.id.fb1);
        ImageView fb2 = (ImageView) findViewById(R.id.fb2);
        ImageView insta1 = (ImageView) findViewById(R.id.insta1);
        ImageView insta2 = (ImageView) findViewById(R.id.insta2);

        linkedin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"LINK NOT AVAILABLE",Toast.LENGTH_SHORT).show();
                /*Uri data = Uri.parse("");
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                startActivity(i);*/

            }
        });

        linkedin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri data = Uri.parse("https://www.linkedin.com/in/kshitijsharma21");
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                startActivity(i);


            }
        });

        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri data = Uri.parse("https://www.facebook.com/ratishaxle");
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                startActivity(i);

            }
        });

        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri data = Uri.parse("https://www.facebook.com/kshitij.sharma.97");
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                startActivity(i);

            }
        });


        insta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri data = Uri.parse("https://www.instagram.com/ratish_jha/");
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                startActivity(i);
            }
        });

        insta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri data = Uri.parse("https://www.instagram.com/kshitij_2101/");
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Toast.makeText(getApplicationContext()," Thank You " + auth.getCurrentUser().getDisplayName(),Toast.LENGTH_SHORT).show();
                auth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),FirstpageActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
