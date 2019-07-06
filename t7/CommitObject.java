
// Classe que lida com os atributos dos commits
public class CommitObject {
	private Person author; // Autor do commit
	private Person committer; // committer
	private String message; // Mensagem do commit

	public void setAuthor(Person author) {
		this.author   = author;
	}

	public void setCommitter(Person committer) {
		this.committer = committer;
	}

	public void setMessage(String message) {
		this.message  = message;
	}

	public String getMessage() {
		return this.message;
	}

	public Person getAuthor() {
		return this.author;
	}

	// Printa na linha de comando as informações do commit
	public void print() {
		System.out.println(
			"---------------------\n" + 
			"\nAuthor: \n" + 
			"\n\tName  - " + this.author.getName()  + 
			"\n\temail - " + this.author.getEmail() +
			"\n\tDate  - " + this.author.getDate()  +
			"\n\nCommiter: \n" +
			"\n\tName  - " + this.committer.getName()  +
			"\n\temail - " + this.committer.getEmail() +
			"\n\tDate  - " + this.committer.getDate() +
			"\n\nMessage: \n" + this.message
			);
	}

	// Retorna as informações do commit
	public String getInfo() {
		return 
			"Author: \n" + 
			"\n\tName  - " + this.author.getName()  + 
			"\n\temail - " + this.author.getEmail() +
			"\n\tDate  - " + this.author.getDate()  +
			"\n\nCommiter: \n" +
			"\n\tName  - " + this.committer.getName()  +
			"\n\temail - " + this.committer.getEmail() +
			"\n\tDate  - " + this.committer.getDate() +
			"\n\nMessage: \n" + this.message;
	}
}
