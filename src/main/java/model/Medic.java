package model;

import org.javalite.activejdbc.annotations.Table;

import java.time.LocalDate;

@Table("Users")
public class Medic extends User {
    public Medic(){}

    public Medic(String cnp, String name, LocalDate birthday, String mail, String phone,Integer idH){
        super(cnp,name,birthday,mail,phone);
        set("IdH",idH);
    }
    public Integer getIdH(){
        return (Integer)get("IdH");
    }
}
