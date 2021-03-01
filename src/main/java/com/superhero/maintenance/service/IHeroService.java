package com.superhero.maintenance.service;

import com.superhero.maintenance.dto.HeroDTO;
import com.superhero.maintenance.models.entity.Hero;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

public interface IHeroService {

    List<Hero> listSuperHero();

    Hero detailSuperHero(Long id);

    Hero createdSuperHero(HeroDTO superHero);

    Hero updateSuperHero(Long id, HeroDTO superHero);

    void deleteSuperHero(Long id);

    List<Hero> findContainsNameSuperHero(String name);
}
