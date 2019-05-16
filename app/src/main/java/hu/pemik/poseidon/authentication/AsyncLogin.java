package hu.pemik.poseidon.authentication;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

import static hu.pemik.poseidon.authentication.Authentication.*;

public class AsyncLogin extends AsyncTask<String, Void, Integer> {

    private final Consumer<Integer> callback;

    public AsyncLogin(Consumer<Integer> callback) {
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String username = strings[0];
        String password = strings[1];
        if (username.equals("") || password.equals("")) {
            return INCOMPLETE_INPUT;
        } else {
            try { // TODO change to try with resources, so that the connection always gets closed
                DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
                String UrlString =
                        "jdbc:jtds:sqlserver://193.6.33.140:1433/PoseidonDb;user=poseidon;password=Poseidon_1";
                Connection conn = DriverManager.getConnection(UrlString);
                if (conn == null) {
                    return DATABASE_ERROR;
                } else {
                    String sql =    "SELECT (\n" +
                                    "CASE\n" +
                                    "  WHEN jelszo = '" + hash(password) + "'\n" +
                                    "  THEN 'igen'\n" +
                                    "  ELSE 'nem'\n" +
                                    "END\n" +
                                    ") AS valasz\n" +
                                    "FROM bejelentkezes\n" +
                                    "WHERE felhasznalo = '" + username + "'";
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    if (!resultSet.next()) {
                        conn.close();
                        return INCORRECT_USER_NAME_OR_PASSWORD;
                    } else if (resultSet.getString("valasz").equals("igen")) {
                        conn.close();
                        return OK;
                    } else {
                        conn.close();
                        return INCORRECT_USER_NAME_OR_PASSWORD;
                    }
                }
            } catch(SQLException e) {
                Log.e("AsyncLogin", e.getMessage());
                return DATABASE_ERROR;
            }
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        callback.accept(result);
    }
}
