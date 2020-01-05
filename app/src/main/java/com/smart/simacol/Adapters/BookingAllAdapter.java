package com.smart.simacol.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.smart.simacol.R;
import com.smart.simacol.models.BookingAll;

public class BookingAllAdapter extends FirebaseRecyclerAdapter<BookingAll, BookingAllAdapter.BookingAllHolder> {

    public BookingAllAdapter(@NonNull FirebaseRecyclerOptions<BookingAll> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final BookingAllHolder holder, int position, @NonNull final BookingAll model) {
        holder.text_subjek.setText(model.getSubjek());
        holder.text_tgl.setText(model.getTanggal());
        holder.text_start.setText(model.getStart());
        holder.text_end.setText(model.getEnd());
        holder.text_user.setText(model.getUser());
    }

    @NonNull
    @Override
    public BookingAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_all, parent, false);
        return new BookingAllAdapter.BookingAllHolder(view);
    }

    class BookingAllHolder extends RecyclerView.ViewHolder{
        TextView text_subjek, text_tgl, text_start, text_end, text_user;
        CardView cardBookingAll;

        public BookingAllHolder(View itemView) {
            super(itemView);
            text_subjek = itemView.findViewById(R.id.text_subjek);
            text_tgl = itemView.findViewById(R.id.text_tgl);
            text_start = itemView.findViewById(R.id.text_start);
            text_end = itemView.findViewById(R.id.text_end);
            text_user = itemView.findViewById(R.id.text_user);
            cardBookingAll = itemView.findViewById(R.id.cardBookingAll);
        }
    }
}
