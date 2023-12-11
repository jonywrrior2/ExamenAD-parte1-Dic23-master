package cesur.examen.core.worker;

import cesur.examen.core.common.DAO;
import cesur.examen.core.common.JDBCUtils;
import lombok.extern.java.Log;
import java.util.Collections;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno:
 * Fecha:
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */
@Log public class WorkerDAO implements DAO<Worker> {

    /* Please, use this constants for the queries */

    private Connection connection;
    private final String QUERY_ORDER_BY = "SELECT * FROM trabajador ORDER BY desde";
    private final String QUERY_BY_DNI = "Select * from trabajador where dni=?";
    private final String UPDATE_BY_ID = "UPDATE trabajador SET nombre=?, desde=? WHERE id=?";

    /*public WorkerDAO(Connection c){
        connection = c;
    } Esto a revisar------------------------------------------------*/

    @Override
    public Worker save(Worker worker) {
        return null;
    }

    /**
     * Update Worker in database.
     * Remember that date objects in jdbc should be converted to sql.Date type.
     * @param worker
     * @return the updated worker or null if the worker does not exist in database.
     */

    @Override
    public Worker update(Worker worker) {
        Worker out = null;

        if (worker != null) {
            try (PreparedStatement st = JDBCUtils.getConn().prepareStatement(UPDATE_BY_ID)) {
                st.setString(1, worker.getName());
                st.setDate(2, JDBCUtils.dateUtilToSQL(worker.getFrom()));
                st.setLong(3, worker.getId());

                int rowsUpdated = st.executeUpdate();

                if (rowsUpdated > 0) {
                    out = worker;
                    log.info("Worker updated successfully: " + worker.toString());
                } else {
                    log.warning("Worker not found with id: " + worker.getId());
                }
            } catch (SQLException e) {
                log.severe("Error in update worker");
                throw new RuntimeException(e);
            }
        }

        return out;
    }


    @Override
    public boolean remove(Worker worker) {
        return false;
    }

    @Override
    public Worker get(Long id) {
        return null;
    }

    /**
     * Retrieve the worker that has this dni. Null if there is no wrker.
     * @param dni
     * @return
     */
    public Worker getWorkerByDNI(String dni) {

        /* Implemented for your pleasure */

        if( JDBCUtils.getConn()==null){
            throw new RuntimeException("Connection is not created!");
        }

        Worker out = null;

        try( PreparedStatement st = JDBCUtils.getConn().prepareStatement(QUERY_BY_DNI) ){
            st.setString(1,dni);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                Worker w = new Worker();
                w.setId( rs.getLong("id") );
                w.setName( rs.getString("nombre"));
                w.setDni( rs.getString("dni"));
                w.setFrom( rs.getDate("desde"));
                out=w;
            }
        } catch (SQLException e) {
            log.severe("Error in getWorkerById()");
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public List<Worker> getAll() {
        return null;
    }

    /**
     * Get a list with all workers, ordered by from column.
     * The first is the oldest worker and the last are the newest.
     * If there is no worker, the list is empty.
     * @return
     */

    public List<Worker> getAllOrderByFrom() {
        ArrayList<Worker> out = new ArrayList<>(0);

        try (PreparedStatement st = JDBCUtils.getConn().prepareStatement(QUERY_ORDER_BY)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Worker w = new Worker();
                w.setId(rs.getLong("id"));
                w.setName(rs.getString("nombre"));
                w.setDni(rs.getString("dni"));
                w.setFrom(rs.getDate("desde"));
                out.add(w);
            }
        } catch (SQLException e) {
            log.severe("Error in getAllOrderByFrom()");
            throw new RuntimeException(e);
        }

        // Order the list by "from" date in ascending order
        Collections.sort(out, Comparator.comparing(Worker::getFrom));

        return out;
    }

}
