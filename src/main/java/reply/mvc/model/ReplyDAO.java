package reply.mvc.model;

import static reply.mvc.model.ReplySQL.INSERT;
import static reply.mvc.model.ReplySQL.REPLY;
import static reply.mvc.model.ReplySQL.REPLYS;
import static reply.mvc.model.ReplySQL.SELECT;
import static reply.mvc.model.ReplySQL.getGroupDepth;

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
import mvc.domain.Reply;

class ReplyDAO {

  DataSource dataSource;

  public ReplyDAO() {
    try {
      Context context = new InitialContext();
      Context envContext = (Context) context.lookup("java:comp/env");
      dataSource = (DataSource) envContext.lookup("jdbc/myDB");
    } catch (NamingException e) {
      System.out.println("DBCP(jdbc/myDB) could not be found)" + e.getMessage());
    }
  }

  ArrayList<Reply> list(int board_seq) {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(REPLYS);
    ) {
      ArrayList<Reply> list = new ArrayList<>();
      ps.setInt(1, board_seq);
      Reply reply;
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int seq = rs.getInt(1);
          String content = rs.getString(2);
          String nickname = rs.getString(3);
          Date udate = rs.getDate(4);
          int group_depth = rs.getInt(7);
          reply = Reply.builder()
              .seq(seq)
              .nickname(nickname)
              .content(content)
              .udate(udate)
              .group_depth(group_depth)
              .board_seq(board_seq).build();
          list.add(reply);
        }
      }
      return list;
    } catch (SQLException e) {
      System.out.println("안됨 list" + e.getMessage());
      return null;
    }
  }

  void insert(Reply reply) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT)
    ) {
      System.out.println("reply = ");
      ps.setString(1, reply.getId());
      ps.setString(2, reply.getNickname());
      ps.setString(3, reply.getContent());
      ps.setInt(4, reply.getBoard_seq());
      int i = ps.executeUpdate();
      System.out.println(i + " rows affected");
    } catch (SQLException e) {
      System.out.println("안됨 insert에서" + e.getMessage());
    }
  }

  void reply(Reply reply) {
    try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(REPLY)
    ) {
      ps.setString(1, reply.getId());
      ps.setString(2, reply.getNickname());
      ps.setString(3, reply.getContent());
      ps.setInt(4, reply.getBoard_seq());
      ps.setInt(5,reply.getGroup_no());
      ps.setInt(6, reply.getGroup_depth());
      int i = ps.executeUpdate();
      System.out.println(i + " rows affected");
    } catch (SQLException e) {
      System.out.println("안됨 insert에서 안되는 것 같음" + e.getMessage());
    }
  }
}