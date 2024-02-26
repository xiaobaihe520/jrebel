package com.jrebel.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * index
 *
 * @author wy
 */
@RestController
public class IndexController {

    @GetMapping("/")
    String index(HttpServletRequest request) {
        return "<h1>Hello,This is a Jrebel License Server!</h1><h2>Activation address was: <span style=\"color: red\">" +
                request.getRequestURL() + UUID.randomUUID() + "</span></h2>";
    }
}
