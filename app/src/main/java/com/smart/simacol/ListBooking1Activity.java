package com.smart.simacol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smart.simacol.Adapters.BookingAllAdapter;
import com.smart.simacol.models.BookingAll;

public class ListBooking1Activity extends AppCompatActivity {
    BookingAllAdapter bookingAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_booking1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("BOOKING")
                .child("room1")
                .orderByChild("status")
                .equalTo("0");
        FirebaseRecyclerOptions<BookingAll> options = new FirebaseRecyclerOptions.Builder<BookingAll>()
                .setQuery(query, BookingAll.class)
                .setLifecycleOwner(this)
                .build();

        bookingAllAdapter = new BookingAllAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycle_bookingAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(bookingAllAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        bookingAllAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bookingAllAdapter.stopListening();
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
