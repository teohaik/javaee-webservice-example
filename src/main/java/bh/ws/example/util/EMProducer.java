package bh.ws.example.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EMProducer {

    @Produces
    @PersistenceContext(unitName="trainingPU")
    private EntityManager em;

 
}
