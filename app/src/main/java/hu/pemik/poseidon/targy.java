package hu.pemik.poseidon;

import java.util.ArrayList;
import java.util.List;

public class targy {
    public String targynev = "";
    public String targykod = "";
    public List<String> elofeltetel = new ArrayList<>();

    public targy(String e, String k){
        targynev = e;
        targykod = k;
    }

    public targy(String e, String k, String h){
        targynev = e;
        targykod = k;
        elofeltetel.add(h);
    }
}
