package dao;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import util.Configuracion;

public class AcademiaDAOImplJPA implements AcademiaDAO {
    private EntityManagerFactory emf;

    public AcademiaDAOImplJPA() {
        emf = Persistence.createEntityManagerFactory(Configuracion.getJpaPersistenceUnitName());
    }

    @Override
    public Collection<Alumno> cargarAlumnos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Alumno> query = em.createQuery("SELECT a FROM Alumno a", Alumno.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Alumno getAlumno(int idAlumno) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Alumno.class, idAlumno);
        } finally {
            em.close();
        }
    }

    @Override
    public int grabarAlumno(Alumno alumno) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(alumno);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int actualizarAlumno(Alumno alumno) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(alumno);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int borrarAlumno(int idAlumno) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Alumno alumno = em.find(Alumno.class, idAlumno);
            if (alumno != null) {
                em.remove(alumno);
                tx.commit();
                return 1;
            }
            return 0;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Curso> cargarCursos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso c", Curso.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Curso getCurso(int idCurso) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Curso.class, idCurso);
        } finally {
            em.close();
        }
    }

    @Override
    public int grabarCurso(Curso curso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(curso);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int actualizarCurso(Curso curso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(curso);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int borrarCurso(int idCurso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Curso curso = em.find(Curso.class, idCurso);
            if (curso != null) {
                em.remove(curso);
                tx.commit();
                return 1;
            }
            return 0;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Matricula> cargarMatriculas() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Matricula> query = em.createQuery("SELECT m FROM Matricula m", Matricula.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long getIdMatricula(int idAlumno, int idCurso) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT m.idMatricula FROM Matricula m WHERE m.alumno.idAlumno = :idAlumno AND m.curso.idCurso = :idCurso", 
                Long.class);
            query.setParameter("idAlumno", idAlumno);
            query.setParameter("idCurso", idCurso);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return -1;
        } finally {
            em.close();
        }
    }

    @Override
    public Matricula getMatricula(long idMatricula) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Matricula.class, idMatricula);
        } finally {
            em.close();
        }
    }

    @Override
    public int grabarMatricula(Matricula matricula) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(matricula);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int actualizarMatricula(Matricula matricula) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(matricula);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int borrarMatricula(long idMatricula) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Matricula matricula = em.find(Matricula.class, idMatricula);
            if (matricula != null) {
                em.remove(matricula);
                tx.commit();
                return 1;
            }
            return 0;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }
}