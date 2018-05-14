package model;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.time.LocalDate;

@IdName("idU")
@Table("dbo.Users  ")
public class Admin extends User {

    public Admin(String cnp, String name, LocalDate birthday, String mail, String phone){
        super(cnp,name,birthday,mail,phone);
    }

    public Admin(){}

    public Integer getIdA(){return (Integer)get("IdA");}
}
