package com.soosan.portfolio.repository;

import com.soosan.portfolio.domain.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
}
