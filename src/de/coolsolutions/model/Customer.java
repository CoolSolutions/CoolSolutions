package de.coolsolutions.model;

public class Customer
{
	private String gender;
	private String firstname;
	private String lastname;
	private String street;
	private String streetnumber;
	private String zipcode;
	private String city;
	
	public Customer(String gender, String firstname, String lastname, String street, String streetnumber, String zipcode, String city)
	{
		this.gender = gender;
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.streetnumber = streetnumber;
		this.zipcode = zipcode;
		this.city = city;
	}

	public String getGender()
	{
		return gender;
	}

	public String getFirstname()
	{
		return firstname;
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

	public String getZipcode()
	{
		return zipcode;
	}

	public String getCity()
	{
		return city;
	}
	
	public int getCityId(){
		int cityId = 0;
		
		
		
		return cityId;
		
	}
	
	
}
