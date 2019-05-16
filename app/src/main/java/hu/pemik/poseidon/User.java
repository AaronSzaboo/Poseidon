package hu.pemik.poseidon;

import android.util.Log;

public class User {
    String uid = "";
    String name = "";
    String email = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){
        uid = "";
        name = "";
        email = "";
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        Log.d("EREDMENY", uid + " " + this.uid);
        this.uid = uid;
        Log.d("EREDMENY", uid + " " + this.uid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "User{" +
                "uid='" + uid + '\''+
                ", name='" + name + '\''+
                ", email='" + email + '\''+
                '}';
    }
}
