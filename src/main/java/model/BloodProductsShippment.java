package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
@Table("BloodProductsShippment")
public class BloodProductsShippment extends Model{


        public BloodProductsShippment(){};


        public BloodProductsShippment(Integer idBPS, Integer idBP, Integer idBD) {
            set("IdBPS",idBPS);
            set("IdBP",idBP);
            set("IdBd",idBD);
        }

        public Integer getIdBPS(){
            return (Integer)get("IdBPS");
        }

        public Integer getIdBP(){
            return (Integer)get("IdBP");
        }

        public Integer getIdBD(){
            return (Integer)get("IdBd");
        }

}
