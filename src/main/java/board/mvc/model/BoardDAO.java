package board.mvc.model;

import static board.mvc.model.BoardSQL.DELETE;
import static board.mvc.model.BoardSQL.INSERT;

import static board.mvc.model.BoardSQL.NICKNAME;
import static board.mvc.model.BoardSQL.SEARCHTILE;
import static board.mvc.model.BoardSQL.SELECT;
import static board.mvc.model.BoardSQL.SELECTCNT;
import static board.mvc.model.BoardSQL.TOTAL;
import static board.mvc.model.BoardSQL.TOTALLIST;
import static board.mvc.model.BoardSQL.UPDATE;
import static board.mvc.model.BoardSQL.VIEW;
import static board.mvc.model.BoardSQL.VIEWCOUNT;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import mvc.domain.Board;

class BoardDAO {

  DataSource dataSource;

  BoardDAO() {
    try {
      Context context = new InitialContext();
      Context envContext = (Context) context.lookup("java:comp/env");
      dataSource = (DataSource) envContext.lookup("jdbc/myDB");
    } catch (NamingException e) {
      System.out.println("DBCP(jdbc/myDB) could not be found)" + e.getMessage());
    }
  }

  // BoardDAO.java

  ArrayList<Board> list() {
    ArrayList<Board> list = new ArrayList<>();
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(TOTALLIST)
    ) {
      while (rs.next()) {
        int seq = rs.getInt("seq");
        String id = rs.getString("id");
        String nickname = rs.getString("nickname");
        String title = rs.getString("title");
        String content = rs.getString("content");
        String fname = rs.getString("fname");
        String ofname = rs.getString("ofname");
        Date date = rs.getDate("date");
        int valid = rs.getInt("valid");
        int group_depth = rs.getInt("group_depth");
        Board board = new Board(seq, id, nickname, title, content, fname, ofname, -1
            ,date, valid,group_depth);
        list.add(board);
      }
      return list;
    } catch (SQLException e) {
      System.out.println("안됨 list" + e.getMessage());
      return null;
    }
  }

  ArrayList<Board> searchTitle(String title) {
    ArrayList<Board> list = new ArrayList<>();
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(SEARCHTILE)
    ) {
      ps.setString(1, "%"+title+"%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int seq = rs.getInt("seq");
          String id = rs.getString("id");
          String nickname = rs.getString("nickname");
          String dbtitle = rs.getString("title");
          String content = rs.getString("content");
          String fname = rs.getString("fname");
          String ofname = rs.getString("ofname");
          Date date = rs.getDate("date");
          int valid = rs.getInt("valid");
          Board board = new Board(seq, id, nickname, dbtitle, content, fname, ofname, -1,date, valid,-1);
          list.add(board);
        }
      }
      return list;
    } catch (SQLException e) {
      System.out.println("안됨 search" + e.getMessage());
      return null;
    }
  }

  int insert(Board board) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT)
    ) {
      ps.setString(1, board.getId());
      ps.setString(2, board.getNickname());
      ps.setString(3, board.getTitle());
      ps.setString(4, board.getContent());
      ps.setString(5, board.getFname());
      ps.setString(6, null);
      return ps.executeUpdate();
    } catch (SQLException se) {
      System.out.println("안됨 insert" + se.getMessage());
      return -1;
    }
  }

  int update(Board board) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(UPDATE)
    ) {
      ps.setString(1, board.getTitle());
      ps.setString(2, board.getContent());
      ps.setInt(3, board.getSeq());
      return ps.executeUpdate();
    } catch (SQLException se) {
      System.out.println("안됨" + se.getMessage());
      return -1;
    }
  }

  int select(Board board) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT)
    ) {
      ps.setInt(1, board.getSeq());
      return ps.executeUpdate();
    } catch (SQLException se) {
      return -1;
    }
  }

  int delete(Board board) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(DELETE)
    ) {
      ps.setInt(1, board.getSeq());
      return ps.executeUpdate();
    } catch (SQLException se) {
      return -1;
    }
  }

  String nickname(String id) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(NICKNAME)
    ) {
      ps.setString(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getString("nickname");
        } else {
          return null;
        }
      }
    } catch (SQLException se) {
      System.out.println("안됨" + se.getMessage());
      return null;
    }
  }

  Board selectCnt(int seq) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECTCNT)
    ) {
      ps.setInt(1, seq);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String nickname = rs.getString(3);
          String title = rs.getString(4);
          String content = rs.getString(5);
          String fname = rs.getString(6);
          Date date = rs.getDate(9);
          Board board = new Board(seq, null, nickname, title, content, fname, null,-1, date, -1,0);
          return board;
        }
      }
    } catch (SQLException se) {
    }
    return null;
  }

  int total() {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(TOTAL)
    ) {
      if (rs.next()) {
        return rs.getInt(1);
      }
    } catch (SQLException se) {
    }
    return 0;
  }
  int view(int seq){
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps1 = con.prepareStatement(VIEW);
        PreparedStatement ps2 = con.prepareStatement(VIEWCOUNT)
        ){
      int count = 0;
      ps1.setInt(1, seq);
      ps2.setInt(1, seq);
      ps1.executeUpdate();
      try (ResultSet rs = ps2.executeQuery()) {
        if (rs.next()) {
           count = rs.getInt(1);
        }
        return count;

      }

    }catch (SQLException se) {
      return 0;
    }
  }
  int viewcount(int seq){
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(VIEWCOUNT)
    ){
      int count = 0;
      ps.setInt(1, seq);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          count = rs.getInt(1);
        }
        return count;

      }

    }catch (SQLException se) {
      return 0;
    }
  }
}