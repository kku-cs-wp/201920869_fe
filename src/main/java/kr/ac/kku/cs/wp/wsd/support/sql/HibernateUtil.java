package kr.ac.kku.cs.wp.wsd.support.sql;

import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import jakarta.persistence.Entity;

/**
 * HibernateUtil
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
public class HibernateUtil {
	
	private static final Logger logger = LogManager.getLogger(HibernateUtil.class);
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
        	Configuration configuration =  new Configuration().configure();
        	
        	String packageName = configuration.getProperty("entity.package.scan");
        	
        	logger.debug("entity.package.scan : {}", packageName);
        	
        	if (packageName != null && !packageName.isBlank()) {
        		Set<Class<?>> entityClasses = findEntityClasses(packageName);
        		
        		if (entityClasses != null) {
	                for (Class<?> entityClass : entityClasses) {
	                	
	                	logger.debug(entityClass.getName());
	                    configuration.addAnnotatedClass(entityClass);
	                }
        		}
        	}
        	
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
    
    private static Set<Class<?>> findEntityClasses(String packageName) {
    	Set<Class<?>> rtn = null;

        Reflections reflections = new Reflections(packageName );
        
        try {
        	rtn = reflections.getTypesAnnotatedWith(Entity.class).stream()
            .filter(cls -> cls.isAnnotationPresent(Entity.class))
            .collect(Collectors.toSet());
		} catch (ReflectionsException e) {
			logger.warn("No Entity Class : {} ", e.getMessage());
		}
        
        return rtn;
    }

}