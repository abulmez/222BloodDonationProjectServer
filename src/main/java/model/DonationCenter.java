package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("DonationCenter")
public class DonationCenter extends Model {

    private Integer idDC;
    private Integer idA;
    private String CenterName;
    private String PhoneNumber;

    public DonationCenter(){};
    public DonationCenter( Integer idDC, Integer idA, String centerName, String phoneNumber) {

        set("IdDC",idDC);
        set("IdA",idA);
        set("CenterName",centerName);
        set("PhoneNumber",phoneNumber);
    }

    public Integer getIdDC() {
        return (Integer)get("IdDC");
    }

    public Integer getIdA() {
        return (Integer)get("IdA");
    }

    public String getCenterName() {
        return (String)get("CenterName");
    }

    public String getPhoneNumber() {
        return (String)get("PhoneNumber");
    }




}
