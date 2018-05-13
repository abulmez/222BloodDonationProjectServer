package model;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.time.LocalDate;


@Table("Users ")
@IdName("IdU")
public class TCP extends User {
    public TCP(){};

    public TCP(String cnp, String name, LocalDate birthday, String mail, String phone, Integer idDC){
        super(cnp,name,birthday,mail,phone);
        set("IdDC",idDC);

    }

    public Integer getIdDC(){
        return (Integer)get("IdDc");
    }

}
