package com.romanmarkunas.blog.odbms.numeric;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Romans Markuns
 */
public class PlayerJPADAO implements PlayerDAO {

    private final EntityManager manager;


    public PlayerJPADAO(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public void create(Player entry) throws DBAccessException {
        try {
            this.manager.getTransaction().begin();
            this.manager.persist(entry);
            this.manager.getTransaction().commit();
        }
        catch (PersistenceException e) {
            throw accessExc("create", entry.accountId(), e);
        }
    }

    @Override
    public Player get(String accountId) throws DBAccessException {
        try {
            // TODO test how concurrent updates may screw this
            return this.manager.find(Player.class, accountId);
        }
        catch (PersistenceException e) {
            throw accessExc("find", accountId, e);
        }
    }

    @Override
    public void update(Player entry) throws DBAccessException{
        try {
            this.manager.getTransaction().begin();
            this.manager.merge(entry);
            this.manager.getTransaction().commit();
        }
        catch (PersistenceException e) {
            throw accessExc("create", entry.accountId(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Player> topTen() throws DBAccessException {
        try {
            return this.manager
                    .createQuery("SELECT p from Player p ORDER BY (100.0 * p.gamesWon) / p.gamesPlayed")
                    .setMaxResults(10)
                    .getResultList();
        }
        catch (PersistenceException e) {
            throw new DBAccessException("Cannot select top 10 players", e);
        }
    }

    @Override
    public void close() throws IOException {
        this.manager.close();
    }
}
