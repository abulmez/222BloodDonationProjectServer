package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("Donation")
@IdName("IdD")
public class Donation extends Model {

    public Donation(){};

    public Donation(Integer idD, Integer idDC, Integer idU, Double quantity, String status) {
        set("IdD",idD);
        set("IdDC",idDC);
        set("IdU",idU);
        set("Quantity",quantity);
        set("Status",status);
    }

    public Integer getIdD(){
        return (Integer)get("IdD");
    }

    public Integer getIdDC(){
        return (Integer)get("IdDC");
    }

    public Integer getIdU(){
        return (Integer)get("IdU");
    }

    public double getQuantity(){
        return Double.parseDouble(get("Quantity").toString());
    }

    public String getStatus(){
        return (String)get("Status");
    }

    public String getReceiverName(){
        return (String)get("ReceiverName");
    }

}
