package ch.aemon.ejb.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by krigu on 20.12.14.
 */
@Entity
@DiscriminatorValue("BOOK")
@Table(name = "BOOKS")
public class Book extends Media {


}
