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

    public void setId(Integer id){
        set("IdLD", id);
    }

    public void setUsername(String username){
        set("Username",username);
    }

    public void setPassword(String password){
        set("Password",password);
    }

    public void setType(String type){
        set("UserType",type);
    }

    public String getUsername(){
        return (String)get("Username");
    }

    public String getUserType(){
        return (String)get("UserType");
    }

    public String getPassword() {return  (String)get("Password"); }
}
