package edu.intec.willgo;


/**
 * Created by LUIS on 4/3/2015.
 */
public class Preference {
    private String name;
    private String place;
    private String coor;

    public Preference(String name, String place, String coor){
        this.name=name;
        this.place=place;
        this.coor=coor;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getPlace(){
        return place;
    }
    public void setPlace(String place){
        this.place=place;
    }

    public String getCoor(){
        return coor;
    }
    public void setCoor(String coor){
        this.coor=coor;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
