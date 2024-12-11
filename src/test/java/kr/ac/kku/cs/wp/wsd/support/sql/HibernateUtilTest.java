package kr.ac.kku.cs.wp.wsd.support.sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

/**
 * HUtilTest
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
public class HibernateUtilTest {

	private static final Logger logger = LogManager.getLogger(HibernateUtilTest.class);
	
	
	@Test
	public void testGetSession() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		assertNotNull(session);
		
		session.close();
	}
}