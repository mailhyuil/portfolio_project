package com.soosan.portfolio.repository;

import com.soosan.portfolio.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
