package in.kiitscoop.kiitscoop.logging;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import in.kiitscoop.kiitscoop.FirstpageActivity;
import in.kiitscoop.kiitscoop.MainActivity;
import in.kiitscoop.kiitscoop.R;
import in.kiitscoop.kiitscoop.SplashActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    GoogleApiClient googleApiClient;
    SignInButton googlesignin;
    //LoginButton fblogin;
    //CallbackManager mCallbackManager;
    static final String TAG = "GoogleActivity";
    static final int rc = 9001;
    FirebaseUser user;
    EditText etemail, etpass;
    Button signin;
    TextView tv, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        if(isNetworkAvailable()) {
            firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
            user = firebaseAuth.getCurrentUser();
            signup = (TextView) findViewById(R.id.textviewsignup);
            tv = (TextView) findViewById(R.id.sign);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            etemail = (EditText) findViewById(R.id.etemail);
            progressDialog = new ProgressDialog(this);
            etpass = (EditText) findViewById(R.id.etpass);
            googlesignin = (SignInButton) findViewById(R.id.googlesignin);
            //fblogin = (LoginButton) findViewById(R.id.fbsignin);
            //fblogin.setReadPermissions("email", "public_profile");
            signin = (Button) findViewById(R.id.buttonsignin);
            googlesignin.setOnClickListener(this);
            //fblogin.setOnClickListener(this);
            signin.setOnClickListener(this);
            signup.setOnClickListener(this);

        }
        else{
            final Dialog dialog = new Dialog(LoginActivity.this);
            dialog.setContentView(R.layout.internet_dialog);
            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private void userLogin() {
        String email = etemail.getText().toString().trim();
        String pass = etpass.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            etemail.setError("Please Enter Email");
            Toast.makeText(getApplicationContext(),"ENTER EMAIL",Toast.LENGTH_SHORT);
        }
        if (TextUtils.isEmpty(pass)) {
            etpass.setError("Please Enter Password");
            Toast.makeText(getApplicationContext(),"ENTER PASSWORD",Toast.LENGTH_SHORT);
        }

        else{
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                            Toast.makeText(getApplicationContext(),"INVALID LOGIN DETAILS",Toast.LENGTH_SHORT).show();
                    }
                });

            }
    }
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
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        if(acct.getIdToken()==null){
            Toast.makeText(this,"Please enter correctly",Toast.LENGTH_SHORT).show();
            Intent signini=new Intent(getApplicationContext(),FirstpageActivity.class);
            startActivity(signini);
        }
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
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
    public void onClick(View view) {
        if (view == signin) {
            userLogin();
        }
       if (view==googlesignin){
            googleSignIn();
        }
        if (view == signup) {
            finish();
            startActivity(new Intent(this, SignupActivity.class));
        }


    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}