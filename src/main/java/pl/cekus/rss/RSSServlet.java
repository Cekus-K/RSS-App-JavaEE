package pl.cekus.rss;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rss")
public class RSSServlet extends HttpServlet {
    public static final String EMAIL_PARAM = "email";
    private ObjectMapper mapper;
    private RSSService service;

    /**
     * Servlet container needs it.
     */
    @SuppressWarnings("unused")
    public RSSServlet() {
        this(new ObjectMapper(), new RSSService());
    }

    RSSServlet(ObjectMapper mapper, RSSService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), service.parseRSS());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var newRSS = mapper.readValue(req.getInputStream(), RSS.class);
        var email = req.getParameter(EMAIL_PARAM);
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), service.addRSS(newRSS, email));
    }
}
