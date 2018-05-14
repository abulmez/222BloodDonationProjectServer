package model;


import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Date;
import java.sql.Timestamp;

@Table("DonationReport")
public class DonationReport  extends Model {
    public DonationReport(){

    }

    public void setBloodStatus(Boolean status){
        set("BloodStatus",status);
    }

    public void setBloodReport(String report){
        set("BloodReport",report);
    }

    public void setSamplingDate(Timestamp samplingDate){
        set("SamplingDate",samplingDate);
    }

    public Boolean getBloodStatus(){
        return (Boolean)get("BloodStatus");
    }

    public String getBloodReport(){
        return (String)get("BloodReport");
    }

    public Date getSamplingDate(){
        return (Date)get("SamplingDate");
    }

    public void setIdDR(Integer iddr){
        set("IdDR",iddr);
    }

    public Integer getIdDR(){
        return (Integer)get("IdDR");
    }

}
