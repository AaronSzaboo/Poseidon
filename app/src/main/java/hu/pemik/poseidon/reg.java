package hu.pemik.poseidon;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class reg extends AppCompatActivity {

    TextInputEditText textUsername, textPassword, textPasswordVerfy;
    ProgressBar progressBar;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);

        textUsername = (TextInputEditText) findViewById(R.id.username_register);
        textPassword = (TextInputEditText) findViewById(R.id.password_register);
        textPasswordVerfy = (TextInputEditText) findViewById(R.id.password_verfy_register);
        progressBar = (ProgressBar) findViewById(R.id.prograssBarRegister);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    public void registerUser(View v){
        progressBar.setVisibility(View.VISIBLE);
        final String user = textUsername.getText().toString();
        final String password = textPassword.getText().toString();
        final String passwordverfy = textPasswordVerfy.getText().toString();
        if (!password.equals("") && !passwordverfy.equals("") && !user.equals("")) {
            if (password.equals(passwordverfy)) {
                auth.createUserWithEmailAndPassword(user, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    User u = new User();
                                    u.setName(user);
                                    u.setEmail(user);

                                    reference.child(firebaseUser.getUid()).setValue(u)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(getApplicationContext(), "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.VISIBLE);
                                                        finish();
                                                        Intent i = new Intent(reg.this, Fooldal.class);
                                                        startActivity(i);
                                                    }
                                                    else{
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(getApplicationContext(), "A felhasználót nem lett létrehozva!", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                }
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "A két jelszó nem eggyezik meg!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Minden mezők ki kell tölteni!", Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoLogin(View v){
        Intent i = new Intent(reg.this, m.class);
        startActivity(i);
    }

}
