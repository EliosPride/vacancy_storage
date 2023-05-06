package com.elios.vacancy_storage.controller;

import com.elios.vacancy_storage.common.StorageClient;
import com.elios.vacancy_storage.dto.VacancyDto;
import com.elios.vacancy_storage.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacancy")
public class VacancyController {
    private final StorageClient storageClient;
    private final VacancyService vacancyService;


    @PostMapping
    public List<Long> saveAllVacancies() {
        return vacancyService.saveAll(storageClient.getVacancies());
    }

    @GetMapping
    public List<VacancyDto> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "50") int size,
                                   @RequestParam(required = false, defaultValue = "location") String sortColumn,
                                   @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
        return vacancyService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortColumn)));
    }

    @GetMapping("/top")
    public List<VacancyDto> findTop() {
        return vacancyService.findTopVacancies();
    }

    @GetMapping("/statistics")
    public Map<String, Integer> getStatistics() {
        return vacancyService.findStatisticsByLocation();
    }
}
