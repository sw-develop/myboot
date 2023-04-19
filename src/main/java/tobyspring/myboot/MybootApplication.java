package tobyspring.myboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MybootApplication {

    public static void main(String[] args) {
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                //Servlet Container 초기화 & Servlet 등록
                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext ->
                        servletContext.addServlet("dispatcherServlet",
                                new DispatcherServlet(this) //WebApplicationContext 전달
                        ).addMapping("/*"));
                webServer.start();
            }
        };

        applicationContext.registerBean(HelloController.class); //빈 등록
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();
    }
}