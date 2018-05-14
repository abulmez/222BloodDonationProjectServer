package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("BloodDemand")
@IdName("IdBD")
public class BloodDemand extends Model {
    public BloodDemand(){};

    public BloodDemand(Integer idBd, Integer idH, String neededType,String description,
                       String priority,Double quantity,String bloodProduct) {
        set("IdBd",idBd);
        set("IdH",idH);
        set("NeededType",neededType);
        set("Description",description);
        set("Priority",priority);
        set("Quantity",quantity);
        set("BloodProductType",bloodProduct);
    }

    public Integer getIdBd(){
        return (Integer)get("IdBd");
    }

    public Integer getIdH(){
        return (Integer)get("IdH");
    }

    public String getNeededType(){
        return (String)get("NeededType");
    }

    public String getDescription(){
        return (String)get("Description");
    }

    public String getPriority(){
        return (String)get("Priority");
    }

    public Double getQuantity(){
        return Double.parseDouble(get("Quantity").toString());
    }

    public String getBloodProductType(){return (String)get("BloodProductType");}
}
