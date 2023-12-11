package cesur.examen.core.common;

import cesur.examen.core.worker.Worker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno:Cristian Bersabe Atienza
 * Fecha:11/12/2023
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */


public class FileUtils {

    public static void toCSV(String fileName, List<Worker> workers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Worker worker : workers) {
                String line = String.format("%d,%s,%s,%s%n",
                        worker.getId(), worker.getName(), worker.getDni(), worker.getFrom());
                writer.write(line);
            }
            System.out.println("Workers data has been successfully saved to " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to CSV file", e);
        }
    }
}





