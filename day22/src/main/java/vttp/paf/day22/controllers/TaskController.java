package vttp.paf.day22.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.paf.day22.models.Task;
import vttp.paf.day22.models.User;
import vttp.paf.day22.repositories.TaskRepo;
import vttp.paf.day22.services.UserService;

@Controller
@RequestMapping(path = "/task")
public class TaskController {
    
    // @GetMapping
    // public String getTask() {
    //     return "task";
    // }

    @Autowired
    private UserService userSvc; 

    @Autowired
    private TaskRepo taskRepo; 

    @PostMapping
    public String postTask(@RequestBody MultiValueMap<String, String> form, Model model) {

        User user = new User(); 
        user.setUsername(form.getFirst("username"));
        user.setPassword(form.getFirst("password"));

        // check if user exists and auth is right
        if(!userSvc.authenticateUser(user)) {
            return "fail_auth";
        }

        Task task = new Task(); 
        task.setTaskName(form.getFirst("taskName"));
        task.setPriority(form.getFirst("priority"));
        task.setAssignTo(user);
        task.setCompletionDate(form.getFirst("completion"));

        Integer count = taskRepo.createTask(task);
        System.out.println(count);
        return "createdTask";
    }

}
