package vttp.paf.pafMockAssessment.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.paf.pafMockAssessment.models.Task;
import vttp.paf.pafMockAssessment.models.User;
import vttp.paf.pafMockAssessment.repositories.TaskRepository;
import vttp.paf.pafMockAssessment.services.UserService;

@Controller
@RequestMapping(path = "/task")
public class TaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public String postTask(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {

        User user = new User();

        // @SuppressWarnings("unchecked")
        // List<Task> tasks = (List<Task>)httpSession.getAttribute("task");

        if (userService.findUserByUsername(form.getFirst("username")).equals(Optional.empty())) {
            // userService.insertUser(user);
            String errorMessage = "Something went wrong while attempting to create this task. Please try again.";
            
            model.addAttribute("errorMessage", errorMessage);
            
            return "error"; 

        } 
        else {
            // if(null == tasks) {
            //     System.out.println("This is a new session.");
            //     System.out.printf("session id = %s\n", httpSession.getId());
            //     tasks = new LinkedList<>(); 
            //     httpSession.setAttribute("task", tasks);
            // }   

            Optional<User> opt = userService.findUserByUsername(form.getFirst("username")); 
            user = opt.get(); 

            Task task = new Task();
            task.setDescription(form.getFirst("description"));
            task.setPriority(form.getFirst("priority"));
    
            String dateStr = form.getFirst("dueDate");
            System.out.println("Due date >>>> " + dateStr);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            task.setDueDate(date);
    
            task.setUserId(user.getUserId());

            Integer count = taskRepository.createTask(task);
            System.out.printf("Task >>> %s, count >>> %d\n", task.toString(), count);
            
            model.addAttribute("username", form.getFirst("username"));
            model.addAttribute("task", task);

            return "createTask";
        }

    }
}
