package hu.pemik.poseidon.authentication;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;

public class Authentication {
    public static final int OK = 1;
    public static final int INCORRECT_USER_NAME_OR_PASSWORD = -1;
    public static final int INCOMPLETE_INPUT = 0;
    public static final int NAME_ALREADY_TAKEN = -2;
    public static final int DIFFERENT_PASSWORDS = -3;
    public static final int DATABASE_ERROR = -10;

    public void register(String username, String password, String verifyPassword, Consumer<Integer> callback) {
        AsyncRegister asyncRegister = new AsyncRegister(callback);
        asyncRegister.execute(username, password, verifyPassword);
    }

    public void login(String username, String password, Consumer<Integer> callback) {
        AsyncLogin asyncLogin = new AsyncLogin(callback);
        asyncLogin.execute(username, password);
    }

    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            Log.e("Authentication", e.getMessage());
        }
        return null;
    }
}
