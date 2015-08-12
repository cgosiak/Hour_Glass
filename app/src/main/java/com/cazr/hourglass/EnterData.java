package com.cazr.hourglass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EnterData extends ActionBarActivity {

    public EditText points;
    public Integer current_point_value;

    public String user_name;
    public String friend_name;
    public String friend_phone;
    public Integer current_weight;
    public Integer goal_weight;
    public String goal_date;
    public String string_list;
    public String todays_date;
    public String last_date_entered;
    public String packet;

    public Integer updated_weight;

    public String message_to_user_good;
    public String message_to_user_bad;
    public String message_to_friend_good;
    public String message_to_friend_bad;
    public String message_nothing_changed;
    public String text_message;

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
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Calendar calendar = Calendar.getInstance();
        todays_date = df.format(calendar.getTime());

        updated_weight = Integer.parseInt(points.getText().toString());

        gained = String.valueOf(updated_weight - current_weight);
        message_to_user_bad = "Sorry " + user_name + ", You Gained " + gained + " lbs";
        message_to_friend_bad = "Sorry, it Looks Like Your Partner Wasn't Very Active Yesterday. Remember: This is a Team Effort and They Need Your Support! So Let's Do This Together!";

        lost = String.valueOf(current_weight - updated_weight);
        message_to_user_good = "Congratulations " + user_name + ", You Lost " + lost + " lbs";
        message_to_friend_good = "Awesome Work Team! Because of Your Support, " + user_name + " Was Able to Keep on Moving Towards Their Goal!";
        message_nothing_changed = "Well, Your Partner didn't Lose Progress, or make Progress. Consider this a Win for the Team, but Strive to Work with Your Teammate More and Accomplish Your Goals Together!";

        packet = packet + (todays_date + ":" + current_weight + ":" + gained + ":" + lost + ",");

        string_list = string_list + updated_weight + ",";
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("cur_weight", updated_weight);
        editor.putString("string_list", string_list);
        editor.putString("last_date_entered",todays_date);
        editor.putString("packet", packet);
        editor.apply();

        if (updated_weight > current_weight){
            Toast.makeText(this,message_to_user_bad,Toast.LENGTH_LONG).show();
            text_message = message_to_friend_bad;
        }
        else {
         if (updated_weight.equals(current_weight)){
             Toast.makeText(this,"You Remained Exactly the Same! Let's Hit it Harder Tomorrow!",Toast.LENGTH_LONG).show();
             text_message = message_nothing_changed;
         }
            else {
             Toast.makeText(this,message_to_user_good,Toast.LENGTH_LONG).show();
             text_message = message_to_friend_good;
         }
        }
        text_your_friend(text_message);
    }

    public void text_your_friend(String message){
        try {
            Toast.makeText(this,"Text to Accountability Partner has Been Sent!", Toast.LENGTH_LONG).show();
            SmsManager send_text = SmsManager.getDefault();
            send_text.sendTextMessage(friend_phone, null, message, null, null);
        }
        catch (Exception e){
            Toast.makeText(this,"Unfortunately We Were Unable to Send Your Accountability Buddy Your Update. Try Again Later!", Toast.LENGTH_LONG).show();
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
            updated_weight = current_weight;
            goal_weight = prefs.getInt("goal_weight", 0);
            goal_date = prefs.getString("goal_date", "");
            string_list = prefs.getString("string_list","");
            last_date_entered = prefs.getString("last_date_entered","");
            packet = prefs.getString("packet","");
        }
        fill_in_fields();
    }

    public void fill_in_fields(){
        try{
        points.setText(current_weight.toString());
        }
        catch (Exception e){
            current_weight = 0;
            points.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_data, menu);
        return true;
    }


    public void upload_data(View view){
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Calendar calendar = Calendar.getInstance();
        todays_date = df.format(calendar.getTime());

        if (todays_date.equals(last_date_entered)){
            Toast.makeText(this,"Data Already Entered Once Today",Toast.LENGTH_LONG).show();
            Intent go_back = new Intent(this,MainActivity.class);
            startActivity(go_back);
        }

        else {
            ImageView upload_image = (ImageView) findViewById(R.id.animate_this);
            upload_image.setBackgroundResource(R.drawable.upload_completed);
            upload = (AnimationDrawable) upload_image.getBackground();

            upload.start();

            update_prefs();

            Intent go_back = new Intent(this, MainActivity.class);
            startActivity(go_back);
        }

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
