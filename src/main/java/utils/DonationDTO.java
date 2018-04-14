package utils;

import org.javalite.activejdbc.Model;

import java.io.Serializable;

public class DonationDTO {
    private Integer idD;
    private String name;
    private Integer idU;
    private String status;
    private Double quantity;
    public DonationDTO(Integer idD,String name,Integer idU,String status,Double quantity){
        this.idD=idD;
        this.name=name;
        this.idU=idU;
        this.status=status;
        this.quantity=quantity;
    }

    public Integer getIdD() {
        return idD;
    }

    public String getName() {
        return name;
    }

    public Integer getIdU() {
        return idU;
    }

    public String getStatus() {
        return status;
    }

    public Double getQuantity() {
        return quantity;
    }

}
