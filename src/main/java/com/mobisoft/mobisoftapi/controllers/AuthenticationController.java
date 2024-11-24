package com.mobisoft.mobisoftapi.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mobisoft.mobisoftapi.dtos.auth.AuthenticationDTO;
import com.mobisoft.mobisoftapi.dtos.auth.RegisterDTO;
import com.mobisoft.mobisoftapi.dtos.auth.LoginResponseDTO;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.UserGroupRepository;
import com.mobisoft.mobisoftapi.repositories.UserRepository;
import com.mobisoft.mobisoftapi.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
	
	@PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        UserGroup group = new UserGroup();
        userGroupRepository.save(group);
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role(), group);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
