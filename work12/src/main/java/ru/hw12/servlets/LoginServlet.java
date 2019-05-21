package ru.hw12.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet
{
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String DEFAULT_ADMIN_PASSWORD = "111";
    private static final String PASSWORD_PARAMETER_NAME = "password";

    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final String LOGGED_IN_PAGE_TEMPLATE = "logged_in.html";

    private final TemplateProcessor templateProcessor;
    private String login;

    public LoginServlet(TemplateProcessor templateProcessor, String login)
    {
        this.login = login;
        this.templateProcessor = templateProcessor;
    }

    private String getPage(final String login, final String pageTemplate) throws IOException
    {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return templateProcessor.getPage(pageTemplate, pageVariables);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPassword = request.getParameter(PASSWORD_PARAMETER_NAME);
        String page;

        if (requestLogin != null && requestPassword.equals(DEFAULT_ADMIN_PASSWORD)) {
            saveToVariable(requestLogin);
            saveToSession(request, requestLogin, requestPassword); //request.getSession().getAttribute("login");
            saveToServlet(request, requestLogin); //request.getAttribute("login");
            saveToCookie(response, requestLogin); //request.getCookies();

            page = getPage(login, LOGGED_IN_PAGE_TEMPLATE);
            response.getWriter().println(page);
        } else {
            page = getPage(login, LOGIN_PAGE_TEMPLATE);
            response.getWriter().println(page);
        }
        setOK(response);
    }

    private void saveToCookie(HttpServletResponse response, String requestLogin)
    {
        response.addCookie(new Cookie("L12.1-login", requestLogin));
    }

    private void saveToServlet(HttpServletRequest request, String requestLogin)
    {
        request.getServletContext().setAttribute("login", requestLogin);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin, String requestPassword)
    {
        request.getSession().setAttribute("login", requestLogin);
        request.getSession().setAttribute("password", requestPassword);
    }

    private void saveToVariable(String requestLogin)
    {
        login = requestLogin != null ? requestLogin : login;
    }

    private void setOK(HttpServletResponse response)
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
