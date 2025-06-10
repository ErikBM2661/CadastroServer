package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Pessoa;

public class PessoaJpaController {

    private EntityManagerFactory emf = null;

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Pessoa findPessoa(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }
}
