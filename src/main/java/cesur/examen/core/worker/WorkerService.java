package cesur.examen.core.worker;

import java.util.Date;
import lombok.extern.java.Log;


/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno:
 * Fecha:
 *
 *  No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 *  En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 *  o para seguir la traza de ejecución.
 */
/**
 *  Services classes offers methods to our main application, and can
 *  use multiple methods from DAOs and Entities.
 *  It's a layer between Data Access Layer and Aplication Layer (where
 *  Main app and controllers lives)
 *  It helps to encapsulate multiple opperations with DAOs that can be
 *  reused in application layer.
 */
@Log
public class WorkerService {
    /*
    RenovateWorker() set "from" date of the worker with this dni to today's date.
    Remember Date().
    Returns the new updated worker, null if fails or dni doesn't exist.
    */
    public static Worker renovateWorker(String dni) {
        Worker out = null;

        WorkerDAO workerDAO = new WorkerDAO();
        Worker existingWorker = workerDAO.getWorkerByDNI(dni);

        if (existingWorker != null) {
            existingWorker.setFrom(new Date()); // Set "from" date to today's date
            out = workerDAO.update(existingWorker);
            if (out != null) {
                log.info("Worker with DNI " + dni + " has been successfully renovated.");
            } else {
                log.warning("Failed to renovate worker with DNI " + dni);
            }
        } else {
            log.warning("Worker with DNI " + dni + " not found.");
        }

        return out;
    }
}



