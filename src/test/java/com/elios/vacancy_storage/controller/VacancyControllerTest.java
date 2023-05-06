package com.elios.vacancy_storage.controller;

import com.elios.vacancy_storage.dao.VacancyDao;
import com.elios.vacancy_storage.dto.VacancyDto;
import com.elios.vacancy_storage.entity.Vacancy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VacancyControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VacancyDao vacancyDao;


    @BeforeEach
    public void init() {
        prepareData();
    }

    @AfterEach
    public void cleanDb() {
        vacancyDao.deleteAll();
    }

    @Test
    public void testFindTop() {
        VacancyDto[] result = this.restTemplate.getForObject("http://localhost:" + port + "/vacancy/top", VacancyDto[].class);

        Assertions.assertEquals(10, result.length);
    }

    @Test
    public void testGetStatistics() {
        Map<String, Integer> result = this.restTemplate.getForObject("http://localhost:" + port + "/vacancy/statistics", Map.class);

        Assertions.assertEquals(6, result.size());
    }

    @Test
    public void testGetAll() {
        VacancyDto[] result = this.restTemplate.getForObject("http://localhost:" + port + "/vacancy", VacancyDto[].class);

        Assertions.assertEquals(11, result.length);
    }

    private void prepareData() {
        List<Vacancy> input = List.of(
                createVacancy("Kyiv", 1L),
                createVacancy("Vinnitsia", 2L),
                createVacancy("Kyiv", 3L),
                createVacancy("Kyiv", 5L),
                createVacancy("Dnipro", 4L),
                createVacancy("Chernobyl", 6L),
                createVacancy("Chernobyl", 7L),
                createVacancy("Rivne", 8L),
                createVacancy("Chernobyl", 10L),
                createVacancy("Kherson", 9L),
                createVacancy("Chernobyl", 11L)
        );
        vacancyDao.saveAll(input);
    }

    private Vacancy createVacancy(String location, Long createdAt) {
        Vacancy vacancy = new Vacancy();
        vacancy.setCreatedAt(createdAt);
        vacancy.setLocation(location);
        return vacancy;
    }
}