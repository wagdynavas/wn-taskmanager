package com.wagdynavas.wntaskmanager.controllers;

import com.wagdynavas.wntaskmanager.models.Task;
import com.wagdynavas.wntaskmanager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        String userEmail = request.getUserPrincipal().getName();
        ModelAndView view = new ModelAndView();
        view.setViewName("tasks/list");
        view.addObject("tasks", taskService.getTasksByUser(userEmail));
        return view;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView view = new ModelAndView();
        view.setViewName("tasks/create");
        view.addObject("task",new Task());
        view.addObject("todayDate", LocalDate.now());//Use to set the min datepicker of the calendar
        return view;
    }

    @PostMapping("/create")
    public ModelAndView create(@Valid Task task, BindingResult result, HttpServletRequest request) {
        String userEmail = request.getUserPrincipal().getName();
        return  taskService.createTask(task, result, userEmail);
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") Long id) {
        return taskService.getById(id);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        taskService.delete(id);
        return "redirect:/tasks/list";
    }

    @GetMapping("/resolved/{id}")
    public String resolved(@PathVariable("id") Long id) {
        taskService.resolved(id);
        return "redirect:/tasks/list";
    }


}
