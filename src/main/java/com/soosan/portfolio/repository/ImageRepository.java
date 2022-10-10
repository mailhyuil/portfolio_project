package com.soosan.portfolio.repository;

import com.soosan.portfolio.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    public List<Image> findByWorkId(long id);
}
