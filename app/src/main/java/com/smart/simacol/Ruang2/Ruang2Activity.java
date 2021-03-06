package com.smart.simacol.Ruang2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.smart.simacol.R;

public class Ruang2Activity extends AppCompatActivity implements View.OnClickListener {
    Button booking, listBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruang2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listBooking = findViewById(R.id.list_booking);
        listBooking.setOnClickListener(this);
        booking = findViewById(R.id.booking);
        booking.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.booking:
                startActivity(new Intent(getApplicationContext(), Booking2Activity.class));
                break;
            case R.id.list_booking:
                startActivity(new Intent(getApplicationContext(), ListBooking2Activity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
