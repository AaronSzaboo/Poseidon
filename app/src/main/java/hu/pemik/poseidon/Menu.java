package hu.pemik.poseidon;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

public class Menu extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupUIView();
        setupListView();
    }

    private void setupUIView(){
        toolbar = (Toolbar) findViewById(R.id.ToolbarMain);
        listView = (ListView) findViewById(R.id.lvMain);
    }

    /*
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Valami");
    }*/

    private void setupListView(){
        String[] title = getResources().getStringArray(R.array.Main);
        String[] description = getResources().getStringArray(R.array.Description);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, title, description);
        listView.setAdapter(simpleAdapter);
    }

    public  class  SimpleAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView title, description;
        private String[] titleArray;
        private String[] descriptionArray;
        private ImageView imageView;

        public SimpleAdapter(Context context, String[] title, String[] description){
            mContext = context;
            titleArray = title;
            descriptionArray = description;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return titleArray.length;
        }

        @Override
        public Object getItem(int position) {
            return titleArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.manu_activity_single_item, null);
            }

            title = (TextView) convertView.findViewById(R.id.text1);
            //description = (TextView) convertView.findViewById(R.id.tvDescription);
            imageView = (ImageView) convertView.findViewById(R.id.logo);

            title.setText(titleArray[position]);
            description.setText(descriptionArray[position]);

            if (titleArray[position].equalsIgnoreCase("Órarend")){
                imageView.setImageResource(R.drawable.orarend);
            } else
            if (titleArray[position].equalsIgnoreCase("Masodik")){
                imageView.setImageResource(R.drawable.masodik);
            } else
            if (titleArray[position].equalsIgnoreCase("Harmadik")){
                imageView.setImageResource(R.drawable.harmadik);
            } else
            if (titleArray[position].equalsIgnoreCase("Negyedik")){
                imageView.setImageResource(R.drawable.negyedik);
            }
            return convertView;
        }
    }

}
