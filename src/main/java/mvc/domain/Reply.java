package mvc.domain;

import java.sql.Date;

public class Reply {

  private final int seq;
  private final String id;
  private final String nickname;
  private final String content;
  private final Date rdate;
  private final Date udate;
  private final int valid;
  private final int board_seq;
  private final int group_no;
  private final int group_depth;

  public Reply(int seq, String id, String nickname, String content, Date rdate, Date udate,
      int valid, int board_seq, int group_no, int group_depth ) {
    this.seq = seq;
    this.id = id;
    this.nickname = nickname;
    this.content = content;
    this.rdate = rdate;
    this.udate = udate;
    this.valid = valid;
    this.board_seq = board_seq;
    this.group_no = group_no;
    this.group_depth = group_depth;

  }

  public static ReplyBuilder builder() {
    return new ReplyBuilder();
  }

  public static class ReplyBuilder {

    private int seq;
    private String id;
    private String nickname;
    private String content;
    private Date rdate;
    private Date udate;
    private int valid;
    private int board_seq;
    private int group_no;
    private int group_depth;
    public ReplyBuilder seq(int seq) {
      this.seq = seq;
      return this;
    }

    public ReplyBuilder id(String id) {
      this.id = id;
      return this;
    }

    public ReplyBuilder nickname(String nickname) {
      this.nickname = nickname;
      return this;
    }

    public ReplyBuilder content(String content) {
      this.content = content;
      return this;
    }

    public ReplyBuilder rdate(Date rdate) {
      this.rdate = rdate;
      return this;
    }

    public ReplyBuilder udate(Date udate) {
      this.udate = udate;
      return this;
    }

    public ReplyBuilder valid(int valid) {
      this.valid = valid;
      return this;
    }

    public ReplyBuilder board_seq(int board_seq) {
      this.board_seq = board_seq;
      return this;
    }
    public ReplyBuilder group_no(int group_no) {
      this.group_no = group_no;
      return this;
    }
    public ReplyBuilder group_depth(int group_depth) {
      this.group_depth = group_depth;
      return this;
    }


    public Reply build() {
      return new Reply(this.seq, this.id, this.nickname, this.content, this.rdate, this.udate,
          this.valid, this.board_seq, this.group_no, this.group_depth);
    }
  }

  @Override
  public String toString() {
    return "Reply{" +
        "seq=" + seq +
        ", id='" + id + '\'' +
        ", nickname='" + nickname + '\'' +
        ", content='" + content + '\'' +
        ", rdate=" + rdate +
        ", udate=" + udate +
        ", valid=" + valid +
        ", board_seq=" + board_seq +
        ", group_no=" + group_no +
        ", group_depth=" + group_depth +
        '}';
  }

  public int getSeq() {
    return seq;
  }

  public String getId() {
    return id;
  }

  public String getNickname() {
    return nickname;
  }

  public String getContent() {
    return content;
  }

  public Date getRdate() {
    return rdate;
  }

  public Date getUdate() {
    return udate;
  }

  public int getValid() {
    return valid;
  }

  public int getBoard_seq() {
    return board_seq;
  }

  public int getGroup_no() {
    return group_no;
  }

  public int getGroup_depth() {
    return group_depth;
  }
}