package librarymanagment.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import librarymanagment.dto.Book;
import librarymanagment.dto.User;

public class Repository {
	private static Repository repository;
	private static Connection connection;

	private Repository() {
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static synchronized Repository getInstance() {
		if (repository == null) {
			repository = new Repository();
		}
		return repository;
	}

	public boolean isUserExists(String email) {
		String query = "SELECT COUNT(*) FROM users WHERE email = ?";
		return executeCountQuery(query, email) > 0;
	}

	public String[] isUserExist(String email) {
		String query = "SELECT user_id, password FROM users WHERE email = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String[] userDetails = new String[2];
				userDetails[0] = String.valueOf(rs.getInt("user_id"));
				userDetails[1] = rs.getString("password");
				return userDetails;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean saveUser(User user) {
		String insertUserSQL = "INSERT INTO users (email, password) VALUES (?, ?)";
		String insertDetailsSQL = "INSERT INTO user_details (user_id, user_name, email, address, gender) VALUES (?, ?, ?, ?, ?)";

		try {
			connection.setAutoCommit(false);
			int userId = insertUser(user, insertUserSQL);
			if (userId == -1)
				return false;
			return insertUserDetails(user, userId, insertDetailsSQL) > 0;
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
			return false;
		} finally {
			commit();
		}
	}

	private int insertUser(User user, String sql) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			if (stmt.executeUpdate() > 0) {
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next())
						return rs.getInt(1);
				}
			}
		}
		return -1;
	}

	private int insertUserDetails(User user, int userId, String sql) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setString(2, user.getUserName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getAddress());
			stmt.setString(5, user.getGender());
			return stmt.executeUpdate();
		}
	}

	public boolean removeUser(int userId) {
		String deleteDetailsSQL = "DELETE FROM user_details WHERE user_id = ?";
		String deleteUserSQL = "DELETE FROM users WHERE user_id = ?";

		try {
			connection.setAutoCommit(false);
			if (executeUpdate(deleteDetailsSQL, userId) == 0)
				return false;
			if (executeUpdate(deleteUserSQL, userId) > 0) {
				connection.commit();
				return true;
			}
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		}
		return false;
	}

	public List<User> getAllUsers() {
		String sql = "SELECT * FROM user_details";
		return executeUserQuery(sql);
	}

	public int getBorrowedBooksCount(int userId) {
		String query = "SELECT COUNT(*) FROM books_given WHERE user_id = ? AND status = 'not returned'";
		return executeCountQuery(query, userId);
	}

	public User getUserById(int userId) {
		String query = "SELECT * FROM user_details WHERE user_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setGender(rs.getString("gender"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Book> getBorrowedBooks(int userId) {
		List<Book> borrowedBooks = new ArrayList<>();
		String query = "SELECT b.book_id, b.book_name, b.author_name, b.edition, "
				+ "b.date_of_publication, bg.transaction_id, bg.date_of_issue, " + "bg.date_of_return, bg.status "
				+ "FROM books_given bg " + "JOIN books b ON bg.book_id = b.book_id " + "WHERE bg.user_id = ? "
				+ "ORDER BY CASE WHEN bg.status = 'not returned' THEN 0 ELSE 1 END";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setAuthorName(rs.getString("author_name"));
				book.setEdition(rs.getString("edition"));
				book.setDateOfPublication(rs.getLong("date_of_publication"));
				book.setTransaction_id(rs.getLong("transaction_id"));
				book.setDateOfIssue(rs.getLong("date_of_issue"));
				book.setDateOfReturn(rs.getLong("date_of_return"));
				book.setStatus(rs.getString("status"));
				borrowedBooks.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowedBooks;
	}

	private List<User> executeUserQuery(String sql) {
		List<User> userList = new ArrayList<>();
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

	public boolean saveBook(Book book) {
		String query = "INSERT INTO books (book_id, book_name, author_name, edition, date_of_publication, stock) VALUES (?, ?, ?, ?, ?, ?)";
		return executeUpdate(query, book.getBookId(), book.getBookName(), book.getAuthorName(), book.getEdition(),
				book.getDateOfPublication(), book.getStock()) > 0;
	}

	public boolean removeBook(String bookId) {
		return executeUpdate("DELETE FROM books WHERE book_id = ?", bookId) > 0;
	}

	public boolean updateBookStock(String bookId, int newStock) {
		return executeUpdate("UPDATE books SET stock = ? WHERE book_id = ?", newStock, bookId) > 0;
	}

	public List<Book> getAllBooks() {
		String query = "SELECT * FROM books";
		return executeBookQuery(query);
	}

	private List<Book> executeBookQuery(String sql) {
		List<Book> bookList = new ArrayList<>();
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				bookList.add(mapBook(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookList;
	}

	private Book mapBook(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setBookId(rs.getString("book_id"));
		book.setBookName(rs.getString("book_name"));
		book.setAuthorName(rs.getString("author_name"));
		book.setEdition(rs.getString("edition"));
		book.setDateOfPublication(rs.getLong("date_of_publication"));
		book.setStock(rs.getInt("stock"));
		return book;
	}

	public Book getBookById(String bookId) {
		return executeSingleBookQuery("SELECT * FROM books WHERE book_id = ?", bookId);
	}

	private Book executeSingleBookQuery(String sql, String param) {
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, param);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return mapBook(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Book> getBooksByName(String filter) {
		String query = "SELECT * FROM books WHERE LOWER(book_name) LIKE LOWER(?)";
		return executeBookQueryWithParam(query, "%" + filter + "%");
	}

	private List<Book> executeBookQueryWithParam(String sql, String param) {
		List<Book> books = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, param);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				books.add(mapBook(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	public boolean recordBookTransaction(int userId, String bookId, long dateOfIssue) {
		String query = "INSERT INTO books_given (user_id, book_id, date_of_issue, date_of_return, status) VALUES (?, ?, ?, ?, 'not returned')";
		long fifteenDaysInMillis = 15L * 24 * 60 * 60 * 1000;
		return executeUpdate(query, userId, bookId, dateOfIssue, dateOfIssue + fifteenDaysInMillis) > 0;
	}

	public boolean returnBook(int userId, String bookId) {
		String query = "UPDATE books_given SET date_of_return = ?, status = 'returned' WHERE user_id = ? AND book_id = ? AND status = 'not returned'";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, System.currentTimeMillis());
			statement.setInt(2, userId);
			statement.setString(3, bookId);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Book> getIssuedBooks() {
		List<Book> issuedBooks = new ArrayList<>();
		String sql = "SELECT bg.transaction_id, bg.user_id, bg.book_id, bg.date_of_issue, bg.date_of_return, bg.status, b.book_name, b.author_name, ud.user_name "
				+ "FROM books_given bg JOIN books b ON bg.book_id = b.book_id JOIN user_details ud ON bg.user_id = ud.user_id "
				+ "ORDER BY CASE WHEN bg.status = 'not returned' THEN 0 ELSE 1 END, bg.date_of_return ASC";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

	private int executeUpdate(String sql, Object... params) {
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			int row = stmt.executeUpdate();
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private int executeCountQuery(String sql, Object... params) {
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void commit() {
		try {
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
