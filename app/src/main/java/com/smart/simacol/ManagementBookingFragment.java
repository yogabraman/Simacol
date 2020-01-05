package com.smart.simacol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ManagementBookingFragment extends Fragment implements View.OnClickListener {
    CardView meeting1, meeting2, infobooking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_management_booking, container, false);
        meeting1 = v.findViewById(R.id.meeting1);
        meeting2 = v.findViewById(R.id.meeting2);
        infobooking = v.findViewById(R.id.infobooking);

        meeting1.setOnClickListener(this);
        meeting2.setOnClickListener(this);
        infobooking.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meeting1:
                startActivity(new Intent(getContext(), Ruang1Activity.class));
                break;
            case R.id.meeting2:
                startActivity(new Intent(getContext(), Ruang2Activity.class));
                break;
            case R.id.infobooking:
                startActivity(new Intent(getContext(), InfoBookingActivity.class));
                break;
        }
    }
}
