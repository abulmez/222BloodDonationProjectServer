package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("SuffersOf")
public class SuffersOf extends Model {
    public SuffersOf(){ }

    public void setIdU(Integer idU){
        set("IdU",idU);
    }
    public void setIdI(Integer idI){set("IdI",idI);}

    public Integer getIdI(){return (Integer)get("IdI");}
}
