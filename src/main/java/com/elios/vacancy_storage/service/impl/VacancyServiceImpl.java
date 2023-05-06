package com.elios.vacancy_storage.service.impl;

import com.elios.vacancy_storage.dao.VacancyDao;
import com.elios.vacancy_storage.dto.VacancyDto;
import com.elios.vacancy_storage.entity.Vacancy;
import com.elios.vacancy_storage.service.VacancyService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private static final Type VACANCY_LIST = new TypeReference<List<Vacancy>>() {
    }.getType();
    private static final Type VACANCY_DTO_LIST = new TypeReference<List<VacancyDto>>() {
    }.getType();

    private final ModelMapper modelMapper;
    private final VacancyDao vacancyDao;

    @Override
    public List<VacancyDto> getAll(Pageable page) {
        return modelMapper.map(vacancyDao.findAll(page), VACANCY_DTO_LIST);
    }

    @Override
    @Transactional
    public List<Long> saveAll(List<VacancyDto> vacancyDtoList) {
        if (CollectionUtils.isEmpty(vacancyDtoList)) {
            return List.of();
        }
        List<Vacancy> entities = modelMapper.map(vacancyDtoList, VACANCY_LIST);
        return StreamSupport.stream(vacancyDao.saveAll(entities).spliterator(), false)
                .map(Vacancy::getId)
                .toList();
    }

    @Override
    public List<VacancyDto> findTopVacancies() {
        List<Vacancy> result = vacancyDao.findTop10ByOrderByCreatedAtDesc();
        return modelMapper.map(result, VACANCY_DTO_LIST);
    }

    @Override
    public Map<String, Integer> findStatisticsByLocation() {
        return vacancyDao.getStatisticsByLocation()
                .stream()
                .collect(Collectors.toMap(s -> String.valueOf(s[0]), s -> Integer.valueOf(s[1].toString())));
    }
}
