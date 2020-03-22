package pl.cekus.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.net.URL;

public class RSSService {

    private RSSRepository repository;

    public RSSService() {
        this(new RSSRepository());
    }

    RSSService(RSSRepository repository) {
        this.repository = repository;
    }

    RSS addRSS(RSS newRSS) {
        for (RSS rss : repository.findAll()) {
            if (newRSS.getHyperlink().equals(rss.getHyperlink())) {
                return null;
            }
        }
        return repository.addRSS(newRSS);
    }

    public String parseRSS() {
        StringBuilder result = new StringBuilder();
        for (RSS rss : repository.findAll()) {
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
