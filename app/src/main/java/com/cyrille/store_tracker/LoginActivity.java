package com.cyrille.store_tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView bien;
    CardView cardView;
    private FirebaseAuth auth;
    EditText editTextmail;
    EditText editTextPassword;
    Button login;
    private FirebaseAuth mAuth;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           // reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        cardView=findViewById(R.id.google_icon);
         editTextmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        login=findViewById(R.id.login_button);
        auth= FirebaseAuth.getInstance();
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=editTextmail.getText().toString();
                password=editTextPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(email.equals("")||password.equals(""))
                                    Toast.makeText(LoginActivity.this, "Tous les champs sont indispensables", Toast.LENGTH_SHORT).show();
                                else{
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //    FirebaseUser user = mAuth.getCurrentUser();
                                    // Log.d("TAG", user.getEmail());
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    if (currentUser != null) {
                                        Toast.makeText(LoginActivity.this, "Connexion Réussie!!",
                                                Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(LoginActivity.this, MenuActivity.class);
                                        LoginActivity.this.startActivity(myIntent);
                                    }

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Echec de la connexion",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            }


                        });
                
            }
        });
    }




    public void Signin(){
        Intent myintent=googleSignInClient.getSignInIntent();
        startActivityForResult(myintent,100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                task.getResult(ApiException.class);
                HomeActivity();
            }
            catch (ApiException e)
            {
                Toast.makeText(this, "Echec de la connexion"+e.getMessage(), Toast.LENGTH_SHORT) .show();
            }
        }
    }
    private void HomeActivity() {
        finish();
        Intent myintent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(myintent);
    }
    }
