package app.service;

import app.dao.StudentRepository;
import app.vo.Greeting;
import app.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Me on 2017/9/16.
 */
@Service
public class WebSocketService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Greeting sendMessage(String name) {
        Greeting greeting = new Greeting(name);
        messagingTemplate.convertAndSend("/topic/greetings",
                greeting);
        return greeting;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentInfo(Long id) {
        return studentRepository.findOne(id);
    }
}
