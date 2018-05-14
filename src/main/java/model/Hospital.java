package model;


import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("Hospital")
public class Hospital extends Model {
    public Hospital(){ }

    public Integer getIdH(){
        return (Integer)get("IdH");
    }
}
