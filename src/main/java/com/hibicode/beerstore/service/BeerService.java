package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class BeerService {
	
	private final Beers beers;
	
	// recebe a instância do repositório pelo construtor
	// em runtime da aplicação a anotação @autowired funciona normal
	// em runtime de testes a instância gerá passada pelo mockito (ver classes de testes)
	BeerService(@Autowired final Beers beers) {
		this.beers = beers;
	}
	
	Beer save(final Beer beer) {
		final Optional<Beer> beerByNameAndType = beers.findByNameAndType(beer.getName(), beer.getType());
		
		if(beerByNameAndType.isPresent()) {
			throw new BeerAlreadyExistException();
		}
		
		return beers.save(beer);
	}
}
