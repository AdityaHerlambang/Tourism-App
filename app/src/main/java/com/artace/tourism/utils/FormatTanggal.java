package com.artace.tourism.utils;

public class FormatTanggal {

    public static String indo(int tanggal, int bulan, int tahun){
        String value = "";
        switch(bulan){
            case 1 :
                value = "Januari";
                break;
            case 2 :
                value = "Februari";
                break;
            case 3 :
                value = "Maret";
                break;
            case 4 :
                value = "April";
                break;
            case 5 :
                value = "Mei";
                break;
            case 6 :
                value = "Juni";
                break;
            case 7 :
                value = "Juli";
                break;
            case 8 :
                value = "Agustus";
                break;
            case 9 :
                value = "September";
                break;
            case 10 :
                value = "Oktober";
                break;
            case 11 :
                value = "November";
                break;
            case 12 :
                value = "Desember";
                break;
        }
        return tanggal+" "+value+" "+tahun;
    }

    public static String bulanTahun(int bulan, int tahun){
        String value = "";
        switch(bulan){
            case 1 :
                value = "Januari";
                break;
            case 2 :
                value = "Februari";
                break;
            case 3 :
                value = "Maret";
                break;
            case 4 :
                value = "April";
                break;
            case 5 :
                value = "Mei";
                break;
            case 6 :
                value = "Juni";
                break;
            case 7 :
                value = "Juli";
                break;
            case 8 :
                value = "Agustus";
                break;
            case 9 :
                value = "September";
                break;
            case 10 :
                value = "Oktober";
                break;
            case 11 :
                value = "November";
                break;
            case 12 :
                value = "Desember";
                break;
        }
        return value+" "+tahun;
    }

}
