package com.elios.vacancy_storage.service.impl;

import com.elios.vacancy_storage.dao.VacancyDao;
import com.elios.vacancy_storage.dto.VacancyDto;
import com.elios.vacancy_storage.entity.Vacancy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VacancyServiceImplTest {

    private static final String KYIV = "Kyiv";
    private static final String VINNITSA = "Vinnitsa";
    private static final Long KYIV_VACANCY_ID = 1L;
    private static final Long VINNITSA_VACANCY_ID = 2L;
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 2, Sort.by(Sort.Direction.fromString("ASC"), "location"));

    @Mock
    private VacancyDao vacancyDao;
    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<List<Vacancy>> vacancyCaptor;

    @InjectMocks
    private VacancyServiceImpl vacancyService;

    @Test
    public void testGetAll() {
        List<Vacancy> output = getVacancyList();
        when(vacancyDao.findAll(PAGE_REQUEST)).thenReturn(output);

        List<VacancyDto> result = vacancyService.getAll(PAGE_REQUEST);

        assertVacancyDto(result);
        verify(vacancyDao).findAll(PAGE_REQUEST);
    }

    @Test
    public void testReturnEmptyList() {
        when(vacancyDao.findAll(PAGE_REQUEST)).thenReturn(List.of());

        List<VacancyDto> result = vacancyService.getAll(PAGE_REQUEST);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testSaveAll() {
        List<Vacancy> output = getVacancyList();
        when(vacancyDao.saveAll(anyList())).thenReturn(output);
        List<VacancyDto> input = List.of(createVacancyDTO(KYIV), createVacancyDTO(VINNITSA));

        List<Long> result = vacancyService.saveAll(input);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(KYIV_VACANCY_ID, result.get(0));
        assertEquals(VINNITSA_VACANCY_ID, result.get(1));
        verify(vacancyDao).saveAll(vacancyCaptor.capture());
        List<Vacancy> value = vacancyCaptor.getValue();
        assertFalse(value.isEmpty());
        assertEquals(2, value.size());
        assertEquals(KYIV, value.get(0).getLocation());
        assertEquals(VINNITSA, value.get(1).getLocation());
    }

    @Test
    public void whenInputIsNullThenReturnEmptyList() {
        List<Long> result = vacancyService.saveAll(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindTopVacancies() {
        List<Vacancy> output = getVacancyList();
        when(vacancyDao.findTop10ByOrderByCreatedAtDesc()).thenReturn(output);

        List<VacancyDto> topVacancies = vacancyService.findTopVacancies();

        assertVacancyDto(topVacancies);
    }

    @Test
    public void testFindStatisticsByLocation() {
        when(vacancyDao.getStatisticsByLocation()).thenReturn(List.of(new Object[]{KYIV, 24}, new Object[]{VINNITSA, 4}));

        Map<String, Integer> result = vacancyService.findStatisticsByLocation();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.containsKey(KYIV));
        assertEquals(24, result.get(KYIV));
        assertTrue(result.containsKey(VINNITSA));
        assertEquals(4, result.get(VINNITSA));
    }

    private VacancyDto createVacancyDTO(String location) {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setLocation(location);
        return vacancyDto;
    }

    private Vacancy createVacancy(String location, Long id) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(id);
        vacancy.setLocation(location);
        return vacancy;
    }

    private List<Vacancy> getVacancyList() {
        return List.of(createVacancy(KYIV, KYIV_VACANCY_ID), createVacancy(VINNITSA, VINNITSA_VACANCY_ID));
    }

    private void assertVacancyDto(List<VacancyDto> topVacancies) {
        assertFalse(topVacancies.isEmpty());
        assertEquals(2, topVacancies.size());
        assertEquals(KYIV, topVacancies.get(0).getLocation());
        assertEquals(VINNITSA, topVacancies.get(1).getLocation());
    }
}