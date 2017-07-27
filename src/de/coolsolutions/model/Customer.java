package de.coolsolutions.model;

public class Customer
{
	private String gender;
	private String surname;
	private String lastname;
	private String street;
	private String streetnumber;
	private Integer cityId;
	private String email;
	
	public Customer(String gender, String surname, String lastname, String street, String streetnumber, Integer cityId, String email)
	{
		this.gender = gender;
		this.surname = surname;
		this.lastname = lastname;
		this.street = street;
		this.streetnumber = streetnumber;
		this.cityId = cityId;
		this.email = email;
	}

	public String getGender()
	{
		return gender;
	}

	public String getSurname()
	{
		return surname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public String getStreet()
	{
		return street;
	}

	public String getStreetnumber()
	{
		return streetnumber;
	}

	public Integer getCityId()
	{
		return cityId;
	}
	
	public String getEmail()
	{
		return email;
	}
	
}
