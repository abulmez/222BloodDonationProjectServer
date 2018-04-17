package model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.io.InputStream;


@Table("LoginData")
@IdName("IdLD")
public class UserLoginData extends Model {

    public UserLoginData(){};

    public Integer getId(){
        return (Integer)get("IdLD");
    }

    public String getUsername(){
        return (String)get("Username");
    }

    public String getUserType(){
        return (String)get("UserType");
    }

    public String getPassword() {return  (String)get("Password"); }
}
