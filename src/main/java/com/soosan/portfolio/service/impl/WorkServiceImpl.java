package com.soosan.portfolio.service.impl;

import com.soosan.portfolio.domain.*;
import com.soosan.portfolio.repository.*;
import com.soosan.portfolio.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.naming.NameNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
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


        imgList.stream().filter((item) -> {
            return !item.isEmpty();
        }).map((img) -> {
            return createImageFile(img, work);
        }).forEach((vo) -> {
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

    @Override
    public void updateWork(Work work, MultipartHttpServletRequest files,
                           String[] designer_name,
                           String[] client_name,
                           String[] client_link, String[] collaborator_name,
                           String[] collaborator_link,
                           String[] collaborator_job) {

        Work foundWork = workRepository.findById(work.getId()).get();
        foundWork.setCategory(work.getCategory());
        foundWork.setTitle(work.getTitle());
        foundWork.setContent(work.getContent());

        List<MultipartFile> imageList = files.getFiles("files");
        System.out.println("imageList : " + imageList);

        if(!imageList.get(0).isEmpty()){
            List<Image> foundImageList = imageRepository.findByWorkId(work.getId());

            for (Image image : foundImageList) {
                File file = new File("C:/Temp/upload" + "\\" + image.getUuidName());
                if (file.exists()) {
                    file.delete();
                    imageRepository.deleteById(image.getId());
                }
            }

            imageList.stream().filter((item) -> {
                return !item.isEmpty();
            }).map((img) -> {
                return createImageFile(img, work);
            }).forEach((vo) -> {
                imageRepository.save(vo);
                foundWork.getImageList().add(vo);
            });
        }

        for (int i = 0; i < designer_name.length; i++){
            Designer foundDesigner = foundWork.getDesignerList().get(i);
            foundDesigner.setName(designer_name[i]);
        }

        for(int i = 0; i< client_name.length; i++){
            Client foundClient = foundWork.getClientList().get(i);
            foundClient.setName(client_name[i]);
            foundClient.setLink(client_link[i]);
        }

        for(int i = 0; i < collaborator_name.length; i++){
            Collaborator foundCollaborator = foundWork.getCollaboratorList().get(i);
            foundCollaborator.setName(collaborator_name[i]);
            foundCollaborator.setLink(collaborator_link[i]);
            foundCollaborator.setJob(collaborator_job[i]);
        }
        workRepository.save(foundWork);
    }

    @Override
    public void deleteWork(long id) {
        List<Image> imageList = imageRepository.findByWorkId(id);

        for (Image image : imageList) {
            File file = new File("C:/Temp/upload" + "\\" + image.getUuidName());
            if (file.exists()) {
                file.delete();
            }
        }
        workRepository.deleteById(id);
    }

    @Override
    public Work getWorkById(long id) {
        return workRepository.findById(id).get();
    }

    @Override
    public List<Work> getWorksByKeyword(String keyword) throws NameNotFoundException {
        if(keyword == null){
            return workRepository.findAll();
        }
        List<Work> works = workRepository.findAllByCategory(keyword);
        if(works.isEmpty()){
            throw new NameNotFoundException();
        }
        return works;
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
