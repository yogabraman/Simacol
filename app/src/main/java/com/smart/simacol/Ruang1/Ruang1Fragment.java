package com.smart.simacol.Ruang1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smart.simacol.Adapters.BookingAdapter1;
import com.smart.simacol.R;
import com.smart.simacol.models.Booking;


public class Ruang1Fragment extends Fragment {
    BookingAdapter1 bookingAdapter1;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    Button KekerJANCUK;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ruang1, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("USER-BOOKING")
                .child(uid)
                .child("room1");
        FirebaseRecyclerOptions<Booking> options = new FirebaseRecyclerOptions.Builder<Booking>()
                .setQuery(query, Booking.class)
                .setLifecycleOwner(this)
                .build();

        bookingAdapter1 = new BookingAdapter1(options);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookingAdapter1);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        bookingAdapter1.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bookingAdapter1.stopListening();
    }
}
