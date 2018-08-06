package in.kiitscoop.kiitscoop.logging;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import in.kiitscoop.kiitscoop.FirstpageActivity;
import in.kiitscoop.kiitscoop.MainActivity;
import in.kiitscoop.kiitscoop.R;

public class SignupActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
EditText etemail,etname,etpass1,etpass2;
Button signup;
SignInButton gsignin;
    static final int rc = 9001;
ProgressDialog pro;
FirebaseAuth auth;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if(isNetworkAvailable()) {
            etemail = (EditText) findViewById(R.id.etemail);
            etname = (EditText) findViewById(R.id.name);
            etpass1 = (EditText) findViewById(R.id.password1);
            etpass2 = (EditText)findViewById(R.id.password2);
            signup = (Button) findViewById(R.id.signup);
            pro = new ProgressDialog(this);
            auth = FirebaseAuth.getInstance();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            gsignin = (SignInButton) findViewById(R.id.gsignup);

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signin();
                }
            });

            gsignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleSignIn();
                }
            });
        }
        else {
            final Dialog dialog = new Dialog(SignupActivity.this);
            dialog.setContentView(R.layout.internet_dialog);
            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(SignupActivity.this,SignupActivity.class));
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }
    private void signin() {
        String email= etemail.getText().toString().trim();
        String pass= etpass1.getText().toString().trim();
        String cpass= etpass2.getText().toString().trim();
        final String name = etname.getText().toString().trim();
        if (TextUtils.isEmpty(email))
            etemail.setError("Enter Email");
        if (TextUtils.isEmpty(pass))
            etpass1.setError("Enter password");
        if (TextUtils.isEmpty(cpass))
            etpass2.setError("Enter Confirm Password");
        if(TextUtils.isEmpty(name))
            etname.setError("Enter Name");
        else {
                if (pass.equals(cpass)) {
                    pro.setMessage("Please Wait...");
                    pro.show();
                    auth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pro.dismiss();
                                    if (task.isSuccessful()) {
                                        finish();
                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        final FirebaseUser user = auth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name).build();
                                        user.updateProfile(profileUpdates);
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    } else
                                        Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    etpass2.setError("Password Don't Match");
                }
        }
    }

    //GOOGLE SIGN IN
    private void googleSignIn(){
        //Intent SignInIntent= new Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        //startActivityForResult(SignInIntent,rc);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, rc);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==rc){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }
    void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        pro.setMessage("Please Wait...");
        pro.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        if(acct.getIdToken()==null){
            Toast.makeText(this,"Please enter correctly",Toast.LENGTH_SHORT).show();
            Intent signini=new Intent(getApplicationContext(),FirstpageActivity.class);
            startActivity(signini);
        }
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pro.dismiss();
                        if (task.isSuccessful() ){
                            finish();
                            Intent signini=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(signini);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}