package com.hibicode.beerstore.service;

import com.hibicode.beerstore.error.exceptions.BeerAlreadyExistException;
import com.hibicode.beerstore.error.exceptions.BeerNotFoundException;
import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

    private final Beers beers;

    // recebe a instância do repositório pelo construtor
    // em runtime da aplicação a anotação @autowired funciona normal
    // em runtime de testes a instância gerá passada pelo mockito (ver classes de testes)
    BeerService(@Autowired final Beers beers) {
        this.beers = beers;
    }

    public List<Beer> findAll() {
        return beers.findAll();
    }

    public Beer save(final Beer beer) {
        verifyIfBeerExists(beer);
        return beers.save(beer);
    }
    
    public void delete(final Long id) {
        final Optional<Beer> beer = beers.findById(id);
        if(!beer.isPresent()) {
            throw new BeerNotFoundException();
        }
        
        beers.delete(beer.get());
    }

    private void verifyIfBeerExists(final Beer beer) {
        if(beer.alreadyExist() && !beers.findById(beer.getId()).isPresent()) {
            throw new BeerNotFoundException();
        }
        
        final Optional<Beer> beerByNameAndType = beers.findByNameAndType(beer.getName(), beer.getType());

        if (beerByNameAndType.isPresent() && (beer.isNew() || isUpdatingToADifferentBeer(beer, beerByNameAndType.get()))) {
            throw new BeerAlreadyExistException();
        }
    }

    private boolean isUpdatingToADifferentBeer(final Beer beer, final Beer beerByNameAndType) {
        return beer.alreadyExist() && !beerByNameAndType.equals(beer);
    }
}
