package net.jirox.dj.bean.template;

public class ContextXmlBean {
  private String username;
  private String password;
  private String url;

  public ContextXmlBean(String username, String password, String url) {
    super();
    this.username = username;
    this.password = password;
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getUrl() {
    return url;
  }

}
