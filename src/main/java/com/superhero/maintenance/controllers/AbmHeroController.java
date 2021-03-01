package com.superhero.maintenance.controllers;


import com.superhero.maintenance.dto.HeroDTO;
import com.superhero.maintenance.service.IHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/abm/superheros")
public class AbmHeroController {

	@Autowired
	private IHeroService iHeroService;
	
	@GetMapping("/list")
	public ResponseEntity<?> listSuperHero(){
		return ResponseEntity.ok(iHeroService.listSuperHero());
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name){
		return ResponseEntity.ok(iHeroService.findContainsNameSuperHero(name));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detailSuperHero(@PathVariable Long id){
		return ResponseEntity.ok(iHeroService.detailSuperHero(id));
	}

	@PostMapping("/created")
	public ResponseEntity<?> createdSuperHero(@Valid @RequestBody HeroDTO superHero){
		return ResponseEntity.ok(iHeroService.createdSuperHero(superHero));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSuperHero(@PathVariable Long id, @Valid @RequestBody HeroDTO superHero){
		return ResponseEntity.ok(iHeroService.updateSuperHero(id, superHero));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteSuperHero(@PathVariable Long id){
		iHeroService.deleteSuperHero(id);
		return ResponseEntity.ok().build();
	}
}

