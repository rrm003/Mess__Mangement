package com.example.rrm.mess__management;


import java.util.Calendar;


import static java.util.Calendar.HOUR_OF_DAY;

public class Time {

    private int hr;
    private int day;

    Time() {
        Calendar calendar = Calendar.getInstance();
        hr = calendar.get(HOUR_OF_DAY);
        day = calendar.get(Calendar.DAY_OF_YEAR);
    }

    public int getHr(){
        return hr;
    }

    public int getDay(){
        return  day;
    }
}