package com.ensc40.manitosecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private ImageButton imageState;
    private SharedPreferences settings;
    SharedPreferences.Editor editor;
    String TAG = "MAINTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GET SHARED PREFERENCES and SET UP EDITOR
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        editor = settings.edit();

        //Set up finished
        //if(!settings.getBoolean("setUp", false)) {setUpFinished();}

        setUpUI();

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(getApplicationContext(), SetUpBT.class);
                startActivity(settings);
                return true;
            case R.id.developer_setting:
                Intent developer = new Intent(getApplicationContext(), DeveloperChat.class);
                startActivity(developer);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpFinished(){
        editor.putBoolean("setUp", true).commit();
    }

    private void setUpUI(){
        imageState = (ImageButton) findViewById(R.id.stateImage);


        if (!settings.getBoolean("armState", false)){		//if setting is off, button should be off
            imageState.setImageResource(R.drawable.button_off);
        }

        imageState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = settings.edit();
                if(settings.getBoolean("armState", false) == true){			//if armed -> disarm
                    imageState.setImageResource(R.drawable.button_off);
                    editor.putBoolean("armState", false).commit();
                }

                else{   //disarm -> arm
                    imageState.setImageResource(R.drawable.button_on);
                    editor.putBoolean("armState", true).commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
