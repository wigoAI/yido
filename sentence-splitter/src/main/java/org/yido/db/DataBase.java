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
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:localwigo", "SYSTEM", "wigo1234");
        this.statement = connection.createStatement();
    }


    public List<String> getRoles() {
        List<String> roles = new ArrayList<String>();

        String query = "SELECT VAL_STRING FROM TB_ARA_SEN_GROUP_STRING";
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
