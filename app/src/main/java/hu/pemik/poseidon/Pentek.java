package hu.pemik.poseidon;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Pentek extends AppCompatActivity {

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private final String fileName = "export.xlsx";

    ListView list;

    List<String> Targynevek = new ArrayList<>();
    List<String> Terem = new ArrayList<>();
    List<String> Kezdesi = new ArrayList<>();
    int img[] = {R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pentek);

        list = findViewById(R.id.lista);
        Pentek.MyAdapter adapter = new Pentek.MyAdapter(this, Targynevek, Terem, Kezdesi, img);
        list.setAdapter(adapter);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> myTitles = new ArrayList<>();
        List<String> myterem = new ArrayList<>();
        List<String> mykezdes = new ArrayList<>();
        int[] mykep;

        public MyAdapter(Context c, List<String> titles, List<String> terem, List<String> kezdes, int[] kep) {
            super(c, R.layout.oraitem, R.id.text1, titles);
            this.context = c;
            this.mykep = kep;
            this.myterem = terem;
            this.mykezdes = kezdes;
            this.myTitles = titles;
        }

        @Nullable
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.oraitem, parent, false);
            ImageView images = row.findViewById(R.id.logo);
            TextView nev = row.findViewById(R.id.text1);
            TextView terem = row.findViewById(R.id.text2);
            TextView kezdes = row.findViewById(R.id.text3);
            images.setImageResource(mykep[position]);
            nev.setText(myTitles.get(position));
            terem.setText(myterem.get(position));
            kezdes.setText(mykezdes.get(position));

            return row;
        }
    }

    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (canRead) {
            try {
                this.readFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        try {
                            readFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InvalidFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //writeFile();
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() throws IOException, InvalidFormatException {

        File extStore = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i("ExternalStorageDemo", "Read file: " + path);

        Workbook workbook = WorkbookFactory.create(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/export.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);

        String kezdes = "";
        String oraneve = "";

        String datum = "";
        String kezdoora = "";
        String nev = "";
        String terem = "";

        List<String> targynev = new ArrayList<>();
        List<String> idopont = new ArrayList<>();
        List<String> teremnev = new ArrayList<>();

        int kell = 0;
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Now let's iterate over the columns of the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            int oszlopsz = 0;

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = cell.getStringCellValue();
                System.out.print(cellValue + "\t");
                Log.d("ADSADSA", cellValue);
                if (kell == 1) {
                    if (oszlopsz == 0) {
                        kezdes = cellValue;
                    }
                    if (oszlopsz == 2) {
                        oraneve = cellValue;
                    }
                    if (oszlopsz == 3) {
                        terem = cellValue;
                    }
                }
                oszlopsz++;
            }
            if (kell == 1) {
                String[] id = kezdes.split(" ");

                for (int i = 0; i < id.length; i++) {
                    Log.d("KARALABE", id[i]);
                }


                datum = id[0];
                kezdoora = id[1];
                String[] id2 = oraneve.split("-");
                String[] id3 = id2[0].split("\\(");
                nev = id3[0];
                Log.d("TESZTTESZT", kezdes + ", " + oraneve);
                Log.d("IDOSZAK", datum + "DDDD");
                SimpleDateFormat myformat = new SimpleDateFormat("yyyy.MM.dd.");
                String egy = datum;
                String ketto = "2017.05.02.";

                try {
                    java.util.Date date1 = myformat.parse(egy);
                    java.util.Date date2 = myformat.parse(ketto);
                    Long diff = date1.getTime() - date2.getTime();
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) % 7 == 0) {
                        Log.d("IDOSZAK", "Kedd");
                    }
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) % 7 == 1) {
                        Log.d("IDOSZAK", "Szerda");
                    }
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) % 7 == 2) {
                        Log.d("IDOSZAK", "Csutortok");
                    }
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) % 7 == 3) {
                        Log.d("IDOSZAK", "Pentek");
                        boolean voltmar = false;
                        for (int i = 0; i < targynev.size(); i++) {
                            if (id2[0].equals(targynev.get(i)) && terem.equals(teremnev.get(i)) && kezdoora.equals(idopont.get(i))) {
                                voltmar = true;
                            }
                        }
                        if (voltmar == false) {
                            targynev.add(id2[0]);
                            teremnev.add(terem);
                            idopont.add(kezdoora);
                        }
                    }
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) % 7 == 4) {
                        Log.d("IDOSZAK", "Szombat");
                    }
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) % 7 == 5) {
                        Log.d("IDOSZAK", "Vasarnap");
                    }
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) % 7 == 6) {
                        Log.d("IDOSZAK", "Hetfo");
                    }
                    //datumos.setText((int) diff);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            kell = 1;
        }

        List<String> rtargynev = new ArrayList<>();
        List<String> ridopont = new ArrayList<>();
        List<String> rteremnev = new ArrayList<>();

        for (int j = 0; j < targynev.size(); j++) {
            int min = 50;
            int index = 0;

            for (int i = 0; i < targynev.size(); i++) {
                String[] dar = idopont.get(i).split(":");
                int ora = Integer.parseInt(dar[0]);
                if (min >= ora) {
                    min = ora;
                    index = i;
                }
            }
            rtargynev.add(targynev.get(index));
            ridopont.add(idopont.get(index));
            rteremnev.add(teremnev.get(index));
            idopont.set(index, "100:" + idopont.get(index));
        }

        for (int i = 0; i < targynev.size(); i++) {
            Targynevek.add(rtargynev.get(i));
            Terem.add(ridopont.get(i));
            Kezdesi.add(rteremnev.get(i));
        }
    }
}
