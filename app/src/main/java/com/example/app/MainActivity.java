package com.example.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(getApplicationContext(), ChatHeadService.class));
        moveTaskToBack(true);
    }
}
