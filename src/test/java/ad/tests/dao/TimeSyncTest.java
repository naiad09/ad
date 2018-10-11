package ad.tests.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.annotations.Test;

import ad.domain.dao.util.HibernateUtil;
import ad.tests.AbstractTest;

public class TimeSyncTest extends AbstractTest {
	@Test
	public void timeSyncTest() {
		System.out.println("This one checks that time in DB and time in the app are sync");

		String uniqueResult = HibernateUtil.getSession().createSQLQuery("select now()").uniqueResult().toString();
		System.out.println(uniqueResult);

		LocalDateTime dbTime = LocalDateTime.parse(uniqueResult, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
		LocalDateTime appTime = LocalDateTime.now().withNano(0);
		System.out.println("App time: " + appTime);
		assertEquals(dbTime, appTime);
	}
}
