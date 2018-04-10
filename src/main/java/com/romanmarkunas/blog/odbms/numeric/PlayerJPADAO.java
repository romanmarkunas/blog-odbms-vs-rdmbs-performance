package com.romanmarkunas.blog.odbms.numeric;

import javax.persistence.EntityManager;
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
    public void create(Player entry) {
        this.manager.getTransaction().begin();
        this.manager.persist(entry);
        this.manager.getTransaction().commit();
    }

    @Override
    public Player get(String accountId) {
        // TODO test how concurrent updates may screw this
        return this.manager.find(Player.class, accountId);
    }

    @Override
    public void update(Player entry) {
        create(entry);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Player> topTen() {
        return this.manager
                .createQuery("SELECT p from Player p ORDER BY p.gamesWon / p.gamesPlayed")
                .setMaxResults(10)
                .getResultList();
    }
}
