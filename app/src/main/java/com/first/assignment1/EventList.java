package com.first.assignment1;

public class EventList {
    private String taskName;
    private String date;
    private String status;
    private String time;
    private int dateId;
    private String location;


    public EventList(int dateId, String taskName, String location, int status, int date, int time){
        this.taskName = taskName;
        int year=0,month=0,day=0,hour=0,min=0;

        year = date/10000;
        month = date/100%100;
        day = date%100;
        hour = time/100;
        min = time%100;

        String smouth = dealwith(month);
        String sday = dealwith(day);
        String shour = dealwith(hour);

        this.date = sday+"/"+smouth+"/"+year;
        this.time = shour+":"+min;
        if(status == 1){
            this.status = "complete";
        }else{
            this.status = "not complete";
        }
        this.location = location;
        this.dateId = dateId;
    }

    public EventList(int dateId, String taskName, int status, int date, int time){
        this.taskName = taskName;
        int year=0,month=0,day=0,hour=0,min=0;

        year = date/10000;
        month = date/100%100;
        day = date%100;
        hour = time/100;
        min = time%100;

        String smouth = dealwith(month);
        String sday = dealwith(day);
        String shour = dealwith(hour);

        this.date = sday+"/"+smouth+"/"+year;
        this.time = shour+":"+min;
        if(status == 1){
            this.status = "complete";
        }else{
            this.status = "not complete";
        }
        this.dateId = dateId;
    }

    public String getTaskName(){
        return taskName;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getStatus(){
        return status;
    }

    public String getLocation(){
        return location;
    }

    public int getDateId(){
        return dateId;
    }

    public String dealwith(int tem){
        String out = new String();
        if(tem<10){
            out = "0"+tem;
            return out;
        }else
            return String.valueOf(tem);
    }

}
