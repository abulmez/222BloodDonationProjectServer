package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;


@Table("LoginData")
public class UserLoginData extends Model {

    public Integer getId(){
        return (Integer)get("IdLD");
    }

    public String getUserType(){
        return (String)get("UserType");
    }
}
