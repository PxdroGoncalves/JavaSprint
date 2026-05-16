
package br.com.nuvemtech.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    public static Connection conexao() throws SQLException, ClassNotFoundException {

        Class.forName("oracle.jdbc.driver.OracleDriver");

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        // Se estiver local e não tiver variável de ambiente
        if (url == null || user == null || password == null) {

            url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
            user = "rm567265";
            password = "290406";
        }

        return DriverManager.getConnection(url, user, password);
    }
}