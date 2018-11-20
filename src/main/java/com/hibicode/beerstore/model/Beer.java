package com.hibicode.beerstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author s2it_rboni
 * @version $Revision: $<br/>
 * $Id: $
 * @since 20/11/18 17:37
 */

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Beer {
  @Id
  @SequenceGenerator(name = "beer_seq", sequenceName = "beer_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_seq")
  @EqualsAndHashCode.Include
  private Long id;
  
  private String name;
  
  @Enumerated(EnumType.STRING)
  private BeerType type;
  
  private BigDecimal volume;
}
