package ad.tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import ad.domain.dao.util.HibernateUtil;

public class AbstractTest extends Assert {
	@BeforeTest
	public void open() {
		HibernateUtil.getSessionFactory();
	}

	@AfterTest(alwaysRun = true)
	public void shutdown() {
		HibernateUtil.shutdown();
	}
}
