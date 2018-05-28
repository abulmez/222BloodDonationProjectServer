package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("Reservation")
@IdName("IdU")
public class Reservation extends Model {
    public Reservation() {
    }

    public Reservation(Integer idDS, Integer idDC, String status) {
        set("IdDS",idDS);
        set("IdU",idDC);
        set("Status",status);
    }

    public Integer getIdDS(){
        return (Integer)get("IdDS");
    }

    public Integer getIdU(){
        return (Integer)get("IdDC");
    }

    public String getStatus(){
        return (String)get("Status");
    }
}
