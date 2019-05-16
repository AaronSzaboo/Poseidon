package hu.pemik.poseidon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.pemik.poseidon.authentication.Authentication;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView valami = (ImageView) findViewById(R.id.valami);
        valami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistration();
            }
        });


        ImageView loginbutton = (ImageView) findViewById(R.id.LoginButton);
        //ImageView signupp = (ImageView) findViewById(R.id.atiranyitas);

        EditText username = (EditText) findViewById(R.id.UsernameText);
        EditText pass = (EditText) findViewById(R.id.PasswordText);
        TextView latahatlanocska = (TextView) findViewById(R.id.lathatatlan);
        username.setText("");
        pass.setText("");

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authentication authentication = new Authentication();
                    authentication.login(username.getText().toString(), pass.getText().toString(), (Integer result) -> {

                    latahatlanocska.setText(result.toString());
                    Log.d("LOGIN", result.toString());
                    String kiszed = latahatlanocska.getText().toString();

                    if(kiszed.equals("1")){
                        Log.d("LOGIN", "belement");
                        Toast.makeText(getApplicationContext(), "Sikeres belépés!", Toast.LENGTH_SHORT).show();
                        openBejelentkezett();
                    } else if(kiszed.equals("-1")){
                        Log.d("LOGIN", "belement");
                        Toast.makeText(getApplicationContext(), "Hibás felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
                    } else if(kiszed.equals("-10")) {
                        Log.d("LOGIN", "belement");
                        Toast.makeText(getApplicationContext(), "Az adatbázist nem lehet elérni!", Toast.LENGTH_SHORT).show();
                    } else if(kiszed.equals("0")){
                        Log.d("LOGIN", "belement");
                        Toast.makeText(getApplicationContext(), "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();
                    }
                    });
            }
        });


    }

    public void openRegistration(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void openLogin(){
        Intent intent1 = new Intent(this, Login.class);
        startActivity(intent1);
    }

    public void openBejelentkezett(){
        Intent intent4 = new Intent(this, Fooldal.class);
        startActivity(intent4);
    }
}
