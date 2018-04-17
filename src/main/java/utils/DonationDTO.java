package utils;

import org.javalite.activejdbc.Model;

import java.io.Serializable;

public class DonationDTO {
    private Integer idD;
    private String name;
    private Integer idU;
    private String cnp;
    private String status;
    private Double quantity;
    public DonationDTO(Integer idD,String name,Integer idU,String cnp,String status,Double quantity){
        this.idD=idD;
        this.name=name;
        this.idU=idU;
        this.cnp=cnp;
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

    public String getCnp() {
        return cnp;
    }

    public String getStatus() {
        return status;
    }

    public Double getQuantity() {
        return quantity;
    }

}
