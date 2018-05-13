package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.time.LocalDate;

@Table("DonationReport")
public class DonationReport extends Model {
    public DonationReport(){};

    public DonationReport(Integer idDR, LocalDate samplingDate, Boolean bloodStatus, String bloodReport) {
        set("IdDR",idDR);
        set("SamplingDate",samplingDate);
        set("BloodStatus",bloodStatus);
        set("BloodReport",bloodReport);
    }

    public Integer getIdDR(){
        return (Integer)get("IdDR");
    }

    public LocalDate getSamplingDate(){
        return (LocalDate)get("SamplingDate");
    }

    public Boolean getBloodStatus(){
        return (Boolean)get("BloodStatus");
    }

    public String getBloodReport(){
        return (String)get("BloodReport");
    }

}
