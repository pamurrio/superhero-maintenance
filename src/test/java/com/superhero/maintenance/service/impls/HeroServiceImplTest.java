package com.superhero.maintenance.service.impls;

import com.superhero.maintenance.dao.HeroDao;
import com.superhero.maintenance.models.entity.Hero;
import com.superhero.maintenance.service.IHeroService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HeroServiceImplTest {
    @InjectMocks
    private HeroServiceImpl heroService;
    @Mock
    private HeroDao heroDao;
    @Test
    public void findAll() {
        Mockito.when(heroDao.findAll()).thenReturn(new ArrayList<>());
        List<Hero> list = heroService.listSuperHero();
        assertNotNull(list);
    }

    @Test
    public void findById() {
        Optional<Hero> returnCar = Optional.of(new Hero());
        Mockito.when(heroDao.findById(Mockito.any())).thenReturn(returnCar);
        Hero add = heroService.detailSuperHero(Mockito.any());
        assertNotNull(add);
    }
}