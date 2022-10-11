package com.soosan.portfolio.repository;

import com.soosan.portfolio.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    public List<Work> findAllByCategory(String category);
}
