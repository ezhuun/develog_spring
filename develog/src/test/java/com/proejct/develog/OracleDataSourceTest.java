package com.proejct.develog;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * @RunWith은 JUnit 프레임 워크의 테스트 실행 방법을 확장할 때 사용하는 어노테이션이다.
 * @ContextConfiguration은 자동으로 만들어줄 애플리케이션의 컨텍스트의 설정파일 위치를 지정해준다.
 * @RunWith, @ContextConfiguration 애노테이션은 현재 테스트 코드를 실행할 때 스프링이 로딩되로록 하는 부분이다.
 */
public class OracleDataSourceTest {

	@RunWith(SpringJUnit4ClassRunner.class) // 스프링의 테스트 컨텍스트 프레임워크의 JUnit의 확장기능 지정
	@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
	public class DataSourceTest {

		// @Autowired
		@Inject
		private DataSource ds;

		@Test
		public void test() throws Exception {
			Connection con = ds.getConnection();
			System.out.println(con);
			con.close();
		}
	}
}