package com.elios.vacancy_storage.dao;

import com.elios.vacancy_storage.entity.Vacancy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class VacancyDaoTest {

    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 2, Sort.by(Sort.Direction.fromString("ASC"), "createdAt"));

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
    public void testFindTop10ByOrderByCreatedAtDesc() {
        List<Vacancy> result = vacancyDao.findTop10ByOrderByCreatedAtDesc();

        assertFalse(result.isEmpty());
        assertEquals(10, result.size());

        assertEquals(11, result.get(0).getCreatedAt());
        assertEquals(10, result.get(1).getCreatedAt());
        assertEquals(9, result.get(2).getCreatedAt());
        assertEquals(8, result.get(3).getCreatedAt());
        assertEquals(7, result.get(4).getCreatedAt());
        assertEquals(6, result.get(5).getCreatedAt());
        assertEquals(5, result.get(6).getCreatedAt());
        assertEquals(4, result.get(7).getCreatedAt());
        assertEquals(3, result.get(8).getCreatedAt());
        assertEquals(2, result.get(9).getCreatedAt());
    }

    @Test
    public void testGetStatisticsByLocation() {
        List<Object[]> result = vacancyDao.getStatisticsByLocation();

        assertFalse(result.isEmpty());
        assertEquals(6, result.size());

        assertEquals("Chernobyl", result.get(0)[0]);
        assertEquals("Dnipro", result.get(1)[0]);
        assertEquals("Kherson", result.get(2)[0]);
        assertEquals("Kyiv", result.get(3)[0]);
        assertEquals("Rivne", result.get(4)[0]);
        assertEquals("Vinnitsia", result.get(5)[0]);
        assertEquals(4L, result.get(0)[1]);
        assertEquals(1L, result.get(1)[1]);
        assertEquals(1L, result.get(2)[1]);
        assertEquals(3L, result.get(3)[1]);
        assertEquals(1L, result.get(4)[1]);
        assertEquals(1L, result.get(5)[1]);
    }

    @Test
    public void findAll() {
        List<Vacancy> result = vacancyDao.findAll(PAGE_REQUEST);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        assertEquals("Kyiv", result.get(0).getLocation());
        assertEquals("Vinnitsia", result.get(1).getLocation());
    }

    private Vacancy createVacancy(String location, Long createdAt) {
        Vacancy vacancy = new Vacancy();
        vacancy.setCreatedAt(createdAt);
        vacancy.setLocation(location);
        return vacancy;
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
}