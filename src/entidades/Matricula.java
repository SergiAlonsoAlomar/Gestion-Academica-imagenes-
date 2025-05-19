package entidades;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "matriculas")
public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private long idMatricula;
    
    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;
    
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    public Matricula() {}

    public Matricula(long idMatricula, int idAlumno, int idCurso, Date fechaInicio) {
        this.idMatricula = idMatricula;
        this.alumno = new Alumno();
        this.alumno.setIdAlumno(idAlumno);
        this.curso = new Curso();
        this.curso.setIdCurso(idCurso);
        this.fechaInicio = fechaInicio;
    }

    public long getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(long idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdAlumno() {
        return alumno != null ? alumno.getIdAlumno() : 0;
    }

    public void setIdAlumno(int idAlumno) {
        if (this.alumno == null) {
            this.alumno = new Alumno();
        }
        this.alumno.setIdAlumno(idAlumno);
    }

    public int getIdCurso() {
        return curso != null ? curso.getIdCurso() : 0;
    }

    public void setIdCurso(int idCurso) {
        if (this.curso == null) {
            this.curso = new Curso();
        }
        this.curso.setIdCurso(idCurso);
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        return idMatricula + " - " + getIdAlumno() + " - " + getIdCurso() + " - " + fechaInicio;
    }
}