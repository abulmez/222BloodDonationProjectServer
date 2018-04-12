package model;

import com.google.gson.annotations.Expose;
import org.javalite.activejdbc.Model;

import java.sql.Date;
import java.time.LocalDate;

public abstract class User extends Model {

    public User(){}

    public User(String cnp, String name, LocalDate birthday, String mail, String phone){
        set("CNP",cnp);
        set("Name",name);
        set("Birthday",birthday);
        set("Mail",mail);
        set("Phone",phone);

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


}
