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

public class Fooldal extends AppCompatActivity {

    ListView list;
    String Napok[] = {"Órarend", "Előfeltételek", "Chat"};
    int img[] = {R.drawable.masodik, R.drawable.masodik, R.drawable.masodik};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooldal);

        list = findViewById(R.id.lista);
        Fooldal.MyAdapter adapter = new Fooldal.MyAdapter(this, Napok, img);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    orarend();
                }
                if (position == 1){
                    elofeltetelek();
                }
                if (position == 2){
                    chatoldal();
                }
            }
        });




    }

    public void chatoldal(){
        Intent intent85 = new Intent(this, chat.class);
        startActivity(intent85);
    }

    public void orarend(){
        Intent intent9 = new Intent(this, Orarendmenu.class);
        startActivity(intent9);
    }

    public void elofeltetelek(){
        Intent intent11 = new Intent(this, base.class);
        startActivity(intent11);
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

