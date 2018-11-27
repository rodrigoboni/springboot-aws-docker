package com.hibicode.beerstore.repository;

import com.hibicode.beerstore.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author s2it_rboni
 * @version $Revision: $<br/>
 * $Id: $
 * @since 20/11/18 17:46
 */
public interface Beers extends JpaRepository<Beer, Long> {
}
