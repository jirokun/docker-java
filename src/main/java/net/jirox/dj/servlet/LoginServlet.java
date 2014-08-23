package net.jirox.dj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html; charset=utf8");
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("template/login.mustache");
    mustache.execute(resp.getWriter(), null);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String user = req.getParameter("user");
    HttpSession session = req.getSession();
    session.setAttribute("user", user);
    // resp.sendRedirectを使用するとCookieがレスポンスヘッダに出力されない
    resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    resp.setHeader("Location", "/hello");
  }
}
