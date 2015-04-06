package edu.intec.willgo;


/**
 * Created by LUIS on 4/3/2015.
 */
public class Preference {
    private String name;
    private String place;
    private String location;
    private String coor;
    private int id;

    public Preference(int id,String name, String place, String location,String coor){
        this.id=id;
        this.name=name;
        this.place=place;
        this.coor=coor;
        this.setLocation(location);
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
