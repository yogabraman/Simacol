package com.smart.simacol;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ACFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef, ACcon, ACsu;
    Button tambah, kurang, kontrolAC;
    TextView suhuAC;
    int suhu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ac, container, false);
        tambah = v.findViewById(R.id.tambah);
        kurang = v.findViewById(R.id.kurang);
        suhuAC = v.findViewById(R.id.suhuAC);
        kontrolAC = v.findViewById(R.id.kontrolAC);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        ACsu = myRef.child("GEDUNG/roomKerja/suhu");
        ACcon = myRef.child("STATUS-PERANGKAT/roomKerja/ac");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        ACsu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suhu = dataSnapshot.getValue(Integer.class);
                suhuAC.setText(String.valueOf(suhu));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ACcon.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String mbut = dataSnapshot.getValue(String.class);
                        if (mbut.equals("1")) {
                            suhu = suhu + 1;
                            ACsu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ACsu.setValue(suhu);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ACcon.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String mbut = dataSnapshot.getValue(String.class);
                        if (mbut.equals("1")) {
                            suhu = suhu - 1;
                            ACsu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ACsu.setValue(suhu);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        kontrolAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ACcon.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String jem = dataSnapshot.getValue(String.class);
                        switch (jem){
                            case "1":
                                ACcon.setValue("0");
                                Toast.makeText(getContext(),"AC Mati tapi sabar",Toast.LENGTH_LONG).show();
                                break;
                            case "0":
                                ACcon.setValue("1");
                                Toast.makeText(getContext(),"AC Hidup tapi sabar",Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        ACcon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String mbut = dataSnapshot.getValue(String.class);
                if (mbut.equals("0")) {
                    kontrolAC.setText("OFF");
                } else {
                    kontrolAC.setText("ON");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
