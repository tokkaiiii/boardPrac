package reply.mvc.model;

import java.util.ArrayList;
import mvc.domain.Reply;

public class ReplyService {

  ReplyDAO dao;
  private static final ReplyService instance = new ReplyService();

  private ReplyService() {
    dao = new ReplyDAO();
  }

  public static ReplyService getInstance() {
    return instance;
  }

  public ArrayList<Reply> listS(int board_seq) {
    return dao.list(board_seq);
  }

  public void insertS(Reply reply) {
    dao.insert(reply);
  }
  public void replyS(Reply reply) {
    dao.reply(reply);
  }
}