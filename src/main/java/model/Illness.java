package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("Illness")
public class Illness extends Model {
    public Illness(){ }

    public void setIdI(Integer idI){set("IdI",idI);}

    public void setName(String name){set("Name",name);}

    public void setDescription(String description){set("Description",description);}

    public String getDescription(){return (String)get("Description");}
}
