package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;

import java.sql.Date;
import java.time.LocalDate;


@IdName("IdU")
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

    public void setCnp(String cnp){
        set("CNP", cnp);
    }

    public void setName(String name){
        set("Name", name);
    }

    public void setBirthday(String date){
        set("Birthday",date);
    }

    public void setMail(String mail){
        set("Mail",mail);
    }

    public void setPhone(String phone){
        set("Phone",phone);
    }

    public String getMail(){
        return (String)get("Mail");
    }

    public String getPhone(){
        return (String)get("Phone");
    }

    public String getCNP(){return (String)get("CNP");}


}
