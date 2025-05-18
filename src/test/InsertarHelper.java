package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import dao.AcademiaDAO;
import dao.AcademiaDAOImplJDBC;
import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class InsertarHelper {

    private AcademiaDAO dao = null;

    public InsertarHelper() {
        System.out.println("Creando el DAO...");
        dao = new AcademiaDAOImplJDBC();
    }

    /**
     * Método para insertar un nuevo alumno con su foto.
     */
    private void insertarAlumno(int id, String nombre, String rutaFoto) {
        System.out.println("\nCreando un alumno...");
        Alumno alumno = new Alumno(id, nombre);

        // Leer la foto del disco y guardarla en el objeto Alumno
        File file = new File(rutaFoto);
        byte[] foto = null;
        try {
            foto = getBytesFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        alumno.setFoto(foto);

        System.out.println("Grabando el nuevo alumno...");
        if (dao.grabarAlumno(alumno) == 1) {
            System.out.println("Se ha grabado el alumno");
        } else {
            System.out.println("Error al grabar el alumno");
        }
    }

    /**
     * Método para modificar un alumno existente, incluyendo su foto.
     */
    private void modificarAlumno(int id, String nombre, String rutaFoto) {
        Alumno alumno = dao.getAlumno(id);
        if (alumno != null) {
            System.out.println("\nModificando el nombre del alumno con id: " + id + " y nombre: " + alumno.getNombreAlumno());
            alumno.setNombreAlumno(nombre);

            // Si se ha pasado la ruta de la foto, actualizarla
            if (rutaFoto != null) {
                System.out.println("\nModificando la foto del alumno con id: " + id + " y nombre: " + alumno.getNombreAlumno());
                File file = new File(rutaFoto);
                byte[] foto = null;
                try {
                    foto = getBytesFromFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                alumno.setFoto(foto);
            }

            if (dao.actualizarAlumno(alumno) == 1) {
                System.out.println("Se ha modificado el alumno con id: " + id);
            } else {
                System.out.println("Error al modificar el alumno con id: " + id);
            }
        } else {
            System.out.println("No se encontró el alumno con id: " + id);
        }
    }

    /**
     * Método para insertar un nuevo curso.
     */
    private void insertarCurso(int id, String nombre) {
        System.out.println("\nCreando un curso...");
        Curso curso = new Curso(id, nombre);
        System.out.println("Grabando el nuevo curso...");
        if (dao.grabarCurso(curso) == 1) {
            System.out.println("Se ha grabado el curso");
        } else {
            System.out.println("Error al grabar el curso");
        }
    }

    /**
     * Método para modificar un curso existente.
     */
    private void modificarCurso(int id, String nombre) {
        Curso curso = dao.getCurso(id);
        if (curso != null) {
            System.out.println("\nModificando el nombre del curso con id: " + id + " y nombre: " + curso.getNombreCurso());
            curso.setNombreCurso(nombre);

            if (dao.actualizarCurso(curso) == 1) {
                System.out.println("Se ha modificado el curso con id: " + id);
            } else {
                System.out.println("Error al modificar el curso con id: " + id);
            }
        } else {
            System.out.println("No se encontró el curso con id: " + id);
        }
    }

    /**
     * Método para insertar una nueva matrícula.
     */
    private void insertarMatricula(int idAlumno, int idCurso) {
        System.out.println("\nCreando una matrícula...");

        // Crear la matrícula con la fecha actual
        Matricula matricula = new Matricula(0, idAlumno, idCurso, new java.util.Date());

        System.out.println("Grabando la nueva matrícula...");
        if (dao.grabarMatricula(matricula) == 1) {
            System.out.println("Se ha grabado la matrícula");
        } else {
            System.out.println("Error al grabar la matrícula");
        }
    }

    /**
     * Método para modificar la fecha de una matrícula existente.
     */
    private void modificarMatricula(int idAlumno, int idCurso, java.util.Date fecha) {
        long idMatricula = dao.getIdMatricula(idAlumno, idCurso);
        if (idMatricula != -1) {
            Matricula matricula = dao.getMatricula(idMatricula);
            if (matricula != null) {
                System.out.println("\nModificando fecha de la matrícula...");
                matricula.setFechaInicio(fecha);

                if (dao.actualizarMatricula(matricula) == 1) {
                    System.out.println("Se ha modificado la matrícula");
                } else {
                    System.out.println("Error al modificar la matrícula");
                }
            } else {
                System.out.println("No se encontró la matrícula con id: " + idMatricula);
            }
        } else {
            System.out.println("No se encontró la matrícula para el alumno: " + idAlumno + " y curso: " + idCurso);
        }
    }

    /**
     * Método para mostrar todos los datos de alumnos, cursos y matrículas.
     */
    private void showAllData() {
        showData(dao.cargarAlumnos(), "Alumnos");
        showData(dao.cargarCursos(), "Cursos");
        showData(dao.cargarMatriculas(), "Matrículas");
    }

    /**
     * Método auxiliar para mostrar una colección de datos.
     */
    private void showData(Collection<?> coleccion, String entidad) {
        System.out.println("\nMostrando..." + entidad);
        for (Object obj : coleccion) {
            System.out.println(obj);
        }
    }

    /**
     * Método principal para probar la inserción y modificación de datos.
     */
    public static void main(String[] args) {
        InsertarHelper programa = new InsertarHelper();

        // Insertar alumnos con fotos
        programa.insertarAlumno(1000, "Daniel", "imagenes/cara2.jpg");
        programa.insertarAlumno(1001, "Francisco", "imagenes/cara4.jpg");

        // Modificar el primer alumno (solo nombre)
        programa.modificarAlumno(1000, "Ezequiel", null);

        // Modificar el primer alumno (nombre y foto)
        programa.modificarAlumno(1000, "Agapito", "imagenes/cara1.jpg");

        // Insertar cursos
        programa.insertarCurso(500, "Java");
        programa.insertarCurso(501, ".NET");

        // Modificar el curso creado
        programa.modificarCurso(500, "Java avanzado");

        // Insertar matrículas
        programa.insertarMatricula(1000, 500);
        programa.insertarMatricula(1000, 501);
        programa.insertarMatricula(1001, 500);

        // Modificar la fecha de la segunda matrícula
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.set(Calendar.MONTH, 11); // Diciembre (los meses en Calendar van de 0 a 11)
        programa.modificarMatricula(1001, 500, fecha.getTime());

        // Mostrar todos los datos
        programa.showAllData();

        System.out.println("\nFin del programa.");
    }

    /**
     * Método para leer un archivo y convertirlo en un array de bytes.
     */
    private static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();

        // Verificar que el archivo no sea demasiado grande
        if (length > Integer.MAX_VALUE) {
            System.out.println("Fichero demasiado grande!");
            System.exit(1);
        }

        // Crear un array de bytes para almacenar los datos
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;

        // Leer el archivo y almacenar los datos en el array de bytes
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Verificar que se hayan leído todos los bytes
        if (offset < bytes.length) {
            throw new IOException("No se ha podido leer completamente el fichero " + file.getName());
        }

        // Cerrar el InputStream y devolver los bytes
        is.close();
        return bytes;
    }
}