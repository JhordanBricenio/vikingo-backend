package com.codej.licoreria.controller;


import com.codej.licoreria.controller.dto.AuthLoginRequest;
import com.codej.licoreria.controller.dto.AuthResponse;
import com.codej.licoreria.security.UserDetailsServiceImpl;
import com.codej.licoreria.service.IUsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthRestController {

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    @Autowired
    private IUsuarioService userService;

    /*@PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid Usuario user){
        return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
    }*/

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

}