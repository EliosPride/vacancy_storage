package com.elios.vacancy_storage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyDto {
    @JsonProperty("slug")
    String slug;
    @JsonProperty("company_name")
    String companyName;
    @JsonProperty("title")
    String vacancyName;
    @JsonProperty("description")
    String description;
    @JsonProperty("remote")
    boolean remote;
    @JsonProperty("url")
    String vacancyUrl;
    @JsonProperty("tags")
    List<String> specialization;
    @JsonProperty("job_types")
    List<String> experienceNeeded;
    @JsonProperty("location")
    String location;
    @JsonProperty("created_at")
    Long createdAt;
}
