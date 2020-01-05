package com.smart.simacol.Ruang2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smart.simacol.Adapters.BookingAdapter2;
import com.smart.simacol.R;
import com.smart.simacol.models.Booking;


public class Ruang2Fragment extends Fragment {
    BookingAdapter2 bookingAdapter2;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    DatabaseReference dbRef;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseRecyclerOptions<Booking> options;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ruang2, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("USER-BOOKING").child(uid).child("room2");

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
                .child("room2");
        options = new FirebaseRecyclerOptions.Builder<Booking>()
                .setQuery(query, Booking.class)
                .setLifecycleOwner(this)
                .build();

        bookingAdapter2 = new BookingAdapter2(options);
        recyclerView = v.findViewById(R.id.recycle_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookingAdapter2);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        bookingAdapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bookingAdapter2.stopListening();
    }
}
