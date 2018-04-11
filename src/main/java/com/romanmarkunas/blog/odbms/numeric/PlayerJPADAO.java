package com.romanmarkunas.blog.odbms.numeric;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
            throwAccessExc("create", entry.accountId(), e);
        }
    }

    @Override
    public Player get(String accountId) throws DBAccessException {
        try {
            // TODO test how concurrent updates may screw this
            return this.manager.find(Player.class, accountId);
        }
        catch (PersistenceException e) {
            throwAccessExc("find", accountId, e);
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
            throwAccessExc("create", entry.accountId(), e);
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


    private void throwAccessExc(
            String operation,
            String id,
            Exception cause) throws DBAccessException {
        throw new DBAccessException("Cannot " + operation + " Player [" + id + "]", cause);
    }
}
