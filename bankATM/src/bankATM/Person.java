package bankATM;

import java.sql.Date;
import java.sql.SQLException;

import database.DBClient;
import database.DBPerson;

public class Person {
	private String id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String phone;
	private String city;
	private String country;

	public Person(String id, String firstName, String lastName, Date birthDate, String phone, String city, String country) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setBirthDate(birthDate);
		setPhone(phone);
		setCity(city);
		setCountry(country);
	}

	public void addToDB() {
		DBPerson dbObj = new DBPerson();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this PErson to DB.");
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String toString() {
		return firstName + " " + lastName;
	}



}