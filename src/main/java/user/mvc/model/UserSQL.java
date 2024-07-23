package user.mvc.model;

public class UserSQL {

  public static String JOIN = "insert into user(ID, PASSWORD, NAME, EMAIL, AVAILABLE,JOIN_DATE,NICKNAME) values(?,?,?,?,?,now(),?)";

  public static String SIGNUP = "select * from user where ID=?";
}