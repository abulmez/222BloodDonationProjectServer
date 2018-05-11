package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.Date;


@Table("AvailableBloodProducts")
public class AvailableBloodProducts extends Model {
    public AvailableBloodProducts(){};


    public AvailableBloodProducts(Integer idBP, Integer idD,String ProductType,Date ValidUntil,Double Quantity) {
        set("IdBP",idBP);
        set("IdD",idD);
        set("ProductType",ProductType);
        set("ValidUntil",ValidUntil);
        set("Quantity",Quantity);
    }

    public Integer getIdBP(){
        return (Integer)get("IdBP");
    }

    public Integer getIdD(){
        return (Integer)get("IdD");
    }

    public String getProductType(){
        return (String)get("ProductType");
    }

    public Date getValidUntil(){return (Date)get("ValidUntil");};

    public Double getQuantity(){return (Double)get("Quantity");};
}
