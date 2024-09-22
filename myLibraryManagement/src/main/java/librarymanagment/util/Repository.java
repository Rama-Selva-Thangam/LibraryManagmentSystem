package librarymanagment.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public Connection getConnection() {
		return connection;
	}

	public boolean isUserExists(String email) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean saveUser(User user) {
		PreparedStatement userStmt = null;
		PreparedStatement userDetailsStmt = null;
		ResultSet rs = null;
		try {
			String userSql = "INSERT INTO users (email, password) VALUES (?, ?)";
			userStmt = connection.prepareStatement(userSql);
			userStmt.setString(1, user.getEmail());
			userStmt.setString(2, user.getPassword());
			int affectedRows = userStmt.executeUpdate();

			if (affectedRows == 0) {
				return false;
			}
			String idSql = "SELECT user_id FROM users WHERE email = ?";
			userStmt = connection.prepareStatement(idSql);
			userStmt.setString(1, user.getEmail());
			rs = userStmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");

				String detailsSql = "INSERT INTO user_details (user_id, user_name, email, address, gender) VALUES (?, ?, ?, ?, ?)";
				userDetailsStmt = connection.prepareStatement(detailsSql);
				userDetailsStmt.setInt(1, userId);
				userDetailsStmt.setString(2, user.getUserName());
				userDetailsStmt.setString(3, user.getEmail());
				userDetailsStmt.setString(4, user.getAddress());
				userDetailsStmt.setString(5, user.getGender());
				userDetailsStmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (userStmt != null)
					userStmt.close();
				if (userDetailsStmt != null)
					userDetailsStmt.close();
				if (rs != null)
					rs.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean removeUser(String email) {
		PreparedStatement deleteDetailsStmt = null;
		PreparedStatement deleteUserStmt = null;
		try {
			connection.setAutoCommit(false);
			String deleteDetailsSQL = "DELETE FROM user_details WHERE email = ?";
			deleteDetailsStmt = connection.prepareStatement(deleteDetailsSQL);
			deleteDetailsStmt.setString(1, email);
			int detailsDeleted = deleteDetailsStmt.executeUpdate();
			if (detailsDeleted > 0) {
				String deleteUserSQL = "DELETE FROM users WHERE email = ?";
				deleteUserStmt = connection.prepareStatement(deleteUserSQL);
				deleteUserStmt.setString(1, email);
				int userDeleted = deleteUserStmt.executeUpdate();

				if (userDeleted > 0) {
					connection.commit();
					return true;
				} else {
					connection.rollback();
					return false;
				}
			} else {

				connection.rollback();
				return false;
			}

		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;

		} finally {

			try {
				if (deleteDetailsStmt != null) {
					deleteDetailsStmt.close();
				}
				if (deleteUserStmt != null) {
					deleteUserStmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	public boolean saveBook(Book book) {
		PreparedStatement preparedStatement = null;

		try {
			String insertBookSQL = "INSERT INTO books (book_id, book_name, author_name, edition, date_of_publication, stock) VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(insertBookSQL);
			preparedStatement.setString(1, book.getBookId());
			preparedStatement.setString(2, book.getBookName());
			preparedStatement.setString(3, book.getAuthorName());
			preparedStatement.setString(4, book.getEdition());
			preparedStatement.setLong(5, book.getDateOfPublication());
			preparedStatement.setInt(6, book.getStock());
			int rowsInserted = preparedStatement.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public boolean removeBook(String bookId) {
		String query = "DELETE FROM books WHERE book_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, bookId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateBook(String bookId, String stock) {
		String query = "UPDATE books SET stock = ? WHERE book_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, Integer.parseInt(stock));
			pstmt.setString(2, bookId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
