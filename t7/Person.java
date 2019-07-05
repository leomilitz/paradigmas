public class Person {
	private String name;
	private String email;
	private String date;

	public Person(String name, String email, String date) {
		this.name = name;
		this.email = email;
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getDate() {
		return this.date;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDate(String date) {
		this.date = date;
	}

}