package com.vac.wasd.twoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("7", Integer.parseInt(editText.getText().toString()));
                startActivityForResult(intent, 9);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9) {
            int n = data.getIntExtra("8", 0);
            TextView textView = findViewById(R.id.textView);
            textView.setText(n + "");
        }
    }
}
