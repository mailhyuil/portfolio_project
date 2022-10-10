package com.soosan.portfolio.controller;

import com.soosan.portfolio.domain.Client;
import com.soosan.portfolio.domain.Collaborator;
import com.soosan.portfolio.domain.Designer;
import com.soosan.portfolio.domain.Work;
import com.soosan.portfolio.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final WorkService workService;

    public AdminController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping({"", "/"})
    public String home(Model model){
        model.addAttribute("works", workService.getWorks());
        return "/admin/home";
    }
    @GetMapping("/insert")
    public String insert(){
        return null;
    }
    @PostMapping("/insert")
    public String insert(Work work, MultipartHttpServletRequest files,
                         String[] designer_name,
                         String[] client_name,
                         String[] client_link,
                         String[] collaborator_name,
                         String[] collaborator_link,
                         String[] collaborator_job){

        workService.saveWork(work, files, designer_name, client_name, client_link, collaborator_name, collaborator_link, collaborator_job);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String delete(long id){
        workService.deleteWork(id);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String update(long id, Model model){
        model.addAttribute("work", workService.getWorkById(id));
        return "/admin/update";
    }

    @PostMapping("/update")
    public String update(Work work, MultipartHttpServletRequest files,
                         String[] designer_name,
                         String[] client_name,
                         String[] client_link,
                         String[] collaborator_name,
                         String[] collaborator_link,
                         String[] collaborator_job){
        workService.updateWork(work, files, designer_name, client_name, client_link, collaborator_name, collaborator_link, collaborator_job);
        return "redirect:/admin";
    }
}
