package com.vladmihalcea.hibernate.type.util.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Vlad Mihalcea
 */
public interface ConnectionCallable<T> {
    T execute(Connection connection) throws SQLException;
}
