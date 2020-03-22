package pl.cekus.sendgrid;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import pl.cekus.rss.RSSService;
import java.io.IOException;

class SendGridService {
    private RSSService service;

    SendGridService() {
        this(new RSSService());
    }

    SendGridService(RSSService service) {
        this.service = service;
    }

    void sendMail(String recipient) {

        Email from = new Email("azure_af4f7f25fa95beaf7e82a6199ea5145c@azure.com");
        String subject = "Kamil Cekus: Message from RSS App sending by SendGrid";
        Email to = new Email(recipient);
        Content content = new Content("text/html", service.parseRSS());
        Mail mail = new Mail(from, subject, to, content);

        // space for sendgrid api key
        SendGrid sg = new SendGrid("...");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}