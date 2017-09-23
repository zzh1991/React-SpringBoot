package app.controller;

import app.entity.Student;
import app.vo.Greeting;
import app.vo.HelloMessage;
import app.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return Greeting.builder().
                content("Hello, " + message.getName() + "!")
                .build();
    }

    @GetMapping("/test/{name}")
    public Greeting test(@PathVariable String name) {
        return webSocketService.sendMessage(name);
    }

    @GetMapping("/data")
    public List<Student> getData() {
        return webSocketService.getStudents();
    }

    @GetMapping("/student/{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        return webSocketService.getStudentInfo(id);
    }
}
