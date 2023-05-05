package com.elios.vacancy_storage.common;

import com.elios.vacancy_storage.dto.VacancyDto;

import java.util.List;

public interface StorageClient {
    List<VacancyDto> getVacancies();
}
