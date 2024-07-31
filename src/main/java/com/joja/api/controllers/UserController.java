package com.joja.api.controllers;
import com.joja.api.DTOs.DTOUser;
import com.joja.api.DTOs.DTOjwt;
import com.joja.api.models.UserModel;
import com.joja.api.repositories.UserRepository;
import com.joja.api.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<DTOUser> registerUser(@RequestBody DTOUser dtoUser){
        UserModel user = repository.save(new UserModel(dtoUser.username(), new BCryptPasswordEncoder().encode(dtoUser.password())));
        return ResponseEntity.status(201).body(new DTOUser(user.getUsername(), user.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<DTOjwt> loginUser(@RequestBody DTOUser dtoUser){
        Authentication authToken = new UsernamePasswordAuthenticationToken(dtoUser.username(), dtoUser.password());
        Authentication auth = manager.authenticate(authToken);
        String tokenJwt = jwtService.createTokenJwt((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new DTOjwt(tokenJwt));
    }

}
