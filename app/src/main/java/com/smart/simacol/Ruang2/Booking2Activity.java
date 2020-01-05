package com.smart.simacol.Ruang2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smart.simacol.R;
import com.smart.simacol.models.Booking;
import com.smart.simacol.models.BookingAll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Booking2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Booking2Activity";
    Button book;
    long cekin_ku, cekout_ku, cekin_mu, cekout_mu;
    String subjek, password, date, start, end, ruang, userid, status, username, uid, key, name;
    String tanggal_mu, start_mu, end_mu;
    String status_mu="0";
    private EditText edt_subjek, edt_password, edt_date, edt_start, edt_end;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener1, mTimeSetListener2;
    private Calendar calendar;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference dbRef, userBookingRef, bookingRef, room2Ref;
    GoogleSignInClient mGoogleSignInClient;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbRef = FirebaseDatabase.getInstance().getReference();
        userBookingRef = dbRef.child("USER-BOOKING");
        bookingRef = dbRef.child("BOOKING");
        room2Ref = bookingRef.child("room2");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        username = user.getDisplayName();
        uid = user.getUid();

        edt_date = findViewById(R.id.edt_date);
        edt_date.setInputType(InputType.TYPE_NULL);
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Booking2Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: date: " + month + "/" + day + "/" + year);
                String waktu = month + "/" + day + "/" + year;
                edt_date.setText(waktu);
            }
        };

        edt_start = findViewById(R.id.edt_start);
        edt_start.setInputType(InputType.TYPE_NULL);
        edt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        Booking2Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        mTimeSetListener1,
                        hour, minute,true
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String waktu = hour + ":" + minute;
                edt_start.setText(waktu);
            }
        };

        edt_end = findViewById(R.id.edt_end);
        edt_end.setInputType(InputType.TYPE_NULL);
        edt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.MILLISECOND);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        Booking2Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        mTimeSetListener2,
                        hour, minute,true
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String waktu = hour + ":" + minute;
                edt_end.setText(waktu);
            }
        };

        edt_subjek = findViewById(R.id.edt_subjek);
        edt_password = findViewById(R.id.edt_password);

        book = findViewById(R.id.btn_book);
        book.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
    }

    @Override
    public void onClick(View v) {
        key = userBookingRef.push().getKey();
        date = edt_date.getText().toString();
        start = edt_start.getText().toString();
        end = edt_end.getText().toString();
        subjek = edt_subjek.getText().toString();
        password = edt_password.getText().toString();
        ruang = "Ruang Meeting 2";
        status = "0";
        userid = uid;
        name = username;
        switch (v.getId()) {
            case R.id.btn_book:
                if (!validateForm()) {
                    return;
                }
                if (validateTime().equals("success")) {
                    SetBookAll(key, date, start, end, subjek, ruang, status, name);
                    setBookUser(key, date, start, end, subjek, password, ruang, status, userid);
                }
                i++;
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        i = 0;
                    }
                };
                if (i == 1){
                    Toast.makeText(getApplicationContext(), "Click Lagi", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runnable, 600);
                }else if (i == 2){
                    Toast.makeText(getApplicationContext(), "Maaf Sudah Ada Yang Booking", Toast.LENGTH_SHORT).show();
                }else if (i == 3){
                    Toast.makeText(getApplicationContext(), "Maaf Sudah Ada Yang Booking", Toast.LENGTH_SHORT).show();
                }else if (i == 4){
                    Toast.makeText(getApplicationContext(), "Maaf Sudah Ada Yang Booking", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        date = edt_date.getText().toString();
        start = edt_start.getText().toString();
        end = edt_end.getText().toString();
        subjek = edt_subjek.getText().toString();
        password = edt_password.getText().toString();
        if (date.isEmpty()) {
            edt_date.setError("Tanggal belum diisi");
            result = false;
        }
        if (start.isEmpty()) {
            edt_start.setError("Waktu mulai belum diisi");
            result = false;
        }
        if (end.isEmpty()) {
            edt_end.setError("Waktu selesai belum diisi");
            result = false;
        }
        if (subjek.isEmpty()) {
            edt_subjek.setError("Subjek belum diisi");
            result = false;
        }
        if (password.isEmpty()) {
            edt_password.setError("Password belum diisi");
            result = false;
        }
        return result;
    }

    private String validateTime() {
        room2Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()){
                    final String childName = areaSnapshot.getKey();
                    tanggal_mu = dataSnapshot.child(childName).child("tanggal").getValue(String.class);
                    start_mu = dataSnapshot.child(childName).child("start").getValue(String.class);
                    end_mu = dataSnapshot.child(childName).child("end").getValue(String.class);
                    status_mu =  dataSnapshot.child(childName).child("status").getValue(String.class);

                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    Date DateStart_ku = null;
                    Date DateEnd_ku = null;
                    Date DateStart_mu = null;
                    Date DateEnd_mu = null;
                    try {
                        DateStart_ku = formatter.parse(date+" "+start);
                        DateEnd_ku = formatter.parse(date+" "+end);
                        DateStart_mu = formatter.parse(tanggal_mu+" "+start_mu);
                        DateEnd_mu = formatter.parse(tanggal_mu+" "+end_mu);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cekin_ku = DateStart_ku.getTime();
                    cekout_ku = DateEnd_ku.getTime();
                    cekin_mu = DateStart_mu.getTime();
                    cekout_mu = DateEnd_mu.getTime();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (cekin_ku >= cekin_mu && cekin_ku <= cekout_mu && status_mu != null && status_mu.equalsIgnoreCase("0")||
                cekout_ku >= cekin_mu && cekout_ku <= cekout_mu && status_mu != null && status_mu.equalsIgnoreCase("0") ||
                cekin_ku <= cekin_mu && cekout_ku >= cekout_mu && status_mu != null && status_mu.equalsIgnoreCase("0")) {
            String result = "failed";
            return result;
        }else {
            String result = "success";
            return result;
        }
    }

    private void SetBookAll(String key,String date, String start, String end, String subjek, String ruang, String status, String name) {
        BookingAll bookingAll = new BookingAll(date, start, end, subjek, ruang, status, name);
        bookingRef.child("room2").child(key).setValue(bookingAll)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void setBookUser(String key, String date, String start, String end, String subjek, String password, String ruang, String status, String userid) {
        Booking booking = new Booking(key, date, start, end, subjek, password, ruang, status, userid);
        userBookingRef.child(uid).child("room2").child(key).setValue(booking)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(Booking2Activity.this, "Success", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Booking2Activity.this, "Failed, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
