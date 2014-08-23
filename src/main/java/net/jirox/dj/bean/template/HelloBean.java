package net.jirox.dj.bean.template;

public class HelloBean {
  private String user;
  private String dateStr;

  public HelloBean(String user, String dateStr) {
    this.user = user;
    this.dateStr = dateStr;
  }

  public String getUser() {
    return user;
  }

  public String getDateStr() {
    return dateStr;
  }

}
