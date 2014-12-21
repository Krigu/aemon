package ch.aemon.util;

/**
 * Created by krigu on 21.12.14.
 */

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
     * @param injectionPoint
     * @return logger
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}

