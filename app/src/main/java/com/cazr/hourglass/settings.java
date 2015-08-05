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

public class settings extends ActionBarActivity {
    AnimationDrawable upload;

    public String user_name;
    public String friend_name;
    public String friend_phone;
    public Integer current_weight;
    public Integer goal_weight;
    public String goal_date;

    public EditText user_name_field;
    public EditText friend_name_field;
    public EditText friend_phone_field;
    public EditText current_weight_field;
    public EditText goal_weight_field;
    public EditText goal_date_field;

    public static final String MY_PREFS_NAME = "My_Saved_Data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView upload_image = (ImageView)findViewById(R.id.animate_this);
        upload_image.setBackgroundResource(R.drawable.upload_needed);
        upload = (AnimationDrawable)upload_image.getBackground();

        upload.start();

        current_weight = 0;
        goal_weight = 0;

        user_name_field = (EditText)findViewById(R.id.user_name);
        friend_name_field = (EditText)findViewById(R.id.workout_friend);
        friend_phone_field = (EditText)findViewById(R.id.workout_friend_number);
        current_weight_field = (EditText)findViewById(R.id.current_weight);
        goal_weight_field = (EditText)findViewById(R.id.goal_weight);
        goal_date_field = (EditText)findViewById(R.id.date_to_reach_goal);

        get_prefs();

    }

    public void save_data(View view){
        ImageView upload_image = (ImageView)findViewById(R.id.animate_this);
        upload_image.setBackgroundResource(R.drawable.upload_completed);
        upload = (AnimationDrawable)upload_image.getBackground();

        upload.start();

        update_user_input();

        Toast.makeText(this,"Personal Profile Saved",Toast.LENGTH_SHORT).show();

        Intent main_page = new Intent(this,MainActivity.class);
        startActivity(main_page);
    }

    public void update_user_input(){
        user_name = user_name_field.getText().toString();
        friend_name = friend_name_field.getText().toString();
        friend_phone = friend_phone_field.getText().toString();
        current_weight = Integer.parseInt(current_weight_field.getText().toString());
        goal_weight = Integer.parseInt(goal_weight_field.getText().toString());
        goal_date = goal_date_field.getText().toString();
        update_prefs();
    }

    public void update_prefs(){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("user_name", user_name);
        editor.putString("friend_name", friend_name);
        editor.putString("friend_phone", friend_phone);
        editor.putInt("cur_weight", current_weight);
        editor.putInt("goal_weight",goal_weight);
        editor.putString("goal_date", goal_date);
        editor.putBoolean("restoredText", true);
        editor.apply();
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
        user_name_field.setText(user_name);
        friend_name_field.setText(friend_name);
        friend_phone_field.setText(friend_phone);
        if (current_weight == 0){
            current_weight_field.setText("");
        }
        else {
            current_weight_field.setText(current_weight.toString());
        }
        if (goal_weight == 0){
            goal_weight_field.setText("");
        }
        else {
            goal_weight_field.setText(goal_weight.toString());
        }
        goal_date_field.setText(goal_date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
