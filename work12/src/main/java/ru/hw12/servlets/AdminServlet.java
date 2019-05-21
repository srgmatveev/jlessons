package ru.hw12.servlets;

import javax.management.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.*;

public class AdminServlet extends HttpServlet
{
    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String ADMIN_CACHE_PAGE_TEMPLATE = "admin_cache.html";

    private final TemplateProcessor templateProcessor;

    @SuppressWarnings("WeakerAccess")
    public AdminServlet(TemplateProcessor templateProcessor)
    {
        this.templateProcessor = templateProcessor;
    }

    @SuppressWarnings("WeakerAccess")
    public AdminServlet() throws IOException
    {
        this(new TemplateProcessor());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String page;
        if (request.getSession().getAttribute("login") != null && request.getSession().getAttribute("password") != null) {
            Map<String, Object> cacheStats = getCacheStats();
            page = templateProcessor.getPage(ADMIN_CACHE_PAGE_TEMPLATE, cacheStats);
        } else {
            Map<String, Object> pageVariables = createPageVariablesMap(request);
            page = templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables);
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(page);
    }

    private static Map<String, Object> getCacheStats()
    {
        final Map<String, Object> result = new LinkedHashMap<>();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        try {
            final String mBeanObjectName = getCacheMBeanObjectName(mbs);
            final ObjectName objectName = new ObjectName(Objects.requireNonNull(mBeanObjectName));
            if (mbs.isRegistered(objectName)) {
                MBeanAttributeInfo[] cacheInfo = mbs.getMBeanInfo(objectName).getAttributes();
                for (final MBeanAttributeInfo mBeanAttributeInfo : cacheInfo) {
                    final String mBeanAttributeDescription = mBeanAttributeInfo.getDescription();
                    result.put(mBeanAttributeDescription, mbs.getAttribute(objectName, mBeanAttributeDescription));
                }
            }
        }
        catch (ReflectionException | InstanceNotFoundException |
                IntrospectionException | AttributeNotFoundException |
                MBeanException | MalformedObjectNameException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String getCacheMBeanObjectName(final MBeanServer mbs)
    {
        Set<ObjectName> objectNameSet = null;

        try {
            objectNameSet = mbs.queryNames(new ObjectName("javax.cache:type=CacheStatistics,*,Cache=userDataSetCache"), null);
        }
        catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }

        Iterator<ObjectName> it = Objects.requireNonNull(objectNameSet).iterator();
        String mBeanObjectName = null;
        final String cacheNameNeeded = "Cache=userDataSetCache";
        while (it.hasNext()) {
            String objectNameFromSet = it.next().toString();
            if (objectNameFromSet.endsWith(cacheNameNeeded)) {
                mBeanObjectName = objectNameFromSet;
            }
        }
        return mBeanObjectName;
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request)
    {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());

        //let's get login from session
        String login = (String) request.getSession().getAttribute("login");
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);

        return pageVariables;
    }
}
