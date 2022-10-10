package com.soosan.portfolio.service.impl;

import com.soosan.portfolio.domain.*;
import com.soosan.portfolio.repository.*;
import com.soosan.portfolio.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class WorkServiceImpl implements WorkService {
    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CollaboratorRepository collaboratorRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private DesignerRepository designerRepository;
    @Override
    public List<Work> getWorks() {
        return workRepository.findAll();
    }

    @Override
    public void saveWork(Work work, MultipartHttpServletRequest files,
                         String[] designer_name,
                         String[] client_name,
                         String[] client_link,
                         String[] collaborator_name,
                         String[] collaborator_link,
                         String[] collaborator_job) {
        List<MultipartFile> imgList = files.getFiles("files");

        List<Image> imgs = new ArrayList<>();

        imgList.stream().filter((item) -> {
            return !item.isEmpty();
        }).map((img) -> {
            return createImageFile(img, work);
        }).forEach((vo) -> {
            imgs.add(vo);
            imageRepository.save(vo);
            work.getImageList().add(vo);
        });

        for (int i = 0; i < designer_name.length; i++){
            Designer designer = designerRepository.save(Designer.builder().name(designer_name[i]).work(work).build());
            work.getDesignerList().add(designer);
        }

        for(int i = 0; i< client_name.length; i++){
            Client client = clientRepository.save(Client.builder().name(client_name[i]).link(client_link[i]).work(work).build());
            work.getClientList().add(client);
        }

        for(int i = 0; i < collaborator_name.length; i++){
            Collaborator collaborator = collaboratorRepository.save(Collaborator.builder().name(collaborator_name[i]).job(collaborator_job[i]).link(collaborator_link[i]).work(work).build());
            work.getCollaboratorList().add(collaborator);
        }

        workRepository.save(work);
    }

    private Image createImageFile(MultipartFile img, Work work) {
        String uuid = UUID.randomUUID().toString();
        String uuidImg = uuid + img.getOriginalFilename();

        Image image = Image.builder().uuidName(uuidImg).name(img.getOriginalFilename()).work(work)
                .build();

        File uploadFile = new File("/", uuidImg);

        try {
            img.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
