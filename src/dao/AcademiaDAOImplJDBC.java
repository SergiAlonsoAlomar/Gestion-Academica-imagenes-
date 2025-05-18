package dao;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class AcademiaDAOImplJDBC implements AcademiaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/dbformacion";
    private static final String USER = "root";
    private static final String PASSWORD = "admin123";

    private static final String FIND_ALL_ALUMNOS_SQL = "SELECT id_alumno, nombre_alumno, foto FROM alumnos";
    private static final String ADD_ALUMNO_SQL = "INSERT INTO alumnos (id_alumno, nombre_alumno, foto) VALUES (?, ?, ?)";
    private static final String UPDATE_ALUMNO_SQL = "UPDATE alumnos SET nombre_alumno = ?, foto = ? WHERE id_alumno = ?";
    private static final String GET_ALUMNO_SQL = "SELECT id_alumno, nombre_alumno, foto FROM alumnos WHERE id_alumno = ?";
    private static final String DELETE_ALUMNO_SQL = "DELETE FROM alumnos WHERE id_alumno = ?";

    private static final String FIND_ALL_CURSOS_SQL = "SELECT id_curso, nombre_curso FROM cursos";
    private static final String ADD_CURSO_SQL = "INSERT INTO cursos (id_curso, nombre_curso) VALUES (?, ?)";
    private static final String UPDATE_CURSO_SQL = "UPDATE cursos SET nombre_curso = ? WHERE id_curso = ?";
    private static final String GET_CURSO_SQL = "SELECT id_curso, nombre_curso FROM cursos WHERE id_curso = ?";
    private static final String DELETE_CURSO_SQL = "DELETE FROM cursos WHERE id_curso = ?";

    private static final String FIND_ALL_MATRICULAS_SQL = "SELECT id_matricula, id_alumno, id_curso, fecha_inicio FROM matriculas";
    private static final String ADD_MATRICULA_SQL = "INSERT INTO matriculas (id_alumno, id_curso, fecha_inicio) VALUES (?, ?, ?)";
    private static final String UPDATE_MATRICULA_SQL = "UPDATE matriculas SET fecha_inicio = ? WHERE id_matricula = ?";
    private static final String GET_MATRICULA_SQL = "SELECT id_matricula, id_alumno, id_curso, fecha_inicio FROM matriculas WHERE id_matricula = ?";
    private static final String DELETE_MATRICULA_SQL = "DELETE FROM matriculas WHERE id_matricula = ?";

    public AcademiaDAOImplJDBC() {}

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void releaseConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Collection<Alumno> cargarAlumnos() {
        Collection<Alumno> alumnos = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_ALL_ALUMNOS_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_alumno");
                String nombre = rs.getString("nombre_alumno");
                Blob fotoBlob = rs.getBlob("foto");

                Alumno alumno = new Alumno(id, nombre);
                if (fotoBlob != null) {
                    alumno.setFoto(fotoBlob.getBytes(1, (int) fotoBlob.length()));
                } else {
                    alumno.setFoto(null);
                }
                alumnos.add(alumno);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return alumnos;
    }

    @Override
    public Alumno getAlumno(int idAlumno) {
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(GET_ALUMNO_SQL);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_alumno");
                String nombre = rs.getString("nombre_alumno");
                Blob fotoBlob = rs.getBlob("foto");

                Alumno alumno = new Alumno(id, nombre);
                if (fotoBlob != null) {
                    alumno.setFoto(fotoBlob.getBytes(1, (int) fotoBlob.length()));
                } else {
                    alumno.setFoto(null);
                }
                return alumno;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return null;
    }

    @Override
    public int grabarAlumno(Alumno alumno) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(ADD_ALUMNO_SQL);
            ps.setInt(1, alumno.getIdAlumno());
            ps.setString(2, alumno.getNombreAlumno());

            if (alumno.getFoto() != null) {
                ps.setBinaryStream(3, new ByteArrayInputStream(alumno.getFoto()));
            } else {
                ps.setBinaryStream(3, null);
            }

            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public int actualizarAlumno(Alumno alumno) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_ALUMNO_SQL);
            ps.setString(1, alumno.getNombreAlumno());

            if (alumno.getFoto() != null) {
                ps.setBinaryStream(2, new ByteArrayInputStream(alumno.getFoto()));
            } else {
                ps.setBinaryStream(2, null);
            }

            ps.setInt(3, alumno.getIdAlumno());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public int borrarAlumno(int idAlumno) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_ALUMNO_SQL);
            ps.setInt(1, idAlumno);
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public Collection<Curso> cargarCursos() {
        Collection<Curso> cursos = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_ALL_CURSOS_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_curso");
                String nombre = rs.getString("nombre_curso");
                cursos.add(new Curso(id, nombre));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return cursos;
    }

    @Override
    public Curso getCurso(int idCurso) {
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(GET_CURSO_SQL);
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_curso");
                String nombre = rs.getString("nombre_curso");
                return new Curso(id, nombre);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return null;
    }

    @Override
    public int grabarCurso(Curso curso) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(ADD_CURSO_SQL);
            ps.setInt(1, curso.getIdCurso());
            ps.setString(2, curso.getNombreCurso());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public int actualizarCurso(Curso curso) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_CURSO_SQL);
            ps.setString(1, curso.getNombreCurso());
            ps.setInt(2, curso.getIdCurso());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public int borrarCurso(int idCurso) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_CURSO_SQL);
            ps.setInt(1, idCurso);
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public Collection<Matricula> cargarMatriculas() {
        Collection<Matricula> matriculas = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(FIND_ALL_MATRICULAS_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long idMatricula = rs.getLong("id_matricula");
                int idAlumno = rs.getInt("id_alumno");
                int idCurso = rs.getInt("id_curso");
                Date fechaInicio = rs.getDate("fecha_inicio");
                matriculas.add(new Matricula(idMatricula, idAlumno, idCurso, fechaInicio));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return matriculas;
    }

    @Override
    public Matricula getMatricula(long idMatricula) {
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(GET_MATRICULA_SQL);
            ps.setLong(1, idMatricula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id_matricula");
                int idAlumno = rs.getInt("id_alumno");
                int idCurso = rs.getInt("id_curso");
                Date fechaInicio = rs.getDate("fecha_inicio");
                return new Matricula(id, idAlumno, idCurso, fechaInicio);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return null;
    }

    @Override
    public int grabarMatricula(Matricula matricula) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(ADD_MATRICULA_SQL);
            ps.setInt(1, matricula.getIdAlumno());
            ps.setInt(2, matricula.getIdCurso());
            ps.setDate(3, new java.sql.Date(matricula.getFechaInicio().getTime()));
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public int actualizarMatricula(Matricula matricula) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_MATRICULA_SQL);
            ps.setDate(1, new java.sql.Date(matricula.getFechaInicio().getTime()));
            ps.setLong(2, matricula.getIdMatricula());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

    @Override
    public int borrarMatricula(long idMatricula) {
        Connection con = null;
        int result = 0;
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_MATRICULA_SQL);
            ps.setLong(1, idMatricula);
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection(con);
        }
        return result;
    }

	@Override
	public long getIdMatricula(int idAlumno, int idCurso) {
		// TODO Auto-generated method stub
		return 0;
	}
}