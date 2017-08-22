package com.hasan.austinpetsalive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public void dogAdoptClick (View view) {
            Intent intent = new Intent(getApplicationContext(), AdoptionActivity.class);
        intent.putExtra("animal","dog");
            startActivity(intent);
    }

    public void catAdoptClick (View view) {
        Intent intent = new Intent(getApplicationContext(), AdoptionActivity.class);
        intent.putExtra("animal","cat");
        startActivity(intent);
    }

    public void tabbedActivityClick (View view) {
        Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

