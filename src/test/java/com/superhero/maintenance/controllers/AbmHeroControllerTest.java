package com.superhero.maintenance.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superhero.maintenance.dto.HeroDTO;
import com.superhero.maintenance.exceptions.HeroNotFoundException;
import com.superhero.maintenance.models.entity.Hero;
import com.superhero.maintenance.service.IHeroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AbmHeroController.class)
class AbmHeroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IHeroService iHeroService;

    @Captor
    private ArgumentCaptor<HeroDTO> heroRequestArgumentCaptor;

    @Test
    public void postingANewHeroShouldCreateANewHero() throws Exception {
        HeroDTO heroDto = HeroDTO.builder()
                .name("Duke")
                .editorial(null).build();
        Hero hero = Hero.builder().name("Duke").editorial(null).build();
        when(iHeroService.createdSuperHero(heroRequestArgumentCaptor.capture())).thenReturn(hero);
        this.mockMvc
                .perform(post("/api/abm/superheros/created")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroDto)))
                .andExpect(status().isOk());

        assertThat(heroRequestArgumentCaptor.getValue().getName(), is("Duke"));
        assertThat(heroRequestArgumentCaptor.getValue().getEditorial(), nullValue());
    }

    @Test
    public void allHeroesEndpointShouldReturnTwoHeroes() throws Exception {

        when(iHeroService.listSuperHero()).thenReturn(List.of(
                createHero(1L, "Superman", null),
                createHero(2L, "Spiderman", null)));

        this.mockMvc
                .perform(get("/api/abm/superheros/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Superman")))
                .andExpect(jsonPath("$[0].editorial", nullValue()))
                .andExpect(jsonPath("$[0].id", is(1)));

    }

    @Test
    public void findHeroesByNameEndpointShouldReturnContainsNameHeroes() throws Exception {

        when(iHeroService.findContainsNameSuperHero("man")).thenReturn(List.of(
                createHero(1L, "Superman", null),
                createHero(2L, "Spiderman", null)));

        this.mockMvc
                .perform(get("/api/abm/superheros/findByName/man"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Superman")))
                .andExpect(jsonPath("$[0].editorial", nullValue()))
                .andExpect(jsonPath("$[0].id", is(1)));

    }

    @Test
    public void getHeroWithIdOneShouldReturnAHero() throws Exception {

        when(iHeroService.detailSuperHero(1L)).thenReturn(createHero(1L, "Batman", null));

        this.mockMvc
                .perform(get("/api/abm/superheros/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("Batman")))
                .andExpect(jsonPath("$.editorial", nullValue()))
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    public void getHeroWithUnknownIdShouldReturn404() throws Exception {

        when(iHeroService.detailSuperHero(1L)).thenThrow(new HeroNotFoundException("Hero with id '1' not found"));

        this.mockMvc
                .perform(get("/api/abm/superheros/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateHeroWithKnownIdShouldUpdateTheHero() throws Exception {

        HeroDTO heroDto = new HeroDTO();
        heroDto.setName("Capitan America");
        heroDto.setEditorial(null);

        when(iHeroService.updateSuperHero(eq(1L), heroRequestArgumentCaptor.capture()))
                .thenReturn(createHero(1L, "Capitan America", null));

        this.mockMvc
                .perform(put("/api/abm/superheros/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("Capitan America")))
                .andExpect(jsonPath("$.editorial", nullValue()))
                .andExpect(jsonPath("$.id", is(1)));

        assertThat(heroRequestArgumentCaptor.getValue().getName(), is("Capitan America"));
        assertThat(heroRequestArgumentCaptor.getValue().getEditorial(), nullValue());

    }

    @Test
    public void updateHeroWithUnknownIdShouldReturn404() throws Exception {

        HeroDTO heroDto = new HeroDTO();
        heroDto.setName("Capitan America");
        heroDto.setEditorial(null);

        when(iHeroService.updateSuperHero(eq(42L), heroRequestArgumentCaptor.capture()))
                .thenThrow(new HeroNotFoundException("The hero with id '42' was not found"));

        this.mockMvc
                .perform(put("/api/abm/superheros/update/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroDto)))
                .andExpect(status().isNotFound());

    }

    private Hero createHero(Long id, String name, String editorial) {
        Hero hero = new Hero();
        hero.setId(id);
        hero.setName(name);
        hero.setEditorial(editorial);
        return hero;
    }
}