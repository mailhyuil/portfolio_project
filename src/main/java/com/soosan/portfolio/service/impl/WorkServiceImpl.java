package com.soosan.portfolio.service.impl;

import com.soosan.portfolio.domain.Work;
import com.soosan.portfolio.repository.WorkRepository;
import com.soosan.portfolio.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    private WorkRepository workRepository;

    @Override
    public List<Work> getWorks() {
        return workRepository.findAll();
    }
}
