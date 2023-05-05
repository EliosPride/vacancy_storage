package com.elios.vacancy_storage.common;

import com.elios.vacancy_storage.dto.VacancyDto;
import com.elios.vacancy_storage.dto.VacancyLink;
import com.elios.vacancy_storage.dto.VacancyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageClientImpl implements StorageClient {

    private final RestTemplate restTemplate;

    @Override
    public List<VacancyDto> getVacancies() {
        List<VacancyDto> vacancyDtoList = new ArrayList<>();
        String url = "https://www.arbeitnow.com/api/job-board-api";
        do {
            VacancyResponse vacancyResponse = restTemplate.getForObject(url, VacancyResponse.class);
            Optional.ofNullable(vacancyResponse)
                    .map(VacancyResponse::getVacancies)
                    .ifPresent(vacancyDtoList::addAll);
            url = Optional.ofNullable(vacancyResponse)
                    .map(VacancyResponse::getVacancyLinks)
                    .map(VacancyLink::getNext)
                    .orElse(null);
        } while (StringUtils.hasLength(url));
        return vacancyDtoList;
    }
}
