package model;

import org.javalite.activejdbc.annotations.Table;

import java.time.LocalDate;

@Table("dbo.Users")
public class Donor extends User {

    public Donor(){}

    public Donor(String cnp, String name, LocalDate birthday, String mail, String phone, String bloodGroup, Double weight){
        super(cnp,name,birthday,mail,phone);
        set("BloodGroup",bloodGroup);
        set("Weight",weight);
    }

    public String getBloodGroup(){
        return (String)get("BloodGroup");
    }

    public Double getWeight(){
        return Double.parseDouble( get("Weight").toString());
    }

    public Integer getIdA(){return (Integer)get("IdA");}

    public String getMail(){return (String)get("Mail");}

    public String getPhone(){return (String)get("Phone");}

    public void setIdA(Integer idA){
        set("IdA", idA);
    }
}
