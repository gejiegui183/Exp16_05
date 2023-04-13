package com.newVer_V4.TimeGet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {

    public String getCurrentTime(){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);

    }
}
