package com.smart.simacol.models;

public class BookingAll {
    private String id_booking;
    private String ruang;
    private String subjek;
    private String tanggal;
    private String start;
    private String end;
    private String status;
    private String user;

    public BookingAll(){}

    public BookingAll(String date, String start, String end, String subjek, String ruang, String status, String name) {
        this.tanggal = date;
        this.start = start;
        this.end = end;
        this.subjek = subjek;
        this.ruang = ruang;
        this.status = status;
        this.user = name;
    }

    public String getId_booking() {
        return id_booking;
    }

    public String getRuang() {
        return ruang;
    }

    public String getSubjek() {
        return subjek;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
