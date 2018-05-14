package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Date;
import java.time.LocalDate;

@Table("Users")
public class UserPacient extends Model {

    public UserPacient(){
    };

    public UserPacient(Integer idU,String CNP,String name,LocalDate birthday,String mail,String phone,String bloodGroup,Double weight,Integer idA,Integer idDC,Integer idH){
        set("IdU",idU);
        set("Name",name);
        set("Birthday",birthday);
        set("CNP",CNP);
        set("Mail",mail);
        set("Phone",phone);
        set("Mail",idU);
        set("Phone",idDC);
        set("BloodGroup",bloodGroup);
        set("Weight",weight);
        set("IdA",idA);
        set("IdDC",idDC);
        set("IdH",idH);

    }

    public Integer getIdU(){
        return (Integer)get("IdU");
    }

    public String getName(){
        return (String)get("Name");
    }

    public LocalDate getBirthday(){

        return  (LocalDate)get("Birthday");
    }

    public String getMail(){
        return (String)get("Mail");
    }

    public String getPhone(){
        return (String)get("Phone");
    }

    public String getCNP(){return (String)get("CNP");}

    public String getBloodGroup(){
        return (String)get("BloodGroup");
    }

    public Double getWeight(){
        return (Double)get("Weight");
    }

    public Integer getIdA(){
        return (Integer)get("IdA");
    }

    public Integer getIdDC(){
        return (Integer)get("IdDC");
    }

    public Integer getIdH(){
        return (Integer)get("IdH");
    }

    //////

    /*public void setName(String name){
        set("Name", name);
    }

    public void setBirthday(Date date){

        Date date = (Date)get("Birthday");
        return date.toLocalDate();
    }*/

}
