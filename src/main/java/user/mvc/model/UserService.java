package user.mvc.model;

import static user.mvc.model.SignupConst.NOID;
import static user.mvc.model.SignupConst.NOPASSWORD;
import static user.mvc.model.SignupConst.SUCCESS;

import mvc.domain.User;

public class UserService {

  private UserDAO dao;

  private static final UserService instance = new UserService();

  private UserService() {dao = new UserDAO();}

  public static UserService getInstance() {return instance;}


  public int join(User user) {return dao.join(user);}

  public User getUserS(String id) {
    User user = dao.getUser(id);
    user.setPassword("");
    return user;}

  public int check(String id, String password) {
    User user = dao.getUser(id);
    if(user==null){
      return NOID;
    }else{
      String pwd = user.getPassword();
      if(!pwd.equals(password)){
        return NOPASSWORD;
      }else {
        return SUCCESS;
      }
    }
  }
}