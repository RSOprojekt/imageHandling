package si.fri.rso.imagehandling.services.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class PersistenceProducer {

    @PersistenceUnit(unitName = "image-handling-jpa")
    private EntityManagerFactory em;

    @Produces
    @ApplicationScoped
    public EntityManager getEntityManager(){
        return em.createEntityManager();
    }

    public void disposeEntityManager(@Disposes EntityManager entityManager){
        if (entityManager.isOpen()) entityManager.close();
    }
}
