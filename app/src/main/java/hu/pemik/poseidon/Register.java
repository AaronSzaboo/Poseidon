package hu.pemik.poseidon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.pemik.poseidon.authentication.Authentication;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView regbutton = (ImageView) findViewById(R.id.RegisterButton);
        EditText username = findViewById(R.id.RusernameText);
        EditText pass = findViewById(R.id.RpasswordText);
        TextView lathat = findViewById(R.id.lathat);
        EditText passv = findViewById(R.id.RpasswordVerfyText);
        ImageView vissza = (ImageView) findViewById(R.id.vassza);
        username.setText("");
        pass.setText("");
        passv.setText("");

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authentication authentication = new Authentication();
                authentication.register(username.getText().toString(), pass.getText().toString(), passv.getText().toString(), (Integer result) -> {
                    lathat.setText(result.toString());
                    Log.d("LOGIN", result.toString());
                    String kiszed = lathat.getText().toString();

                    if(kiszed.equals("1")){
                        Log.d("LOGIN", "belement");
                        Toast.makeText(getApplicationContext(), "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                        openBejelentkezett();
                    } else if(kiszed.equals("-2")){
                        Log.d("LOGIN", "belement");
                        Toast.makeText(getApplicationContext(), "A felhasználónév már foglalt!", Toast.LENGTH_SHORT).show();
                    } else if(kiszed.equals("-3")){
                        Log.d("LOGIN", "belement");
                        Toast.makeText(getApplicationContext(), "A két jelszó nem eggyezik meg!", Toast.LENGTH_SHORT).show();
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

        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

    }

    public void openRegistration(){
        Intent intent2 = new Intent(this, Register.class);
        startActivity(intent2);
    }

    public void openLogin(){
        Intent intent3 = new Intent(this, Login.class);
        startActivity(intent3);
    }

    public void openBejelentkezett(){
        Intent intent4 = new Intent(this, Fooldal.class);
        startActivity(intent4);
    }
}
