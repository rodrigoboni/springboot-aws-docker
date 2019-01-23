package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BeerServiceTest {
	
	private BeerService beerService;
	
	@Before
	public void setup() {
		beerService  = new BeerService();
	}
	
	@Test(expected = BeerAlreadyExistException.class)
	public void should_deny_creation_of_beer_that_exists() {
		final Beer beer = new Beer();
		beer.setName("Heineken");
		beer.setType(BeerType.LAGER);
		beer.setVolume(new BigDecimal(335));
		
		beerService.save(beer);
	}
	
	@Test
	public void should_create_new_beer() {
		final Beer beer = new Beer();
		beer.setName("Heineken");
		beer.setType(BeerType.LAGER);
		beer.setVolume(new BigDecimal(335));
		
		beerService.save(beer);
	}
}
