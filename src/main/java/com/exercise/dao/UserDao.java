package com.exercise.dao;

import com.exercise.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;
    private final JdbcContext jdbcContext;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource); // 구체적인 클래스 이름이 들어가는 구간

    }

    // JdbcContext로 분리
//    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            conn = dataSource.getConnection();
//            pstmt = stmt.makePreparedStatement(conn);
//            pstmt.executeUpdate();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {  // error 발생해도 실행
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//    }

    // JdbcContext 로 넘김
//    public void executeSQL(String sql){
//        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
//                return conn.prepareStatement(sql);
//            }
//        });
//    }

    public void add(final User user) {
        AddStrategy addStrategy = new AddStrategy(user);
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt =
                        conn.prepareStatement("INSERT INTO users(id, name, password) values (?, ?, ?)");
                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());
                return pstmt;
            }
        });

//        Connection conn = connectionMaker.makeConnection();
//        PreparedStatement pstmt =
//                conn.prepareStatement("INSERT INTO users(id, name, password) values (?, ?, ?)");
        /*
         위에는 전략 패턴 적용 전, 아래는 전략 패턴 적용 후 +) try/catch/finally 묶어서 빼줌
         */

//        PreparedStatement pstmt = new AddStrategy().makePreparedStatement(conn);
//        pstmt.setString(1, user.getId());
//        pstmt.setString(2, user.getName());
//        pstmt.setString(3, user.getPassword());
//
//        pstmt.executeUpdate();
//
//        pstmt.close();
//        conn.close();
    }

    public User selectById(String id) throws SQLException, ClassNotFoundException {
        Connection conn = dataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM users where id = ?"
        );

        pstmt.setString(1, id);

        ResultSet rs = pstmt.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User(rs.getString("id"),
                    rs.getString("name"), rs.getString("password"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() throws SQLException {

//        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
//                return conn.prepareStatement("DELETE FROM users");
//            }
//        });
//        ---->
        this.jdbcContext.executeSQL("delete from users");

//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        try {
//            conn = connectionMaker.makeConnection();
//            // pstmt = conn.prepareStatement("DELETE FROM users");
//            pstmt = new DeleteAllStrategy().makePreparedStatement(conn);
//            pstmt.executeUpdate();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }finally {  // error 발생해도 실행
//            if(pstmt != null){
//                try{
//                    pstmt.close();
//                }catch (SQLException e){
//                }
//            }
//            if(conn != null){
//                try{
//                    conn.close();
//                }catch (SQLException e){
//                }
//            }
//        }
    }

    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement("select count(*) from users");
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                }catch (SQLException e){
                }
            }
            if(conn != null){
                try{
                    conn.close();
                }catch (SQLException e){
                }
            }
        }

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }

}
