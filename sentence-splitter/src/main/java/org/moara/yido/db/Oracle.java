package org.moara.yido.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.stream.Stream;

public class Oracle implements DataBaseHandler {

    private Connection connection;
    private Statement statement;
    private PreparedStatement pstm;

    private Oracle() {

    }

    @Override
    public void close() {

    }

    @Override
    public void connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:lcoalwigolig", "SYSTEM", "wigo1234");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stream<String> doQuery(String query) {
        return null;
    }
}
