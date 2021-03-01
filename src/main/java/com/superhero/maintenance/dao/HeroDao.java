package com.superhero.maintenance.dao;

import com.superhero.maintenance.models.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HeroDao extends JpaRepository<Hero, Long> {

    List<Hero> findByNameContainingIgnoreCase(String name);
}
