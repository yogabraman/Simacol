package com.smart.simacol.models;

public class Booking {
    private String id_booking;
    private String ruang;
    private String subjek;
    private String tanggal;
    private String start;
    private String end;
    private String password;
    private String status;
    private String userid;

    public Booking(){
    }

    public Booking(String key, String date, String start, String end, String subjek, String password, String ruang, String status, String userid) {
        this.tanggal = date;
        this.start = start;
        this.end = end;
        this.subjek = subjek;
        this.password = password;
        this.ruang = ruang;
        this.status = status;
        this.userid = userid;
        this.id_booking = key;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getPassword() {
        return password;
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

    public String getStatus() {
        return status;
    }

    public String getUserid() {
        return userid;
    }
}
