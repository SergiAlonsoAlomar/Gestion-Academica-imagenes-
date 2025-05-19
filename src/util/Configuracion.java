package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracion {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = Configuracion.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("No se pudo encontrar el archivo " + CONFIG_FILE);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error al cargar la configuración", ex);
        }
    }

    public static String getPersistenceType() {
        return properties.getProperty("persistence.type", "JDBC");
    }

    public static String getJdbcUrl() {
        return properties.getProperty("jdbc.url");
    }

    public static String getJdbcUser() {
        return properties.getProperty("jdbc.user");
    }

    public static String getJdbcPassword() {
        return properties.getProperty("jdbc.password");
    }

    public static String getJpaPersistenceUnitName() {
        return properties.getProperty("jpa.persistence.unit.name");
    }
}