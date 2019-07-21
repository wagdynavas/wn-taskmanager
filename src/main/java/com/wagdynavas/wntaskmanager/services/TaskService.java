package com.wagdynavas.wntaskmanager.services;

import com.wagdynavas.wntaskmanager.models.Task;
import com.wagdynavas.wntaskmanager.models.User;
import com.wagdynavas.wntaskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllOpenTasks() {
        return taskRepository.findAllOpenTasks();
    }

    public List<Task> getTasksByUser(String email) {
        return taskRepository.findTasksByUsername(email);
    }

    public List<Task> getAllOpenTasksByUser(String email) {
        return taskRepository.findAllOpenTasksByUsername(email);
    }



    public ModelAndView getById(Long id) {
        ModelAndView view = new ModelAndView();
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            view.setViewName("tasks/create");
            view.addObject("task", task);
        } else {
            view.setViewName("tasks/error");
        }
        return view;
    }

    public ModelAndView createTask(Task task, BindingResult result, String userEmail) {
        ModelAndView view = new ModelAndView();
        if (result.hasErrors()) {
            view.setViewName("tasks/create");
            view.addObject(task);
        } else {
            User logedUser = userService.getByEmail(userEmail) ;
            task.setUser(logedUser);
            taskRepository.save(task);
            view.setViewName("redirect:/tasks/list");
        }
        return view;
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public void resolved(Long id) {
        Task task = taskRepository.getOne(id);
        task.setDone(true);
        taskRepository.save(task);
    }
}
