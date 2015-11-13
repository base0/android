package com.example.vac.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String lineEnd = "\r\n";
                    final String twoHyphens = "--";
                    final String boundary = "*****";

                    URL url = new URL("http://10.0.2.2/android/put_img.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"test.jpg\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    FileInputStream fis = new FileInputStream(getFilesDir() + "/temp.jpg");
                    int bufferSize = 1024 * 4;
                    byte[] buffer = new byte[1024 * 4];

                    // Read file
                    int bytesRead = 0;

                    while ((bytesRead = fis.read(buffer, 0, bufferSize)) > 0) {
                        dos.write(buffer, 0, bytesRead);
                        Log.i("gg", "" + bytesRead);
                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    int serverResponseCode = con.getResponseCode();
                    String serverResponseMessage = con.getResponseMessage();
                    Log.i("gg", "" + serverResponseCode);
                    Log.i("gg", serverResponseMessage);
                    fis.close();
                    dos.flush();
                    dos.close();
                } catch (Exception ex) {
                    Log.i("gg", ex.toString());
                }

            }
        }).start();
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
