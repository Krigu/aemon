package ch.aemon.util;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 *
 */
@Stateless
public class ResourceProducer {

    @PersistenceContext(unitName="EJBTest")
    private EntityManager entityManager;

    @Produces
    public EntityManager entityManager(){
        return entityManager;
    }

    /**
     * @param injectionPoint metadata about the injection point.
     * @return logger
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}

