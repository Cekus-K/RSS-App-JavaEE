package pl.cekus.sendgrid;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/send")
public class SendGridServlet extends HttpServlet {
    private static final String EMAIL_PARAM = "email";
    private SendGridService service;

    /**
     * Servlet container needs it.
     */
    @SuppressWarnings("unused")
    public SendGridServlet(){
        this(new SendGridService());
    }

    public SendGridServlet(SendGridService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter(EMAIL_PARAM);
        resp.getWriter().write(service.sendMail(email));
    }
}
