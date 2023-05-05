package com.elios.vacancy_storage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "vacancies")
public final class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "vacancy_name")
    private String vacancyName;
    @Column(length = 64000)
    private String description;
    boolean remote;
    @Column(name = "vacancy_url")
    private String vacancyUrl;
    @ElementCollection
    private List<String> specialization;
    @ElementCollection
    @Column(name = "experience_needed")
    private List<String> experienceNeeded;
    private String location;
    @JsonProperty("created_at")
    private Long createdAt;
}
