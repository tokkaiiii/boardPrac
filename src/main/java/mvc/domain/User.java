package mvc.domain;

import java.sql.Date;

public class User {

  private int seq;
  private String id;
  private String password;
  private String name;
  private String email;
  private int available;
  private Date joinDate;
  private String nickname;
  public User() {
  }

  public User(int seq, String id, String password, String name, String email, int available, Date joinDate, String nickname) {
    this.seq = seq;
    this.id = id;
    this.password = password;
    this.name = name;
    this.email = email;
    this.available = available;
    this.joinDate = joinDate;
    this.nickname = nickname;
  }
  public int getSeq() {return seq;}

  public void setSeq(int seq) {this.seq = seq;}

  public String getId() {return id;}

  public void setId(String id) {this.id = id;}

  public String getPassword() {return password;}

  public void setPassword(String password) {this.password = password;}

  public String getName() {return name;}

  public void setName(String name) {this.name = name;}

  public String getEmail() {return email;}

  public void setEmail(String email) {this.email = email;}

  public int getAvailable() {return available;}

  public void setAvailable(int available) {this.available = available;}

  public Date getJoinDate() {return joinDate;}

  public void setJoinDate(Date joinDate) {this.joinDate = joinDate;}

  public String getNickname() {return nickname;}

  public void setNickname(String nickname) {this.nickname = nickname;}

}