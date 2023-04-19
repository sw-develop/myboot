package tobyspring.myboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MybootApplication {

    public static void main(String[] args) {
        //Spring Container & Bean 등록
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
        applicationContext.registerBean(HelloController.class); //빈 등록
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();

        //Servlet Container & Servlet 등록
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext ->
                servletContext.addServlet("dispatcherServlet",
                        new DispatcherServlet(applicationContext) //스프링 컨테이너를 사용할 수 있도록 ApplicationContext 전달
                ).addMapping("/*"));
        webServer.start();
    }
}