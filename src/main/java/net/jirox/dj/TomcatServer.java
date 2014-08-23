package net.jirox.dj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import net.jirox.dj.bean.template.ContextXmlBean;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class TomcatServer {
  private static String WEBAPP = "src/main/webapp/";

  public static void main(String[] args) throws Exception {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.enableNaming();
    Context context = tomcat.addWebapp("/", new File(WEBAPP).getAbsolutePath());
    context.setConfigFile(generateConfigFile().toURI().toURL());
    tomcat.start();
    tomcat.getServer().await();
  }

  public static File generateConfigFile() throws IOException {
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("template/context.mustache");
    String username = System.getenv("SAMPLE_DB_USERNAME");
    String password = System.getenv("SAMPLE_DB_PASSWORD");
    String url = System.getenv("SAMPLE_DB_URL");

    ContextXmlBean contextXmlBean = new ContextXmlBean(username, password, url);
    File contextFile = File.createTempFile("context-", ".xml");
    mustache.execute(new PrintWriter(new FileOutputStream(contextFile)),
        contextXmlBean).close();
    return contextFile;
  }
}