package com.smart.simacol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.VIBRATOR_SERVICE;


public class RuangKerjaFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef, KUNCI, GEDUNG;
    Button button0, button1, button2;
    ImageView lampu0,lampu1,lampu2;
    TextView jumlah, jumlah1, jumlah2;
    Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ruang_kerja, container, false);
        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        KUNCI = myRef.child("STATUS-PERANGKAT");
        GEDUNG = myRef.child("GEDUNG");

        button0 = v.findViewById(R.id.pintu0);
        button1 = v.findViewById(R.id.pintu1);
        button2 = v.findViewById(R.id.pintu2);

        lampu0 = v.findViewById(R.id.lampu0);
        lampu1 = v.findViewById(R.id.lampu1);
        lampu2 = v.findViewById(R.id.lampu2);

        jumlah = v.findViewById(R.id.jum);
        jumlah1 = v.findViewById(R.id.jum1);
        jumlah2 = v.findViewById(R.id.jum2);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Pintu Ruang Kerja
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert =new AlertDialog.Builder(getContext());
                AlertDialog dialog;
                alert.setTitle("Password");
                alert.setMessage("Tulis password ruangan");

                final EditText input = new EditText(getContext());
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alert.setView(input);
                alert.setIcon(R.drawable.password);
                alert.setPositiveButton("Buka", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference passRef = FirebaseDatabase.getInstance().getReference().child("GEDUNG");
                        passRef.child("roomKerja").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String pass = dataSnapshot.getValue(String.class);
                                String password = input.getText().toString();
                                if (pass.equals(password)){
                                    Toast.makeText(getContext(), "Pintu Terbuka", Toast.LENGTH_SHORT).show();
//                                    KUNCI.child("roomKerja/lampu").setValue("1");
                                    KUNCI.child("roomKerja/kunci").setValue("1");
                                    Thread i = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                sleep(3500);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                KUNCI.child("roomKerja/kunci").setValue("0");
                                            }
                                        }
                                    };
                                    i.start();
                                }else{
                                    Toast.makeText(getContext(), "Password Salah", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });

                alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        KUNCI.child("roomKerja/kunci").setValue("0");
//                        KUNCI.child("roomKerja/lampu").setValue("0");
                        dialog.cancel();
                    }
                });
                dialog = alert.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alert.show();
            }
        });

        //Pintu Ruang Meeting 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert =new AlertDialog.Builder(getContext());
                AlertDialog dialog;
                alert.setTitle("Password");
                alert.setMessage("Tulis password ruangan");

                final EditText input = new EditText(getContext());
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alert.setView(input);
                alert.setIcon(R.drawable.password);
                alert.setPositiveButton("Buka", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference passRef = FirebaseDatabase.getInstance().getReference().child("GEDUNG");
                        passRef.child("room1").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String pass = dataSnapshot.getValue(String.class);
                                String password = input.getText().toString();
                                if (pass.equals(password)){
                                    Toast.makeText(getContext(), "Pintu Terbuka", Toast.LENGTH_SHORT).show();
//                                    KUNCI.child("room1/lampu").setValue("1");
                                    KUNCI.child("room1/kunci").setValue("1");
                                    Thread i = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                sleep(3500);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                KUNCI.child("room1/kunci").setValue("0");
                                            }
                                        }
                                    };
                                    i.start();
                                }else{
                                    Toast.makeText(getContext(), "Password Salah", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });

                alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        KUNCI.child("room1/kunci").setValue("0");
//                        KUNCI.child("room1/lampu").setValue("0");
                        dialog.cancel();
                    }
                });
                dialog = alert.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alert.show();
            }
        });

        //Pintu Ruang Meeting 2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert =new AlertDialog.Builder(getContext());
                AlertDialog dialog;
                alert.setTitle("Password");
                alert.setMessage("Tulis password ruangan");

                final EditText input = new EditText(getContext());
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alert.setView(input);
                alert.setIcon(R.drawable.password);
                alert.setPositiveButton("Buka", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference passRef = FirebaseDatabase.getInstance().getReference().child("GEDUNG");
                        passRef.child("room2").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String pass = dataSnapshot.getValue(String.class);
                                String password = input.getText().toString();
                                if (pass.equals(password)){
                                    Toast.makeText(getContext(), "Pintu Terbuka", Toast.LENGTH_SHORT).show();
//                                    KUNCI.child("room2/lampu").setValue("1");
                                    KUNCI.child("room2/kunci").setValue("1");
                                    Thread i = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                sleep(3500);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                KUNCI.child("room2/kunci").setValue("0");
                                            }
                                        }
                                    };
                                    i.start();
                                }else{
                                    Toast.makeText(getContext(), "Password Salah", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });

                alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        KUNCI.child("room2/kunci").setValue("0");
//                        KUNCI.child("room2/lampu").setValue("0");
                        dialog.cancel();
                    }
                });
                dialog = alert.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alert.show();
            }
        });

        //Indikator Saklar
        KUNCI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Saklar Ruang Kerja
                final String saklar0 = dataSnapshot.child("roomKerja/lampu").getValue(String.class);
                if (saklar0.equals("1")) {
                    lampu0.setBackgroundResource(R.drawable.lampon);
                    vibrator.vibrate(1000);
                } else {
                    lampu0.setBackgroundResource(R.drawable.lampoff);
                }

                //Saklar Ruang Meeting1
                final String saklar1 = dataSnapshot.child("room1/lampu").getValue(String.class);
                if (saklar1.equals("1")) {
                    lampu1.setBackgroundResource(R.drawable.lampon);
                } else {
                    lampu1.setBackgroundResource(R.drawable.lampoff);
                }

                //Saklar Ruang Meeting2
                final String saklar2 = dataSnapshot.child("room2/lampu").getValue(String.class);
                if (saklar2.equals("1")) {
                    lampu2.setBackgroundResource(R.drawable.lampon);
                } else {
                    lampu2.setBackgroundResource(R.drawable.lampoff);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Indikator jumlah orang
        GEDUNG.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Integer jum = dataSnapshot.child("roomKerja/jumlah").getValue(Integer.class);
                jumlah.setText(String.valueOf(jum));
                final Integer jum1 = dataSnapshot.child("room1/jumlah").getValue(Integer.class);
                jumlah1.setText(String.valueOf(jum1));
                final Integer jum2 = dataSnapshot.child("room2/jumlah").getValue(Integer.class);
                jumlah2.setText(String.valueOf(jum2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
