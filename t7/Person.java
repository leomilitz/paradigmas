import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;
	
// Classe que lida com os itens do commit que são usuários.
public class Person {
	private String name; // Nome do usuário
	private String email; // email do usuário
	private String date; // data do commit realizado pelo usuário (string)
	private Date dateValue; // data do commit realizado pelo usuário (objeto data)

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

	// Cria o objeto data a partir de uma string de data.
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