package com.newVer_V5.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeGetter {
    public String getTime(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
