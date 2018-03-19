package model;

import org.javalite.activejdbc.Model;

import java.sql.Date;
import java.time.LocalDate;

public class User extends Model {

    public User(){}

    public User(String cnp, String name, LocalDate birthday, String mail, String phone, String bloodGroup, Integer weight){
        set("CNP",cnp);
        set("Name",name);
        set("Birthday",birthday);
        set("Mail",mail);
        set("Phone",phone);
        set("BloodGroup",bloodGroup);
        set("Weight",weight);
    }

    public Integer getIdU(){
        return (Integer)get("IdU");
    }

    public String getName(){
        return (String)get("Name");
    }

    public LocalDate getBirthday(){
        Date date = (Date)get("Birthday");
        return date.toLocalDate();
    }

    public String getMail(){
        return (String)get("Mail");
    }

    public String getPhone(){
        return (String)get("Phone");
    }

    public String getBloodGroup(){
        return (String)get("BloodGroup");
    }

    public Integer getWeight(){
        return (Integer)get("Weight");
    }
}
