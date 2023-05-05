package com.elios.vacancy_storage.dao;

import com.elios.vacancy_storage.entity.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VacancyDao extends CrudRepository<Vacancy, Long> {

    List<Vacancy> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT v.location, COUNT(v.id) FROM Vacancy AS v GROUP BY v.location")
    List<Object[]> getStatisticsByLocation();

    List<Vacancy> findAll(Pageable pageable);
}
