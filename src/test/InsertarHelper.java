package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import dao.AcademiaDAO;
import dao.AcademiaDAOFactory;
import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class InsertarHelper {

    private AcademiaDAO dao = AcademiaDAOFactory.getAcademiaDAO();

    public InsertarHelper() {
        System.out.println("Creando el DAO...");
    }

    private void insertarAlumno(int id, String nombre, String rutaFoto) {
        System.out.println("\nCreando un alumno...");
        Alumno alumno = new Alumno(id, nombre);

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

    private void modificarAlumno(int id, String nombre, String rutaFoto) {
        Alumno alumno = dao.getAlumno(id);
        if (alumno != null) {
            System.out.println("\nModificando el nombre del alumno con id: " + id + " y nombre: " + alumno.getNombreAlumno());
            alumno.setNombreAlumno(nombre);

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

    private void insertarMatricula(int idAlumno, int idCurso) {
        System.out.println("\nCreando una matrícula...");
        Matricula matricula = new Matricula(0, idAlumno, idCurso, new java.util.Date());

        System.out.println("Grabando la nueva matrícula...");
        if (dao.grabarMatricula(matricula) == 1) {
            System.out.println("Se ha grabado la matrícula");
        } else {
            System.out.println("Error al grabar la matrícula");
        }
    }

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

    private void showAllData() {
        showData(dao.cargarAlumnos(), "Alumnos");
        showData(dao.cargarCursos(), "Cursos");
        showData(dao.cargarMatriculas(), "Matrículas");
    }

    private void showData(Collection<?> coleccion, String entidad) {
        System.out.println("\nMostrando..." + entidad);
        for (Object obj : coleccion) {
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        InsertarHelper programa = new InsertarHelper();

        programa.insertarAlumno(1000, "Daniel", "imagenes/cara2.jpg");
        programa.insertarAlumno(1001, "Francisco", "imagenes/cara4.jpg");

        programa.modificarAlumno(1000, "Ezequiel", null);
        programa.modificarAlumno(1000, "Agapito", "imagenes/cara1.jpg");

        programa.insertarCurso(500, "Java");
        programa.insertarCurso(501, ".NET");

        programa.modificarCurso(500, "Java avanzado");

        programa.insertarMatricula(1000, 500);
        programa.insertarMatricula(1000, 501);
        programa.insertarMatricula(1001, 500);

        Calendar fecha = GregorianCalendar.getInstance();
        fecha.set(Calendar.MONTH, 11);
        programa.modificarMatricula(1001, 500, fecha.getTime());

        programa.showAllData();

        System.out.println("\nFin del programa.");
    }

    private static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            System.out.println("Fichero demasiado grande!");
            System.exit(1);
        }

        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;

        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("No se ha podido leer completamente el fichero " + file.getName());
        }

        is.close();
        return bytes;
    }
}