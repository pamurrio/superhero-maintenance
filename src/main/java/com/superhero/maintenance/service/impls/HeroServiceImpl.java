package com.superhero.maintenance.service.impls;

import com.superhero.maintenance.dao.HeroDao;
import com.superhero.maintenance.dto.HeroDTO;
import com.superhero.maintenance.exceptions.HeroNotFoundException;
import com.superhero.maintenance.models.entity.Hero;
import com.superhero.maintenance.service.IHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HeroServiceImpl implements IHeroService {
    @Autowired
    private HeroDao heroDao;
    @Override
    public List<Hero> listSuperHero() {
        return heroDao.findAll();
    }

    @Override
    public Hero detailSuperHero(Long id) {
        Optional<Hero> requestedHero = heroDao.findById(id);

        if (requestedHero.isEmpty()) {
            throw new HeroNotFoundException(String.format("Hero with id: '%s' not found", id));
        }

        return requestedHero.get();
    }

    @Override
    public Hero createdSuperHero(HeroDTO superHero) {
        Hero hero = Hero
                    .builder()
                    .name(superHero.getName())
                    .editorial(superHero.getEditorial())
                    .build();
        hero = heroDao.save(hero);
        return hero;
    }

    @Override
    @Transactional
    public Hero updateSuperHero(Long id, HeroDTO superHero) {
        Optional<Hero> heroFromDatabase = heroDao.findById(id);

        if (heroFromDatabase.isEmpty()) {
            throw new HeroNotFoundException(String.format("Hero with id: '%s' not found", id));
        }

        Hero heroToUpdate = heroFromDatabase.get();
        heroToUpdate.setName(superHero.getName());
        heroToUpdate.setEditorial(superHero.getEditorial());
        return heroToUpdate;
    }

    @Override
    public void deleteSuperHero(Long id) {
        heroDao.deleteById(id);
    }

    @Override
    public List<Hero> findContainsNameSuperHero(String name) {
        return heroDao.findByNameContainingIgnoreCase(name);
    }
}
