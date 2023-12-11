package cesur.examen.core.common;

import cesur.examen.App;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno: Cristian Bersabé Atienza
 * Fecha: 11/12/2023
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */
@Log
public class JDBCUtils {

    private static Connection conn;

    static {
        Logger logger = Logger.getLogger(JDBCUtils.class.getName());

        try (InputStream input = App.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties cfg = new Properties();
            cfg.load(input);

            String url = cfg.getProperty("url");
            String user = cfg.getProperty("user");
            String password = cfg.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);

            if (conn == null) {
                log.warning("JDBCUtils Not implemented yet!");
            } else {
                log.info("Successfully connected!");
            }

        } catch (IOException e) {
            log.severe("Error loading db.properties");
            throw new RuntimeException(e);
        } catch (Exception ex) {
            log.severe("Error connecting to database");
            throw new RuntimeException(ex);
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static java.sql.Date dateUtilToSQL(java.util.Date d) {
        return new java.sql.Date(d.getTime());
    }
}
