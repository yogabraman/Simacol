package com.smart.simacol.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smart.simacol.R;
import com.smart.simacol.models.Booking;

import java.util.HashMap;
import java.util.Map;

public class BookingAdapter2 extends FirebaseRecyclerAdapter<Booking, BookingAdapter2.BookingHolder> {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid, id;
    //Referensi Database
    DatabaseReference bookRef, bookAllRef;
    Context context;
    public BookingAdapter2(FirebaseRecyclerOptions<Booking> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final BookingHolder holder, int position, @NonNull Booking model) {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        //deklarasi variabel
        bookRef = FirebaseDatabase.getInstance().getReference().child("USER-BOOKING/"+uid+"/room2");
        bookAllRef = FirebaseDatabase.getInstance().getReference().child("BOOKING/room2");

        holder.text_ruangan.setText(model.getRuang());
        holder.text_subjek.setText(model.getSubjek());
        holder.text_tgl.setText(model.getTanggal());
        holder.text_start.setText(model.getStart());
        holder.text_end.setText(model.getEnd());
        holder.text_password.setText(model.getPassword());
        holder.kekerASU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CANCEL BOOKING")
                        .setMessage("Do you want to cancel your booking ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Map<String, Object> asu = new HashMap<>();
                                asu.put("status", "1");
                                DataSnapshot dataSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                                id = dataSnapshot.getKey();
                                Toast.makeText(view.getContext(), id, Toast.LENGTH_SHORT).show();
                                bookRef.child(id).updateChildren(asu);
                                bookAllRef.child(id).updateChildren(asu);
                            }
                        })
                        .setNegativeButton("NO", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot areaSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                    String childName = areaSnapshot.getKey();
                    final String UNTA = dataSnapshot.child(childName).child("status").getValue(String.class);
                    if (UNTA.equals("1")) {
                        holder.kekerASU.setBackgroundColor(Color.parseColor("#fc4245"));
                        holder.kekerASU.setText("expired");
                        holder.kekerASU.setEnabled(false);
                    } else {
                        holder.kekerASU.setBackgroundColor(Color.parseColor("#fcbc32"));
                        holder.kekerASU.setText("cancel");
                        holder.kekerASU.setEnabled(true);
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        context = parent.getContext();
        return new BookingHolder(view);
    }

    class BookingHolder extends RecyclerView.ViewHolder{
        TextView text_ruangan, text_subjek, text_tgl, text_start, text_end, text_password;
        Button kekerASU;
        CardView cardBooking;

        public BookingHolder(View itemView) {
            super(itemView);
            text_ruangan = itemView.findViewById(R.id.text_ruangan);
            text_subjek = itemView.findViewById(R.id.text_subjek);
            text_tgl = itemView.findViewById(R.id.text_tgl);
            text_start = itemView.findViewById(R.id.text_start);
            text_end = itemView.findViewById(R.id.text_end);
            text_password = itemView.findViewById(R.id.text_password);
            kekerASU = itemView.findViewById(R.id.kekerASU);
            cardBooking = itemView.findViewById(R.id.cardBooking);
        }
    }

}
