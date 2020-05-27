package com.spintech.testtask.controller;

import com.spintech.testtask.dto.ErrorDto;
import com.spintech.testtask.dto.RegisterDto;
import com.spintech.testtask.dto.UserDto;
import com.spintech.testtask.exception.ActorNotFoundException;
import com.spintech.testtask.repository.ActorRepository;
import com.spintech.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity registerUser(@RequestBody RegisterDto registerDto) {
        if (userService.registerUser(registerDto) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/favorite/actor/{id}", method = POST)
    public ResponseEntity addFavoriteActor(@PathVariable String id) throws ActorNotFoundException {
        UserDto response = userService.addFavoriteActor(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/favorite/actor/{id}", method = DELETE)
    public ResponseEntity deleteFavoriteActor(@PathVariable String id) throws ActorNotFoundException {
        UserDto response = userService.deleteFavoriteActor(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(ActorNotFoundException.class)
    public ResponseEntity actorNotFound(ActorNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
    }
}
