package librarymanagment.dto;

import java.util.Date;

public class Book {
	private String bookId;
	private String bookName;
	private String authorName;
	private String edition;
	private long dateOfPublication;
	private int stock;

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

	@Override
	public String toString() {
		return "Book{" + "bookId='" + bookId + '\'' + ", bookName='" + bookName + '\'' + ", authorName='" + authorName
				+ '\'' + ", edition='" + edition + '\'' + ", dateOfPublication=" + dateOfPublication + ", stock="
				+ stock + '}';
	}
}
