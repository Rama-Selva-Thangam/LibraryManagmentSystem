package librarymanagment.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	public boolean updateBook(String bookId, String stock) {
		String query = "UPDATE books SET stock = ? WHERE book_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, Integer.parseInt(stock));
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

}
