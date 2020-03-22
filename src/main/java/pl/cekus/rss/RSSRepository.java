package pl.cekus.rss;

import pl.cekus.HibernateUtil;

import java.util.List;

class RSSRepository {
    List<RSS> findAll() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        var result = session.createQuery("from RSS", RSS.class).list();

        transaction.commit();
        session.close();
        return result;
    }

    RSS addRSS(RSS newRSS) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        session.persist(newRSS);

        transaction.commit();
        session.close();
        return newRSS;
    }
}
