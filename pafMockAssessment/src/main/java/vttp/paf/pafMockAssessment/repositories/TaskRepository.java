package vttp.paf.pafMockAssessment.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.paf.pafMockAssessment.models.Task;
import static vttp.paf.pafMockAssessment.repositories.Queries.*;

@Repository
public class TaskRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    public Integer insertTask(Task task) {
        return jdbcTemplate.update(SQL_INSERT_TASK, task.getDescription(), task.getPriority().toString(), task.getDueDate(), task.getUserId());
    }

}
