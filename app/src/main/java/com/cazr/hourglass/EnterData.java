package com.cazr.hourglass;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class EnterData extends ActionBarActivity {

    public EditText points;
    public Integer current_point_value;

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
