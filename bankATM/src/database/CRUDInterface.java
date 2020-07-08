package database;

import java.sql.SQLException;

public interface CRUDInterface<T> {
	
	public abstract void create(T t) throws SQLException;

	public abstract T retrieve(T t) throws SQLException;
	
	public abstract T retrieveById(String id) throws SQLException;
	
	public abstract void delete(T t) throws SQLException;

	public abstract void deleteById(String id) throws SQLException;

	public abstract void update(T t) throws SQLException;

	public abstract void updateById(String id) throws SQLException;

	
}
