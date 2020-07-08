package manager;

import java.sql.Date;
import java.sql.SQLException;

public interface Settings {

	public abstract void setCurrentDate(Date date) throws SQLException;

}