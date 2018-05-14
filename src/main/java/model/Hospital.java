package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;
@IdName("IdH")
@Table("Hospital")
public class Hospital extends Model{

    private Integer idH;
    private Integer idA;
    private String hospitalName;
    private String phoneNumber;

    public Hospital(){};
    public Hospital( Integer idH, Integer idA, String hospitalName, String phoneNumber) {

        set("IdH",idH);
        set("IdA",idA);
        set("HospitalName",hospitalName);
        set("PhoneNumber",phoneNumber);
    }

    public Integer getIdH() {
        return (Integer)get("IdH");
    }

    public Integer getIdA() {
        return (Integer)get("IdA");
    }

    public String getHospitalName() {
        return (String)get("HospitalName");
    }

    public String getPhoneNumber() {
        return (String)get("PhoneNumber");
    }
}
