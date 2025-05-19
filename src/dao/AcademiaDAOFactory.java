package dao;

import util.Configuracion;

public class AcademiaDAOFactory {
    public static AcademiaDAO getAcademiaDAO() {
        String persistenceType = Configuracion.getPersistenceType();
        
        if ("JPA".equalsIgnoreCase(persistenceType)) {
            return new AcademiaDAOImplJPA();
        } else {
            return new AcademiaDAOImplJDBC();
        }
    }
}