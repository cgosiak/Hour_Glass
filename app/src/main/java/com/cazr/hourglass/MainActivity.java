package com.cazr.hourglass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    ImageButton FAB;
    public String packet;
    public List<String> usable_packet_list;
    public Boolean restored_text;
    public ListView listView;

    public static final String MY_PREFS_NAME = "My_Saved_Data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        get_prefs();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNum(position);
            }
        });
    }

    public void showNum(Integer item){
        String myData = usable_packet_list.get(item);
        List<String> full_packet = Arrays.asList(myData.split("\\s*:\\s*"));
        String message = "Weight from " + full_packet.get(0) + " was " + full_packet.get(1) + " lbs";
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    public void fab_Button(View view){
        if (restored_text) {
            Intent i = new Intent(MainActivity.this, EnterData.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this,"Please Visit The Settings Menu First",Toast.LENGTH_LONG).show();
        }
    }

    public void get_prefs(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean restore_text = prefs.getBoolean("restoredText",false);
        restored_text = restore_text;
        if (restore_text) {
            packet = prefs.getString("packet","");
        }
        try {
            usable_packet_list = Arrays.asList(packet.split("\\s*,\\s*"));
        }
        catch (Exception e) {
            packet = "03/30/1994:200:0:5";
            usable_packet_list = Arrays.asList(packet.split("\\s*,\\s*"));
        }
        set_up_list();
    }

    public void set_up_list(){
        // Construct the data source
        ArrayList<Recap> arrayOfUsers = new ArrayList<Recap>();
        // Create the adapter to convert the array to views
        my_list_adapter adapter = new my_list_adapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.show_recap);
        listView.setAdapter(adapter);

        try {
            for (int i = 0; i < usable_packet_list.size(); i++) {
                String current_list_element = usable_packet_list.get(i);
                List<String> full_packet = Arrays.asList(current_list_element.split("\\s*:\\s*"));
                Integer cur_weight = Integer.parseInt(full_packet.get(1));
                Integer lost_weight = Integer.parseInt(full_packet.get(3));
                Integer gained_weight = Integer.parseInt(full_packet.get(2));
                Recap newRecap = new Recap(full_packet.get(0), cur_weight, lost_weight, gained_weight);
                adapter.add(newRecap);
            }
        }
        catch (Exception e){
            Recap newRecap = new Recap("Current Date", 0, 0, 0);
            adapter.add(newRecap);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this,settings.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
