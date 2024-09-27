package librarymanagment.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import librarymanagment.dto.Book;
import librarymanagment.dto.User;

public class Repository {

	private static Repository instance;
	private Connection connection;

	private Repository() {
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static synchronized Repository getInstance() {
		if (instance == null) {
			instance = new Repository();
		}
		return instance;
	}

	public boolean isUserExists(String email) {
		String query = "SELECT COUNT(*) FROM users WHERE email = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, email);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveUser(User user) {
		String insertUserSQL = "INSERT INTO users (email, password) VALUES (?, ?)";
		String userIdSQL = "SELECT user_id FROM users WHERE email = ?";
		String insertDetailsSQL = "INSERT INTO user_details (user_id, user_name, email, address, gender) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement userStmt = connection.prepareStatement(insertUserSQL);
				PreparedStatement userIdStmt = connection.prepareStatement(userIdSQL);
				PreparedStatement userDetailsStmt = connection.prepareStatement(insertDetailsSQL)) {

			userStmt.setString(1, user.getEmail());
			userStmt.setString(2, user.getPassword());
			if (userStmt.executeUpdate() == 0)
				return false;

			userIdStmt.setString(1, user.getEmail());
			try (ResultSet rs = userIdStmt.executeQuery()) {
				if (rs.next()) {
					int userId = rs.getInt("user_id");

					userDetailsStmt.setInt(1, userId);
					userDetailsStmt.setString(2, user.getUserName());
					userDetailsStmt.setString(3, user.getEmail());
					userDetailsStmt.setString(4, user.getAddress());
					userDetailsStmt.setString(5, user.getGender());
					return userDetailsStmt.executeUpdate() > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeUser(int userId) {
		String deleteDetailsSQL = "DELETE FROM user_details WHERE user_id = ?";
		String deleteUserSQL = "DELETE FROM users WHERE user_id = ?";

		try {
			connection.setAutoCommit(false);

			try (PreparedStatement deleteDetailsStmt = connection.prepareStatement(deleteDetailsSQL);
					PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserSQL)) {

				deleteDetailsStmt.setInt(1, userId);
				if (deleteDetailsStmt.executeUpdate() == 0) {
					connection.rollback();
					return false;
				}

				deleteUserStmt.setInt(1, userId);
				if (deleteUserStmt.executeUpdate() > 0) {
					connection.commit();
					return true;
				} else {
					connection.rollback();
					return false;
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveBook(Book book) {
		String query = "INSERT INTO books (book_id, book_name, author_name, edition, date_of_publication, stock) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, book.getBookId());
			stmt.setString(2, book.getBookName());
			stmt.setString(3, book.getAuthorName());
			stmt.setString(4, book.getEdition());
			stmt.setLong(5, book.getDateOfPublication());
			stmt.setInt(6, book.getStock());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeBook(String bookId) {
		String query = "DELETE FROM books WHERE book_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, bookId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBook(String bookId, int stock) {
		String query = "UPDATE books SET stock = ? WHERE book_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, stock);
			stmt.setString(2, bookId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> bookList = new ArrayList<>();
		String query = "SELECT * FROM books";

		try {
			Statement smt = connection.createStatement();
			ResultSet rs = smt.executeQuery(query);

			while (rs.next()) {
				String bookId = rs.getString("book_id");
				String bookName = rs.getString("book_name");
				String authorName = rs.getString("author_name");
				String edition = rs.getString("edition");
				long dateOfPublication = rs.getLong("date_of_publication");
				int stock = rs.getInt("stock");

				Book book = new Book(bookId, bookName, authorName, edition, dateOfPublication, stock);
				bookList.add(book);
			}

			rs.close();
			smt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	public ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<>();
		String sql = "SELECT * FROM user_details";

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setGender(rs.getString("gender"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public String[] isUserExist(String userId) {
		String query = "SELECT user_id, password FROM users WHERE email = ?";
		String[] userDetail = new String[2];

		try (PreparedStatement pst = connection.prepareStatement(query)) {
			pst.setString(1, userId);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				userDetail[0] = String.valueOf(rs.getInt("user_id"));
				userDetail[1] = rs.getString("password");
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userDetail;
	}

	public User getUserById(int userId) {
		String query = "SELECT user_name, email, address, gender FROM user_details WHERE user_id = ?";
		User user = null;

		try (PreparedStatement pst = connection.prepareStatement(query)) {
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserId(userId);
				user.setUserName(rs.getString("user_name"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setGender(rs.getString("gender"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public ArrayList<Book> getBooksByName(String filter) {
		ArrayList<Book> books = new ArrayList<>();

		String query = "SELECT * FROM books WHERE LOWER(book_name) LIKE LOWER(?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, "%" + filter + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setAuthorName(rs.getString("author_name"));
				book.setEdition(rs.getString("edition"));
				book.setDateOfPublication(rs.getLong("date_of_publication"));
				book.setStock(rs.getInt("stock"));
				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	public Book getBookById(String bookId) {
		String query = "SELECT * FROM books WHERE book_id = ?";
		Book book = null;
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, bookId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				book = new Book();
				book.setBookId(rs.getString("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setAuthorName(rs.getString("author_name"));
				book.setEdition(rs.getString("edition"));
				book.setDateOfPublication(rs.getLong("date_of_publication"));
				book.setStock(rs.getInt("stock"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	public boolean updateBookStock(String bookId, int newStock) {
		String query = "UPDATE books SET stock = ? WHERE book_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, newStock);
			stmt.setString(2, bookId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean recordBookTransaction(int userId, String bookId, long dateOfIssue) {
		String query = "INSERT INTO books_given (user_id, book_id, date_of_issue, date_of_return, status) VALUES (?, ?, ?, ?, 'not returned')";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			long fifteenDaysInMillis = 15L * 24 * 60 * 60 * 1000;
			long dateOfReturn = dateOfIssue + fifteenDaysInMillis;

			stmt.setInt(1, userId);
			stmt.setString(2, bookId);
			stmt.setLong(3, dateOfIssue);
			stmt.setLong(4, dateOfReturn);

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Book> getBorrowedBooks(int userId) {
		String query = "SELECT b.book_id, b.book_name, b.author_name, b.edition, b.date_of_publication, bg.date_of_issue, bg.date_of_return, bg.status "
				+ "FROM books b " + "JOIN books_given bg ON b.book_id = bg.book_id " + "WHERE bg.user_id = ? "
				+ "ORDER BY bg.status DESC, bg.date_of_issue ASC";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			List<Book> borrowedBooks = new ArrayList<>();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setAuthorName(rs.getString("author_name"));
				book.setEdition(rs.getString("edition"));
				book.setDateOfPublication(rs.getLong("date_of_publication"));
				book.setDateOfIssue(rs.getLong("date_of_issue"));
				book.setDateOfReturn(rs.getLong("date_of_return"));
				book.setStatus(rs.getString("status"));
				borrowedBooks.add(book);
			}
			return borrowedBooks;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean returnBook(int userId, String bookId) throws SQLException {
		String query = "UPDATE books_given SET date_of_return = ?, status = 'returned' "
				+ "WHERE user_id = ? AND book_id = ? AND status = 'not returned'";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, System.currentTimeMillis());
			statement.setInt(2, userId);
			statement.setString(3, bookId);
			int updated = statement.executeUpdate();
			return updated > 0;
		}
	}
	public List<Book> getIssuedBooks() {
	    List<Book> issuedBooks = new ArrayList<>();
	    String sql = "SELECT bg.transaction_id, bg.user_id, bg.book_id, bg.date_of_issue, bg.date_of_return, bg.status, " +
	                 "b.book_name, b.author_name, ud.user_name " +
	                 "FROM books_given bg " +
	                 "JOIN books b ON bg.book_id = b.book_id " +
	                 "JOIN user_details ud ON bg.user_id = ud.user_id " +  
	                 "ORDER BY CASE WHEN bg.status = 'not returned' THEN 0 ELSE 1 END, bg.date_of_return ASC"; 
	    try (
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Book book = new Book();
	            book.setTransaction_id(rs.getLong("transaction_id"));
	            book.setBookName(rs.getString("book_name"));
	            book.setAuthorName(rs.getString("author_name"));
	            book.setDateOfIssue(rs.getLong("date_of_issue"));
	            book.setDateOfReturn(rs.getLong("date_of_return"));
	            book.setStatus(rs.getString("status"));
	            book.setUserName(rs.getString("user_name"));
	            issuedBooks.add(book);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return issuedBooks;
	}


}
