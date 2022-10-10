package com.soosan.portfolio.controller;

import com.soosan.portfolio.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    WorkService workService;
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("works", workService.getWorks());
        return "home";
    }
    @GetMapping("/detail")
    public String detail(Model model, long id){
        model.addAttribute("work", workService.getWorkById(id));
        return "/detail";
    }
}
