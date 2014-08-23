package net.jirox.dj.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.jirox.dj.bean.template.HelloBean;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

@WebServlet(name = "HelloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    String user = (String) session.getAttribute("user");
    Context context;
    try {
      context = new InitialContext();
      DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/db");
      Timestamp timestamp;
      try (Connection con = ds.getConnection();
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT CURRENT_TIMESTAMP()");) {
        rs.next();
        timestamp = rs.getTimestamp(1);
      }
      HelloBean helloBean = new HelloBean(user, timestamp.toString());
      resp.setContentType("text/html; charset=utf8");
      MustacheFactory mf = new DefaultMustacheFactory();
      Mustache mustache = mf.compile("template/hello.mustache");
      mustache.execute(resp.getWriter(), helloBean);
    } catch (NamingException | SQLException e) {
      throw new ServletException(e);
    }
  }
}
