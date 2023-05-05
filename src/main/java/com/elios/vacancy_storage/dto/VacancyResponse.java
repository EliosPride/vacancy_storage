package com.elios.vacancy_storage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyResponse {
    @JsonProperty("data")
    private List<VacancyDto> vacancies;
    @JsonProperty("links")
    private VacancyLink vacancyLinks;
}
