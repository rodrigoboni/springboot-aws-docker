package com.hibicode.beerstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // define p/ implementar equals & hashcode somente com as props indicadas
public class Beer {
	@Id
	@SequenceGenerator(name = "beer_seq", sequenceName = "beer_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_seq")
	@EqualsAndHashCode.Include //usar somente esta prop nos métodos equals & hashcode
	private Long id;
	
	@NotBlank(message = "beers-1")
	private String name;
	
	@NotNull(message = "beers-2")
	@Enumerated(EnumType.STRING)
	private BeerType type;
	
	@NotNull(message = "beers-3")
	@DecimalMin(value = "0", message = "beers-4")
	private BigDecimal volume;

	@JsonIgnore
	public boolean isNew() {
		return getId() == null;
	}

	@JsonIgnore
	public boolean alreadyExist() {
		return getId() != null;
	}
}
