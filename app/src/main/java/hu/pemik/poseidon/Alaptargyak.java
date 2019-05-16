package hu.pemik.poseidon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Alaptargyak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaptargyak);
        Targyak t = new Targyak();
        try {
            String url = "jdbc:jtds:sqlserver://193.6.33.140:1433/PoseidonDb;user=poseidon;password=Poseidon_1";;
            Connection conn = DriverManager.getConnection(url);
            if (conn == null) {
                Log.e("BEKERES", "Nincs kapcsolat");
            }
            Statement stmt = conn.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT nev, kod, feltetel_kod from targy join elofeltetel as e on e.targy_kod = targy.kod order by targy.nev");
            while ( rs.next() ) {
                String tnev = rs.getString("nev");
                String tkod = rs.getString("kod");
                String tfel = rs.getString("feltetel_kod");
                Log.d("BEKERES", tnev + " " + tkod + " " + tfel);
                int index = -1;
                for (int i= 0; i < t.targyak.size(); i++){
                    if (t.targyak.get(i).targykod.equals(tkod)){
                        index = i;
                    }
                }
                if (index == -1){
                    if (!tfel.isEmpty()){
                        t.targyak.add(new targy(tnev, tkod, tfel));
                    } else{
                        t.targyak.add(new targy(tnev, tkod));
                    }
                }else{
                    t.targyak.get(index).elofeltetel.add(tfel);
                }
            }
            Log.d("BEKERES", String.valueOf(t.targyak.size()));
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            Log.e("BEKERES", String.valueOf(t.targyak.size()));
        }

    }



}
