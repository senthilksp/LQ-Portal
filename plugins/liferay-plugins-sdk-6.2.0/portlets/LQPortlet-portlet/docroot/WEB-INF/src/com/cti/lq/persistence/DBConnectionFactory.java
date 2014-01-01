package com.cti.lq.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.util.InfrastructureUtil;

public abstract class DBConnectionFactory {

	private static final Log LOG = LogFactory.getLog(DBConnectionFactory.class);

	public static Connection getLiferayDBConnection() {

		Connection connection = null;
		DataSource dataSource = InfrastructureUtil.getDataSource();
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public static Connection getPostgresDBConnection() {
		Connection connection = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context
					.lookup("java:comp/env/jdbc/LQDatabase");
			connection = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

}
