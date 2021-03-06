package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;
import java.time.LocalDate;

@Table("DonationSchedule")
public class DonationSchedule extends Model {

    public DonationSchedule() {
    };

    public DonationSchedule(Integer idDS, Integer idDC, Timestamp donationDateTime, Integer availableSpots, String status) {
        set("IdDS",idDS);
        set("IdDC",idDC);
        set("DonationDateTime",donationDateTime);
        set("AvailableSpots",availableSpots);
        set("Status",status);
    }

    public Integer getIdDS(){
        return (Integer)get("IdDS");
    }

    public Integer getIdDC(){
        return (Integer)get("IdDC");
    }

    public Timestamp getDonationDateTime(){
        return (Timestamp)get("DonationDateTime");
    }

    public Integer getAvailableSpots(){
        return (Integer)get("AvailableSpots");
    }

    /*public String getStatus(){
        return (String)get("Status");
    }*/
}
