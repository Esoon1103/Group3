package com.example.myapplication.model;

public class Reservation {
    private String key, table_id, date, time;

    public Reservation(){

    }

    public String getTable_id(){return table_id;}
    public void setTable_id(String table_id){this.table_id=table_id;}

    public String getKey(){return key;}
    public void setKey(String key){this.key=key;}

    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}

    public String getTime(){return time;}
    public void setTime(String time){this.time=date;}
}


