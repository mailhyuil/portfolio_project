package com.soosan.portfolio.service;

import com.soosan.portfolio.domain.Client;
import com.soosan.portfolio.domain.Collaborator;
import com.soosan.portfolio.domain.Designer;
import com.soosan.portfolio.domain.Work;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface WorkService {
    public List<Work> getWorks();
    public void saveWork(Work work, MultipartHttpServletRequest files,
                         String[] designer_name,
                         String[] client_name,
                         String[] client_link,
                         String[] collaborator_name,
                         String[] collaborator_link,
                         String[] collaborator_job);
    public void updateWork(Work work, MultipartHttpServletRequest files,
                         String[] designer_name,
                         String[] client_name,
                         String[] client_link,
                         String[] collaborator_name,
                         String[] collaborator_link,
                         String[] collaborator_job);
    public void deleteWork(long id);

    public Work getWorkById(long id);
}
