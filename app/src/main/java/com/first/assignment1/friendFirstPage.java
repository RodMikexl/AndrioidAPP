package com.first.assignment1;

public class friendFirstPage {
    private String name;
    private int Id;
    private int age;
    private String fname;
    private String lname;
    private String gender;
    private String address;

    public friendFirstPage(String fname, String lname, int id){

        this.name = fname+" "+lname;

        this.Id = id;
    }
    public friendFirstPage( int id, String fname, String lname, String gender,int age, String address){

        this.lname = lname;
        this.fname = fname;
        this.Id = id;
        this.address = address;
        this.age = age;
        this.gender = gender;
    }

    public String getFname(){
        return fname;
    }

    public String getLname(){
        return lname;
    }

    public String getGender(){
        return gender;
    }

    public String getAddress(){
        return address;
    }

    public int getAge(){
        return age;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return Id;
    }
}
