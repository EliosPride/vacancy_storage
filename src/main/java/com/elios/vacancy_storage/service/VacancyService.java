package com.elios.vacancy_storage.service;

import com.elios.vacancy_storage.dto.VacancyDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface VacancyService {
    List<VacancyDto> getAll(Pageable page);

    List<Long> saveAll(List<VacancyDto> vacancyDtoList);

    List<VacancyDto> findTopVacancies();

    Map<String, Integer> findStatisticsByLocation();

}
