package com.soosan.portfolio.controller;

import com.soosan.portfolio.domain.Work;
import com.soosan.portfolio.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.naming.NameNotFoundException;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    WorkService workService;
    @GetMapping("/")
    public String home(Model model, String keyword){
        List<Work> works = null;

        try {
            works = workService.getWorksByKeyword(keyword);
        } catch (NameNotFoundException e) {
            return "redirect:/?error=NOT_FOUND";
        }

        model.addAttribute("works", works);
        return "home";
    }
    @GetMapping("/detail")
    public String detail(Model model, long id){
        model.addAttribute("work", workService.getWorkById(id));
        return "/detail";
    }
}
