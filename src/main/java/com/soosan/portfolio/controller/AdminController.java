package com.soosan.portfolio.controller;

import com.soosan.portfolio.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
