package model;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.time.LocalDate;

@IdName("idH")
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

    public void setIdH(Integer idH){
        set("IdH", idH);
    }

    public Integer getIdA(){return (Integer)get("IdA");}
}
