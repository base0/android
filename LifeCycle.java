package com.example.vac.myapplication;

// unpredictable behavior

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public void clk(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("gg", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("gg", "onResume");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("gg", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("gg", "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("gg", "onStop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("gg", "onCreate");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("gg", "onDestroy");
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
