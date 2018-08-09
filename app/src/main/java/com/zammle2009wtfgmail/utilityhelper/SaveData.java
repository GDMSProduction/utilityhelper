package com.zammle2009wtfgmail.utilityhelper;

public class SaveData {
    String email,password,reenterPassword;

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getReenterPassword(){
        return reenterPassword;
    }

    public void setReenterPassword(String reenterPassword){
        this.reenterPassword = reenterPassword;
    }

    public SaveData(String email, String password, String reenterPassword){
        this.email = email;
        this.password = password;
        this.reenterPassword = reenterPassword;
    }
}
