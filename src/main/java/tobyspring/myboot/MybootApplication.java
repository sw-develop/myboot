package tobyspring.myboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MybootApplication {

    public static void main(String[] args) {
        //Spring Container & Bean 등록
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean(HelloController.class); //빈 등록
        applicationContext.refresh();

        //Servlet Container & Servlet 등록
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> servletContext.addServlet("FrontController", new HttpServlet() {
            @Override
            protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) { //매핑
                    String name = req.getParameter("name");

                    HelloController helloController = applicationContext.getBean(HelloController.class);
                    String ret = helloController.hello(name); //바인딩

                    resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                    resp.getWriter().println(ret);
                } else {
                    resp.setStatus(HttpStatus.NOT_FOUND.value());
                }
            }
        }).addMapping("/*"));
        webServer.start();
    }
}