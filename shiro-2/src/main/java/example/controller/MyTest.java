package example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyTest {

    @GetMapping("/test")
    public String test1(){
        return "hello";
    }
}
