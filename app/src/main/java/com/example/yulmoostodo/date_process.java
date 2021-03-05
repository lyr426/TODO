package com.example.yulmoostodo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class date_process {
    //오늘 날짜 불러오기
    public static String getToday(){
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        return dateF.format(new Date());
    }
    public static String getToday_text(){
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy년MM월dd일");
        return dateF.format(new Date());
    }


    //인풋 날짜의 전 날/ 다음 날 날짜 구하기
    public static String yesterday(String day){
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateF.parse(day);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE,-1);
        return dateF.format(calendar.getTime());
    }

    public static String tomorrow(String day){
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateF.parse(day);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE,+1);
        return dateF.format(calendar.getTime());
    }

}
