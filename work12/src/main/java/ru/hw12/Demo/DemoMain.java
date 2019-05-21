package ru.hw12.Demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.hw12.base.DBService;
import ru.hw12.base.dataSet.AddressDataSet;
import ru.hw12.base.dataSet.PhoneDataSet;
import ru.hw12.base.dataSet.UserDataSet;
import ru.hw12.dbService.DBServiceImpl;
import ru.hw12.listeners.AppInitializerListener;
import ru.hw12.servlets.AdminServlet;
import ru.hw12.servlets.LoginServlet;
import ru.hw12.servlets.TemplateProcessor;
import ru.hw12.utils.SessionUtil;

import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Objects;

public class DemoMain {
    private final static int PORT = 8090;
    public static void main(String[] args) throws Exception {
        final String webDir = Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                .getResource("public_html")).toExternalForm();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(webDir);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        context.addServlet(new ServletHolder(new LoginServlet(templateProcessor, "anonymous")), "/login");
        context.addServlet(AdminServlet.class, "/admin");

        // Initialize DBService
        ServletContextListener scl = new AppInitializerListener();
        context.addEventListener(scl);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();

        /*DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);




            PhoneDataSet phoneDataSet = new PhoneDataSet("1234");
            PhoneDataSet phoneDataSet1 = new PhoneDataSet("9111");

            AddressDataSet addressDataSet = new AddressDataSet();
            addressDataSet.setStreet("Five avenue");

            try(Session session = SessionUtil.getSession()) {
                Transaction tx = session.beginTransaction();
                session.save(phoneDataSet);
                session.save(phoneDataSet1);
                session.save(addressDataSet);
                tx.commit();
            }

            UserDataSet userDataSet = new UserDataSet();
            userDataSet.setName("Sergio");
            userDataSet.addAddress(addressDataSet);
            userDataSet.addPhoneNumber(phoneDataSet);
            userDataSet.addPhoneNumber(phoneDataSet1);
            dbService.save(userDataSet);

            UserDataSet sergio = dbService.readByName("Sergio");
            System.out.println("User by Name: " + sergio);

            Long user_id = sergio.getId();
            UserDataSet user = dbService.read(user_id);
            System.out.println("User by ID: " + user);

            List<UserDataSet> users = dbService.readAll();
            for(UserDataSet item : users)
                System.out.println(item);

        dbService.shutdown();
*/
    }
}
