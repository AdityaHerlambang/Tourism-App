package com.artace.tourism.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatMoney {

    public static String rupiah(double nominal){
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        DecimalFormat df = new DecimalFormat("#.##");
//        Log.d("FORMATMONEY",df.format(nominal));
        formatRupiah.setMaximumFractionDigits(2);
        return formatRupiah.format(Double.valueOf(df.format(nominal)));
    }

    public static String rupiahFromFloat(float nominal){
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        DecimalFormat df = new DecimalFormat("#.##");
//        Log.d("FORMATMONEY",df.format(nominal));
        formatRupiah.setMaximumFractionDigits(2);
        return formatRupiah.format(Double.valueOf(df.format(nominal)));
    }

}
