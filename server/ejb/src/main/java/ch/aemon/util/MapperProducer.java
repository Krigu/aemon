package ch.aemon.util;

import ch.aemon.ejb.dto.AuthorDTO;
import ch.aemon.ejb.entity.Author;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;


@Singleton
public class MapperProducer {

    private MapperFactory mapperFactory;

    @PostConstruct
    public void init() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Author.class, AuthorDTO.class).byDefault();
    }

    @Produces
    public MapperFactory produceMapperFactory() {
        return mapperFactory;
    }

    @Produces
    public MapperFacade produceMapperFacade(){
        return mapperFactory.getMapperFacade();
    }
}
