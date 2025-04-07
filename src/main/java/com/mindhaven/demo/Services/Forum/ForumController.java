package com.mindhaven.demo.Services.Forum;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.mindhaven.demo.Entities.Community;

@RestController
@RequestMapping("/api/forum")
@CrossOrigin("*")
public class ForumController {

    @Autowired
    private ForumService forumService;

    private static final Logger log = LoggerFactory.getLogger(ForumController.class);

    @PostMapping("/new-chat/{userId}")
    public Community addChat(@PathVariable Long userId, @RequestBody ChatDto chatDto) {
        log.info("Received ChatDto: userId={}, thoughts={}", userId, chatDto.getThoughts());
        return forumService.newChat(userId, chatDto);
    }

    @GetMapping("/chats")
    public List<Community> getChats() {
        return forumService.getChats();
    }
}
