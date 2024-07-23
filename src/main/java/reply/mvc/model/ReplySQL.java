package reply.mvc.model;

class ReplySQL {

  final static String SELECT = "select * from REPLY where BOARD_SEQ=? and VALID = 1";
  final static String INSERT = "insert into REPLY(ID,NICKNAME,CONTENT,RDATE,UDATE,VALID,BOARD_SEQ,GROUP_DEPTH) values(?,?,?,now(),now(),1,?,1)";
  final static String REPLY = "insert into REPLY(ID,NICKNAME,CONTENT,RDATE,UDATE,VALID,BOARD_SEQ,GROUP_NO,GROUP_DEPTH) values(?,?,?,now(),now(),1,?,?,?)";
  final static String getGroupDepth = "select GROUP_DEPTH from REPLY where BOARD_ = ?";
  final static String REPLYS ="with recursive CTS as (select SEQ\n"
      + "                            , CONTENT\n"
      + "                            , NICKNAME\n"
      + "                            , UDATE\n"
      + "                            , BOARD_SEQ\n"
      + "                            , GROUP_NO\n"
      + "                            , GROUP_DEPTH\n"
      + "                            , CAST(SEQ as char(100)) lvl\n"
      + "                       from REPLY\n"
      + "                       where GROUP_NO is null and BOARD_SEQ=?\n"
      + "                       UNION ALL\n"
      + "                       select b.seq\n"
      + "                            , b.CONTENT\n"
      + "                            , b.NICKNAME\n"
      + "                            , b.UDATE\n"
      + "                            , b.BOARD_SEQ\n"
      + "                            , b.GROUP_NO\n"
      + "                            , b.GROUP_DEPTH\n"
      + "                            , CONCAT(c.lvl, ',', b.seq) lvl\n"
      + "                       from REPLY b\n"
      + "                                INNER JOIN CTS c\n"
      + "                                           on b.GROUP_NO = c.seq)\n"
      + "select SEQ\n"
      + "     , CONCAT(REPEAT('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;', GROUP_DEPTH), '★', CONTENT) as CONTENT\n"
      + "     , NICKNAME\n"
      + "     , UDATE\n"
      + "     , BOARD_SEQ\n"
      + "     , GROUP_NO\n"
      + "     , GROUP_DEPTH\n"
      + "     , lvl\n"
      + "from CTS\n"
      + "order by lvl";

}