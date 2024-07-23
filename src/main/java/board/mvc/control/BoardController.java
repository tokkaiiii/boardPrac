package board.mvc.control;

import static board.mvc.fileset.Path.FILE_STORE;

import board.mvc.model.BoardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import mvc.domain.Board;
import mvc.domain.User;

@WebServlet("/board.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 10, // 10MB
    maxFileSize = 1024 * 1024 * 20, // 20MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class BoardController extends HttpServlet {

  @Override
  public void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String method = req.getParameter("method");
    if (method != null) {
      switch (method) {
        case "boards": boards(req, res);break;

        case "title": title(req,res);break;

        case "write": write(req, res);break;

        case "insert": insert(req, res);break;

        case "select": select(req, res);break;

        case "update": update(req, res);break;

        case "download": download(req,res);break;
      }
    } else {
     // boards(req, res);
      res.sendRedirect("board/board.jsp");
    }
  }

  private void boards(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String boardJson;
    BoardService service = BoardService.getInstance();
    ArrayList<Board> list = service.listS();

    // JSON 배열 생성
    if (list.size() > 0) {
      StringBuilder jsonBuilder = new StringBuilder("[");
      for (Board board : list) {
        jsonBuilder.append("{")
            .append("\"seq\":").append(board.getSeq()).append(",")
            .append("\"title\":\"").append(board.getTitle()).append("\",")
            .append("\"nickname\":\"").append(board.getNickname()).append("\",")
            .append("\"date\":\"").append(board.getDate()).append("\",")
            .append("\"group_depth\":\"").append(board.getGroup_depth()).append("\",")
            .append("\"boardview\":\"").append(service.viewconutS(board.getSeq())).append("\"")
            .append("},");
      }
      jsonBuilder.setLength(jsonBuilder.length() - 1); // 마지막 ',' 제거
      jsonBuilder.append("]");
      boardJson = jsonBuilder.toString();
    } else {
      boardJson = "[]"; // 비어있는 배열
    }
    // 응답 설정
    res.setContentType("application/json; charset=utf-8"); // 수정: ':' -> ';'
    res.setCharacterEncoding("UTF-8");
    PrintWriter out = res.getWriter();
    out.print(boardJson);
    out.flush(); // 응답 버퍼를 플러시
  }

  private void title(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    String searchField = req.getParameter("searchField");
    System.out.println("searchField: " + searchField);
    if (searchField != null) {
      if (!searchField.equals("")) {
        String boardJson;
        BoardService service = BoardService.getInstance();
        ArrayList<Board> list = service.searchTitle(searchField);
        // JSON 배열 생성
        if (list.size() > 0) {
          StringBuilder jsonBuilder = new StringBuilder("[");
          for (Board board : list) {
            jsonBuilder.append("{")
                .append("\"seq\":").append(board.getSeq()).append(",")
                .append("\"title\":\"").append(board.getTitle()).append("\",")
                .append("\"nickname\":\"").append(board.getNickname()).append("\",")
                .append("\"date\":\"").append(board.getDate()).append("\",")
                .append("\"boardview\":\"").append(service.viewconutS(board.getSeq())).append("\"")
                .append("},");
          }
          jsonBuilder.setLength(jsonBuilder.length() - 1); // 마지막 ',' 제거
          jsonBuilder.append("]");
          boardJson = jsonBuilder.toString();
        } else {
          boardJson = "[]"; // 비어있는 배열
        }
        // 응답 설정
        res.setContentType("application/json; charset=utf-8"); // 수정: ':' -> ';'
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.print(boardJson);
        out.flush(); // 응답 버퍼를 플러시
      }else {
        res.sendRedirect("/board.do?method=boards");
      }
    }else {
      res.sendRedirect("/board.do?method=boards");
    }

  }

  private void write(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute("user");
    res.sendRedirect("board/insert.jsp");

  }

  private void insert(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    BoardService service = BoardService.getInstance();
    String title = req.getParameter("title");
    String content = req.getParameter("content");
    Part filePart = req.getPart("file");
    String fileName = null;
    if(filePart != null) {
      if (!filePart.getSubmittedFileName().isEmpty()) {
        fileName = filePart.getSubmittedFileName();
        service.saveFile(filePart);
      }
    }
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute("user");
    String id = user.getId();
    Board board = new Board(-1, id,service.nicknameS(id), title, content, fileName, null,-1,
        null, 1,-1);
    service.insertS(board);
    res.sendRedirect("/board.do");
  }

  private void select(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String seq_ = req.getParameter("seq");
    String group_depth_ = req.getParameter("group_depth");
    BoardService service = BoardService.getInstance();
    Board board = null;
    int view =0;
    if (seq_ != null ) {
      if (!seq_.isEmpty()) {
        int group_depth = Integer.parseInt(group_depth_);
        int seq = Integer.parseInt(seq_);
        view = service.view(seq);
        board = service.selectCntS(seq);
      }
    }
    req.setAttribute("view", view);
    req.setAttribute("board", board);
    req.getRequestDispatcher("board/cntDetail.jsp").forward(req, res);
  }
  private void update(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String button = req.getParameter("button");
    BoardService service = BoardService.getInstance();
    String seq_ = req.getParameter("seq");
    String title = req.getParameter("title");
    String content = req.getParameter("content");
    Board board = null;
    if (seq_ != null) {
      if (!seq_.isEmpty()) {
        int seq = Integer.parseInt(seq_);
        board = new Board(seq,null,null,title,content, null, null,-1,null, -1,-1);
      }
    }
    if(button.equals("delete")) {
      service.deleteS(board);
      res.sendRedirect("/board.do");
    }else {
      service.updateS(board);
      res.sendRedirect("/board.do");
    }
  }
  private void download(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    String fname = req.getParameter("fname");
    File file = new File(FILE_STORE,fname);
    BoardService service = BoardService.getInstance();
    service.download(res,req,file);
  }
}