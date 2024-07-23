package board.mvc.model;

public class BoardSQL {
  public static String TOTALLIST = "select * from POST where VALID=1 order by SEQ desc";
  public static String TOTAL = "select ceil(count(SEQ)/10) from POST";
  public static String SELECT = "select SEQ,NICKNAME,TITLE,CONTENT,DATE from POST where VALID = 1 and SEQ=?";
  public static String SELECTFILE = "select * from FILE where POST_SEQ=?";
  public static String INSERT = "insert into POST(ID,NICKNAME,TITLE,CONTENT,FNAME,OFNAME,DATE,VALID,GROUP_DEPTH) values(?,?,?,?,?,?,now(),1,0)";
  public static String UPDATE = "update POST set TITLE = ?,CONTENT = ?,DATE = now() where SEQ = ?";
  public static String DELETE = "update POST set VALID = 0 where SEQ = ?";
  public static String FILEINSERT = "insert into FILE(FNAME,OFNAME,RDATE,UDATE,POST_SEQ) values(?,?,now(),now(),?)";
  public static String PAGE = "select * from POST where SEQ < ? and VALID = 1";
  //public static String DATE = "select now()";
  public static String NICKNAME = "select NICKNAME from USER where ID=?";
  public static String SEQ = "select SEQ from POST order by SEQ desc";
  //전체게시글
  public static String SELECTCNT = "select * from POST where SEQ=? and VALID = 1";
  //search
  public static String SEARCHTILE = "select * from POST where TITLE like ?";
  //view
  public static String VIEW = "insert into POST_VIEW(POST_SEQ) values(?)";

  public static String VIEWCOUNT = "select COUNT(SEQ) from POST_VIEW where POST_SEQ=?";
}