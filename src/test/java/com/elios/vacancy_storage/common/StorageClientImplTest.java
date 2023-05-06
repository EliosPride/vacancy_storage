package com.elios.vacancy_storage.common;

import com.elios.vacancy_storage.dto.VacancyDto;
import com.elios.vacancy_storage.dto.VacancyLink;
import com.elios.vacancy_storage.dto.VacancyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StorageClientImplTest {

    @InjectMocks
    private StorageClientImpl storageClient;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGetVacancies() {
        when(restTemplate.getForObject(anyString(), eq(VacancyResponse.class)))
                .thenReturn(getVacancyResponseWithLink())
                .thenReturn(getVacancyResponseWithEmptyLink());

        List<VacancyDto> result = storageClient.getVacancies();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        result.forEach(dto -> assertEquals("Kyiv", dto.getLocation()));
    }

    private VacancyResponse getVacancyResponseWithEmptyLink() {
        VacancyResponse vacancyResponse = new VacancyResponse();
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setLocation("Kyiv");
        List<VacancyDto> vacancies = List.of(vacancyDto);
        vacancyResponse.setVacancies(vacancies);
        return vacancyResponse;
    }

    private VacancyResponse getVacancyResponseWithLink() {
        VacancyResponse vacancyResponse = getVacancyResponseWithEmptyLink();
        VacancyLink vacancyLink = new VacancyLink();
        vacancyLink.setNext("link");
        vacancyResponse.setVacancyLinks(vacancyLink);
        return vacancyResponse;
    }

}