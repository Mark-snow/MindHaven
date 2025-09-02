package com.mindhaven.demo.Services.Forum;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Configurations.DatabaseConfig.SequenceGenerator;
import com.mindhaven.demo.Entities.Community;
import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Exceptions.UserNotFoundException;
import com.mindhaven.demo.Repositories.CommunityRepository;
import com.mindhaven.demo.Repositories.UserRepository;

@Service
public class ForumService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    private static final Logger log = LoggerFactory.getLogger(ForumService.class);


    public Community newChat (Long userId, ChatDto chatDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));;
        log.info("User found: {}", user.getFullName());

        Community community = new Community();

        community.setId(sequenceGenerator.generateSequence("forum_sequence"));
        community.setUserId(userId);
        community.setName(user.getFullName());
        community.setThoughts(chatDto.getThoughts());
        community.setDate(java.time.LocalDate.now());
        communityRepository.save(community);
        log.info("Community saved: {}", community.getName());
        return community;
    }

    public List<Community> getChats () {
        return communityRepository.findAll();
    }
}
