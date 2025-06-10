package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import model.Produto;

public class ProdutoJpaController {

    private EntityManagerFactory emf = null;

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Produto findProduto(int id) {
    return getEntityManager().find(Produto.class, id);
}

    public List<Produto> findProdutoEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        } finally {
            em.close();
        }
    }
    public void edit(Produto produto) throws Exception {
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        produto = em.merge(produto);
        em.getTransaction().commit();
    } catch (Exception ex) {
        throw new Exception("Erro ao editar produto", ex);
    } finally {
        if (em != null) {
            em.close();
        }
    }
}

}
