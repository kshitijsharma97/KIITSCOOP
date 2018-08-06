package in.kiitscoop.kiitscoop;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final boolean conectioncheck = isNetworkAvailable();
        if (conectioncheck) {
            class Mythread extends Thread {
                @Override
                public void run() {
                    try {

                        Thread.sleep(2000);
                        Intent i = new Intent(SplashActivity.this, FirstpageActivity.class);
                        startActivity(i);
                        //finish();
                    } catch (InterruptedException e) {
                        //Toast.makeText(SplashActivity.this,"EXCEPTION",Toast.LENGTH_SHORT).show();
                        Log.e("my error", "error");
                    }
                }
            }
            Mythread my = new Mythread();
            my.start();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please connect", Toast.LENGTH_SHORT).show();
            final Dialog dialog = new Dialog(SplashActivity.this);
            dialog.setContentView(R.layout.internet_dialog);
            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(SplashActivity.this,SplashActivity.class));
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {

    }
}
