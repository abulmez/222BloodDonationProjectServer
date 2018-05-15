package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@IdName("idA")
@Table("Adress")
@IdName("IdA")
public class Adress extends Model {

    public Adress(){
    };

    public Integer getIdA(){return (Integer)get("IdA");}

    public void setStreet(String street){
        set("Street", street);
    }

    public void setStreetNr(Integer streetNr){
        set("StreetNumber", streetNr);
    }

    public void setBlock(Integer block){
        set("BlockNumber",block);
    }

    public void setEntrance(String entrance){
        set("Entrance",entrance);
    }

    public void setFloor(Integer floor){
        set("Floor",floor);
    }

    public void setApartNr(Integer apartNr){
        set("ApartmentNumber",apartNr);
    }

    public void setCity(String city){
        set("City",city);
    }

    public void setCounty(String county){
        set("County",county);
    }

    public void setCountry(String country){
        set("Country",country);
    }

    public String getStreet(){
        return (String)get("Street");
    }

    public Integer getStreetNr(){
        return (Integer)get("StreetNumber");
    }

    public Integer getBlock(){
        return (Integer)get("BlockNumber");
    }

    public String getEntrance(){
        return (String)get("Entrance");
    }

    public Integer getFloor(){
        return (Integer)get("Floor");
    }

    public Integer getApartNr(){
        return (Integer)get("ApartmentNumber");
    }

    public String getCity(){
        return (String)get("City");
    }

    public String getCounty(){
        return (String)get("County");
    }

    public String getCountry(){
        return (String)get("Country");
    }
}
