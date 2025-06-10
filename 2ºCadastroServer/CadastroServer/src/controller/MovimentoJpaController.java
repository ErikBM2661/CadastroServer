package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Movimento;

public class MovimentoJpaController {

    private EntityManagerFactory emf = null;

    public MovimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movimento movimento) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(movimento);
        em.getTransaction().commit();
    } catch (Exception ex) {
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        ex.printStackTrace(); // opcional: log de erro
    } finally {
        if (em != null) {
            em.close();
        }
    }
}

    
}
