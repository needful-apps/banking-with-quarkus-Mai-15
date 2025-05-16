package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Account;
import org.acme.model.Transaction;

import java.util.List;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    public Account findByIban(String iban) {
        return find("iban", iban).firstResult();
    }

    public Account findById(String id) {
        return find("id", id).firstResult();
    }

    public Account findByIdByNativeQuery(String id) {
        return getEntityManager()
                .createQuery("SELECT acc FROM Account acc WHERE acc.id = :id", Account.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
