package org.yido.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private Connection connection;
    private Statement statement;
    private PreparedStatement pstm;

    public DataBase() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:lcoalwigolig", "SYSTEM", "wigo1234");
        this.statement = connection.createStatement();
    }
    public DataBase(String db) throws Exception {
        if(db.equals("mariadb")) {
            Class.forName("org.mariadb.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/localwigo",
                    "localwigo",
                    "wigo1234");
            this.statement = connection.createStatement();
        }


    }


    public List<String> doQueryAndGetList(String select, String from, String where) {
        List<String> roles = new ArrayList<>();

        String query = "SELECT " + select + " FROM " + from + " WHERE " + where;
        try {
            this.pstm = this.connection.prepareStatement(query);
            ResultSet rs = this.pstm.executeQuery();

            while (rs.next()) {
                roles.add(rs.getString("VAL_STRING"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return roles;

    }

    public void close() throws Exception {
        this.statement.close();
        this.connection.close();
        this.pstm.close();
    }

}
