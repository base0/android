package com.vac.wasd.twoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int n;
        switch (view.getId()) {
            case R.id.button2:
                n = 2;
                break;
            default:
                n = 1;
        }
        Intent input = getIntent();
        Intent output = new Intent();
        n += input.getIntExtra("7", 0);
        output.putExtra("8", n);
        setResult(RESULT_OK, output);
        finish();

    }
}
