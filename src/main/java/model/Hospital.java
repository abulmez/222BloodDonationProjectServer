package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("Hospital")
@IdName("IdH")
public class Hospital extends Model {

    public Hospital(){

    }

    public Hospital(Integer idA,String hospitalName,String phoneNumber) {
        set("IdA", idA);
        set("HospitalName", hospitalName);
        set("PhoneNumber", phoneNumber);
    }

    public Integer getIdH(){
        return (Integer)get("IdH");
    }

    public Integer getIdA(){
        return (Integer)get("IdA");
    }

    public String getHospitalName(){
        return (String)get("HospitalName");
    }

    public String getPhoneNumber(){
        return (String)get("PhoneNumber");
    }


}