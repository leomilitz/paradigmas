import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;

public class Person {
	private String name;
	private String email;
	private String date;
	private Date dateValue;

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getDate() {
		return this.date;
	}

	public Date getDateValue() {
		return this.dateValue;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		try {
			this.dateValue = sdf.parse(date);
		}
		catch(ParseException e) {}
		
		this.date = date;
	}

}