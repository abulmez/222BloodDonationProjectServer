package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("BloodDemand")
public class BloodDemand extends Model {
    public BloodDemand(){};

    public BloodDemand(Integer idBd, Integer idH, String neededType,String description,
                       String priority,Integer quantity) {
        set("IdBd",idBd);
        set("IdH",idH);
        set("NeededType",neededType);
        set("Description",description);
        set("Priority",priority);
        set("Quantity",quantity);
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

    public Integer getQuantity(){
        return (Integer)get("Quantity");
    }
}
