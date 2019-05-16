package hu.pemik.poseidon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class m extends AppCompatActivity {

    TextInputEditText textUsername, textPassword;
    ProgressBar progressBar;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            Intent i = new Intent(m.this, Fooldal.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_m);

        textUsername = (TextInputEditText) findViewById(R.id.username_login);
        textPassword = (TextInputEditText) findViewById(R.id.password_login);
        progressBar = (ProgressBar) findViewById(R.id.prograssBarLogin);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");


    }

    public void LoginUser(View v){
        progressBar.setVisibility(View.VISIBLE);
        String user = textUsername.getText().toString();
        String password = textPassword.getText().toString();

        if (!user.equals("") && !password.equals("")){
            auth.signInWithEmailAndPassword(user, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(m.this, Fooldal.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void gotoRegister(View v){
        Intent i = new Intent(m.this, reg.class);
        startActivity(i);
    }
}
