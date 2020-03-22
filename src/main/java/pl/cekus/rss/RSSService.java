package pl.cekus.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import pl.cekus.user.User;
import pl.cekus.user.UserRepository;

import java.net.URL;

public class RSSService {

    private RSSRepository rssRepository;
    private UserRepository userRepository;

    public RSSService() {
        this(new RSSRepository(), new UserRepository());
    }

    RSSService(RSSRepository rssRepository, UserRepository userRepository) {
        this.rssRepository = rssRepository;
        this.userRepository = userRepository;
    }

    RSS addRSS(RSS newRSS, String email) {
        for (User user: userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                newRSS.setEmail(user.getId());
            }
        }

        for (RSS rss : rssRepository.findAll()) {
            if (newRSS.getHyperlink().equals(rss.getHyperlink())
                && newRSS.getEmail().equals(rss.getEmail())) {
                return null;
            }
        }
        return rssRepository.addRSS(newRSS);
    }

    public String parseRSS() {
        StringBuilder result = new StringBuilder();
        for (RSS rss : rssRepository.findAll()) {
            try {
                URL feedUrl = new URL(rss.getHyperlink());

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                for (SyndEntry entry : feed.getEntries()) {
                    result.append("<b>")
                            .append(entry.getTitle())
                            .append("</b><br>")
                            .append(entry.getDescription().getValue())
                            .append("<br>")
                            .append("<a href='")
                            .append(entry.getLink())
                            .append("' target='_blank'>")
                            .append(entry.getLink())
                            .append("'</a><br>--------------</br>");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
        return result.toString();
    }
}
