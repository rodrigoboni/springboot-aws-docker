package com.hibicode.beerstore.service;

import com.hibicode.beerstore.error.exceptions.BeerAlreadyExistException;
import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class BeerServiceTest {
	
	private BeerService beerService;
	
	@Mock // anotação p/ indicar que este attr é mockado
	private Beers beersMocked;
	
	@Before
	public void setup() {
		// inicializa classes marcadas com @Mock
		MockitoAnnotations.initMocks(this);
		
		// injeta no serviço a instância mockada do repositório
		beerService  = new BeerService(beersMocked);
	}
	
	@Test(expected = BeerAlreadyExistException.class)
	public void should_deny_creation_of_beer_that_exists() {
		when(beersMocked.findByNameAndType(getTestBeer().getName(), getTestBeer().getType())).thenReturn(Optional.of(getTestBeer()));
		
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
		
		when(beersMocked.save(beer)).thenReturn(getTestBeer());
		
		final Beer beerSaved = beerService.save(beer);
		
		assertThat(beerSaved.getId(), equalTo(getTestBeer().getId()));
		assertThat(beerSaved.getName(), equalTo(getTestBeer().getName()));
		assertThat(beerSaved.getType(), equalTo(getTestBeer().getType()));
	}
	
	private Beer getTestBeer() {
		final Beer beer = new Beer();
		beer.setId(10L);
		beer.setName("Heineken");
		beer.setType(BeerType.LAGER);
		beer.setVolume(new BigDecimal(335));
		
		return beer;
	}
}
