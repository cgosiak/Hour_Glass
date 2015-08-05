package com.cazr.hourglass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EnterData extends ActionBarActivity {

    public EditText points;
    public Integer current_point_value;

    public String user_name;
    public String friend_name;
    public String friend_phone;
    public Integer current_weight;
    public Integer goal_weight;
    public String goal_date;

    public Integer updated_weight;

    public String message_to_user_good;
    public String message_to_user_bad;
    public String message_to_friend_good;
    public String message_to_friend_bad;

    public String gained;
    public String lost;

    public static final String MY_PREFS_NAME = "My_Saved_Data";

    AnimationDrawable upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);

        points = (EditText)findViewById(R.id.points);
        current_point_value = 100;
        points.setText(current_point_value.toString());

        ImageView upload_image = (ImageView)findViewById(R.id.animate_this);
        upload_image.setBackgroundResource(R.drawable.upload_needed);
        upload = (AnimationDrawable)upload_image.getBackground();

        upload.start();

        get_prefs();
    }

    public void update_prefs(){
        updated_weight = Integer.parseInt(points.getText().toString());
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("cur_weight", updated_weight);
        editor.apply();

        gained = String.valueOf(updated_weight - current_weight);
        message_to_user_bad = "Sorry " + user_name + ", You Gained " + gained + " lbs";

        lost = String.valueOf(current_weight - updated_weight);
        message_to_user_good = "Congratulations " + user_name + ", You Lost " + lost + " lbs";

        if (updated_weight > current_weight){
            Toast.makeText(this,message_to_user_bad,Toast.LENGTH_LONG).show();
        }
        else {
         if (updated_weight.equals(current_weight)){
             Toast.makeText(this,"You Remained Exactly the Same! Let's Hit it Harder Tomorrow!",Toast.LENGTH_LONG).show();
         }
            else {
             Toast.makeText(this,message_to_user_good,Toast.LENGTH_LONG).show();
         }
        }
    }

    public void get_prefs(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean restore_text = prefs.getBoolean("restoredText",false);
        if (restore_text) {
            user_name = prefs.getString("user_name", "");
            friend_name = prefs.getString("friend_name", "");
            friend_phone = prefs.getString("friend_phone", "");
            current_weight = prefs.getInt("cur_weight", 0);
            goal_weight = prefs.getInt("goal_weight", 0);
            goal_date = prefs.getString("goal_date", "");
        }
        fill_in_fields();
    }

    public void fill_in_fields(){
        points.setText(current_weight.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_data, menu);
        return true;
    }

    public void upload_data(View view){
        ImageView upload_image = (ImageView)findViewById(R.id.animate_this);
        upload_image.setBackgroundResource(R.drawable.upload_completed);
        upload = (AnimationDrawable)upload_image.getBackground();

        upload.start();

        update_prefs();

        Intent go_back = new Intent(this,MainActivity.class);
        startActivity(go_back);
    }

    public void raise(View view){
        current_point_value = Integer.parseInt(points.getText().toString());
        current_point_value += 2;
        points.setText(current_point_value.toString());
    }

    public void lower(View view){
        current_point_value = Integer.parseInt(points.getText().toString());
        current_point_value -= 2;
        points.setText(current_point_value.toString());
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
