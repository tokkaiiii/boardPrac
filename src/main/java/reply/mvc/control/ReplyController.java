package reply.mvc.control;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import mvc.domain.Reply;
import mvc.domain.Reply.ReplyBuilder;
import mvc.domain.User;
import reply.mvc.model.ReplyService;

@WebServlet("/reply.do")
public class ReplyController extends HttpServlet {

  public void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String method = req.getParameter("method");
    if (method != null) {
      switch (method) {
        case "list":
          list(req, res);
          break;
        case "insert":
          insert(req, res);
          break;
        case "reply":
          reply(req, res);
          break;
      }
    } else {
      res.sendRedirect("board/cntDetail.jsp");
    }
  }

  private void list(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String replyJason;
    ReplyService service = ReplyService.getInstance();
    String seq_ = req.getParameter("seq");
    int board_seq = -1;
    if (seq_ != null) {
      board_seq = Integer.parseInt(seq_);
    }
    ArrayList<Reply> list = service.listS(board_seq);
    //jason 배열 생성
    if (list.size() > 0) {
      StringBuilder jasonBuilder = new StringBuilder("[");
      for (Reply reply : list) {
        jasonBuilder.append("{")
            .append("\"seq\":").append(reply.getSeq()).append(",")
            .append("\"content\":\"").append(reply.getContent()).append("\",")
            .append("\"nickname\":\"").append(reply.getNickname()).append("\",")
            .append("\"date\":\"").append(reply.getUdate()).append("\",")
            .append("\"group_depth\":\"").append(reply.getGroup_depth()).append("\"")
            .append("},");
      }
      jasonBuilder.setLength(jasonBuilder.length() - 1);
      jasonBuilder.append("]");
      replyJason = jasonBuilder.toString();
    } else {
      replyJason = "[]";
    }
    res.setContentType("application/json;charset=UTF-8");
    res.setCharacterEncoding("UTF-8");
    PrintWriter out = res.getWriter();
    out.println(replyJason);
    out.flush();
  }

  private void insert(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    ReplyService service = ReplyService.getInstance();
    String seq = req.getParameter("seq");
    int board_seq = -1;
    if (seq != null) {
      board_seq = Integer.parseInt(seq);
    }
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute("user");
    String id = user.getId();
    String nickname = user.getNickname();
    String button = req.getParameter("button");
    String group_depth_ = req.getParameter("group_depth"+button);
    int group_depth = -1;
    if(group_depth_ != null) {
      group_depth = Integer.parseInt(group_depth_);
      System.out.println("뎁스 = "+group_depth);
    }
    System.out.println(button);
    if (button != null) {
      if (button.equals("insert")) {
        String commentContent = req.getParameter("commentContent");
        Reply reply = Reply.builder()
            .id(id)
            .nickname(nickname)
            .content(commentContent)
            .board_seq(board_seq).build();
        service.insertS(reply);
        res.sendRedirect(
            "/board.do?method=select&seq=" + board_seq + "&group_depth=" + group_depth);
      } else  {
        String comment = req.getParameter("replyComment"+button);
        int group_no = Integer.parseInt(button);
        System.out.println("버튼값"+group_no);
        System.out.println("comment = " + comment);
        Reply reply = Reply.builder()
            .id(id)
            .nickname(nickname)
            .content(comment)
            .board_seq(board_seq)
            .group_no(group_no)
            .group_depth(group_depth + 1).build();
        service.replyS(reply);
        res.sendRedirect(
            "/board.do?method=select&seq=" + board_seq + "&group_depth=" + group_depth);
      }
    }

  }

  private void reply(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    ReplyService service = ReplyService.getInstance();
    String seq = req.getParameter("seq");
    String replyComment = req.getParameter("replyComment");
    System.out.println("req = " + replyComment);
    String group_depth_ = req.getParameter("group_depth");
    int group_depth = Integer.parseInt(group_depth_);
    System.out.println(group_depth);
    int board_seq = -1;
    if (seq != null) {
      board_seq = Integer.parseInt(seq);
    }
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute("user");
    String id = user.getId();
    String nickname = user.getNickname();
    String commentContent = req.getParameter("commentContent");
    Reply reply = Reply.builder()
        .id(id)
        .nickname(nickname)
        .content(commentContent)
        .board_seq(board_seq)
        .group_depth(group_depth).build();
    service.replyS(reply);
    res.sendRedirect("/board.do?method=select&seq=" + board_seq);

  }
}