package com.example.authtoken.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

@RestController
public class PublisherTestController {
    @Autowired
    private MessagePublisher redisMessagePublisher;

    @GetMapping("/hello")
    public Flux<String> hello(@RequestParam String message) {
        redisMessagePublisher.publish(message);
        return Flux.just("Hello","FLux");
    }
}
