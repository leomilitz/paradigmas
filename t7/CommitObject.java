public class CommitObject {
	private Person author;
	private Person committer;
	private String message;

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

	public String getInfo() {
		return 
			"\nAuthor: \n" + 
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
