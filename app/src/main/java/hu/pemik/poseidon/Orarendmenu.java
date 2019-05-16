package hu.pemik.poseidon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Orarendmenu extends AppCompatActivity {

    ListView list;
    String Napok[] = {"Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek"};
    int img[] = {R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik, R.drawable.masodik};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orarendmenu);

        list = findViewById(R.id.lista);
        Orarendmenu.MyAdapter adapter = new Orarendmenu.MyAdapter(this, Napok, img);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    hetfo();
                }
                if (position == 1){
                    kedd();
                }
                if (position == 2){
                    szerda();
                }
                if (position == 3){
                    csutortok();
                }
                if (position == 4){
                    pentek();
                }
            }
        });




    }

    public void hetfo(){
        Intent intent1 = new Intent(this, Hetfo.class);
        startActivity(intent1);
    }

    public void kedd(){
        Intent intent2 = new Intent(this, Kedd.class);
        startActivity(intent2);
    }

    public void szerda(){
        Intent intent3 = new Intent(this, Szerda.class);
        startActivity(intent3);
    }

    public void csutortok(){
        Intent intent4 = new Intent(this, Csutortok.class);
        startActivity(intent4);
    }

    public void pentek(){
        Intent intent5 = new Intent(this, Pentek.class);
        startActivity(intent5);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String myTitles[];
        String myterem[];
        String mykezdes[];
        String myhet[];
        int[] mykep;

        public MyAdapter(Context c,  String[] napok, int[] kep) {
            super(c, R.layout.manu_activity_single_item, R.id.text1, napok);
            this.context=c;
            this.mykep=kep;
            this.myhet=napok;
        }

        @Nullable
        @Override
        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.manu_activity_single_item, parent, false);
            ImageView images = row.findViewById(R.id.logo);
            TextView nev = row.findViewById(R.id.text1);
            images.setImageResource(mykep[position]);
            nev.setText(myhet[position]);

            return row;
        }
    }
}

