package hu.pemik.poseidon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class base extends AppCompatActivity {

    ListView list;

    List<String> Targynevek = new ArrayList<>();
    List<String> Kezdesi = new ArrayList<>();
    List<Integer> img = new ArrayList<>();
    //int img[] = {R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik};

    Targyak t = new Targyak();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        CONN();
        for (int i = 0; i < t.targyak.size(); i++){
            Targynevek.add(t.targyak.get(i).targynev);
            Kezdesi.add(t.targyak.get(i).targykod);
            img.add(R.drawable.masodik);
        }
        list = findViewById(R.id.lista);
        base.MyAdapter adapter = new base.MyAdapter(this, Targynevek, Kezdesi, img);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1;
                intent1 = new Intent(getBaseContext(), tobb.class);
                String kovi = "";
                for (int i = 0; i < t.targyak.get(position).elofeltetel.size(); i++){
                    if (i == 0){
                        kovi += t.targyak.get(position).elofeltetel.get(i);
                    }else{
                        kovi += "|" + t.targyak.get(position).elofeltetel.get(i);
                    }
                }
                intent1.putExtra("kod", kovi);
                startActivity(intent1);
            }
        });
    }

    String classs = "net.sourceforge.jtds.jdbc.Driver";


    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://193.6.33.140:1433/PoseidonDb;user=poseidon;password=Poseidon_1";
            conn = DriverManager.getConnection(ConnURL);
            Statement stmt = conn.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT nev, kod, feltetel_kod from targy join elofeltetel as e on e.targy_kod = targy.kod order by targy.nev");
            while (rs.next()) {
                String tnev = rs.getString("nev");
                String tkod = rs.getString("kod");
                String tfel = rs.getString("feltetel_kod");
                Log.d("BEKERES", tnev + " " + tkod + " " + tfel);
                int index = -1;
                for (int i = 0; i < t.targyak.size(); i++) {
                    if (t.targyak.get(i).targykod.equals(tkod)) {
                        index = i;
                    }
                }
                if (index == -1) {
                    if (!tfel.isEmpty()) {
                        t.targyak.add(new targy(tnev, tkod, tfel));
                    } else {
                        t.targyak.add(new targy(tnev, tkod));
                    }
                } else {
                    t.targyak.get(index).elofeltetel.add(tfel);
                }
            }
        }
        catch (SQLException se)
        {
            Log.e("safiya", se.getMessage());
        }
        catch (ClassNotFoundException e) {
        }
        catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return conn;
    }


    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private final String fileName = "export.xlsx";


    class MyAdapter extends ArrayAdapter<String> {



        Context context;
        List<String> myTitles = new ArrayList<>();
        List<String> mykezdes = new ArrayList<>();
        List<Integer> mykep = new ArrayList<>();

        public MyAdapter(Context c, List<String> titles, List<String> kezdes, List<Integer> kep) {
            super(c, R.layout.targyitem1, R.id.text1, titles);
            this.context = c;
            this.mykep = kep;
            this.mykezdes = kezdes;
            this.myTitles = titles;
        }

        @Nullable
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.targyitem1, parent, false);
            ImageView images = row.findViewById(R.id.logo);
            TextView nev = row.findViewById(R.id.text1);
            TextView kezdes = row.findViewById(R.id.text3);
            images.setImageResource(mykep.get(position));
            nev.setText(myTitles.get(position));
            kezdes.setText(mykezdes.get(position));

            return row;
        }
    }



    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }




}
