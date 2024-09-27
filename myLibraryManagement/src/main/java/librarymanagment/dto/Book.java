package librarymanagment.dto;


public class Book {
	private String bookId;
	private String bookName;
	private String authorName;
	private String edition;
	private long dateOfPublication;
	private int stock;
	private long transaction_id;
	private long dateOfIssue;
	private long dateOfReturn;
	private String status;
	private String userName;

	public Book() {

	}

	public Book(String bookId, String bookName, String authorName, String edition, long dateOfPublication, int stock) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.authorName = authorName;
		this.edition = edition;
		this.dateOfPublication = dateOfPublication;
		this.stock = stock;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public long getDateOfPublication() {
		return dateOfPublication;
	}

	public void setDateOfPublication(long dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}


	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public long getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(long dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public long getDateOfReturn() {
		return dateOfReturn;
	}

	public void setDateOfReturn(long dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
	    return userName;
	}

	public void setUserName(String userName) {
	    this.userName = userName;
	}
	@Override
	public String toString() {
		return "Book{" + "bookId='" + bookId + '\'' + ", bookName='" + bookName + '\'' + ", authorName='" + authorName
				+ '\'' + ", edition='" + edition + '\'' + ", dateOfPublication=" + dateOfPublication + ", stock="
				+ stock + '}';
	}



	
}
