package com.example.tics.ui.classes;

public class Tic {
    private int TicCount;
    private String TicDate;
    private String TicTime;
    private byte[] Picture;

    public Tic(int TicCount, String TicDate, String TicTime, byte[] Picture) {
        this.TicCount = TicCount;
        this.TicDate = TicDate;
        this.TicTime = TicTime;
        this.Picture = Picture;
    }

    public int getTicCount() {
        return TicCount;
    }

    public String getTicDate() {
        return TicDate;
    }

    public String getTicTime() {
        return TicTime;
    }

    public byte[] getPicture() {
        return Picture;
    }
}
