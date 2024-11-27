package com.mobisoft.mobisoftapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mobisoft.mobisoftapi.dtos.auth.AuthenticationDTO;
import com.mobisoft.mobisoftapi.dtos.auth.RegisterDTO;
import com.mobisoft.mobisoftapi.dtos.auth.LoginResponseDTO;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.AdministrationRepository;
import com.mobisoft.mobisoftapi.repositories.UserGroupRepository;
import com.mobisoft.mobisoftapi.repositories.UserRepository;
import com.mobisoft.mobisoftapi.services.TokenService;
import com.mobisoft.mobisoftapi.services.UserService;

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
	private AdministrationRepository administrationRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService userService;

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
        User newUser = new User(data.login(), encryptedPassword, data.name(), data.role(), group);
        this.repository.save(newUser);
        
        Administration newAdministration = new Administration();
        newAdministration.setAdditionalAssembler(BigDecimal.valueOf(8));
        newAdministration.setAdditionalFinancial(BigDecimal.valueOf(30));
        newAdministration.setAdditionalProjectDesigner(BigDecimal.valueOf(1));
        newAdministration.setAdditionalSeller(BigDecimal.valueOf(10));
        newAdministration.setTax(BigDecimal.valueOf(7));
        newAdministration.setUserGroup(group);
        administrationRepository.save(newAdministration);

        return ResponseEntity.ok().build();
    }
	
	@GetMapping("/getInfo")
    public ResponseEntity<User> getInfoDetails() {
        User user = userService.getLoggedUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<Boolean> checkAuthentication() {
        try {
            User loggedUser = userService.getLoggedUser();
            return ResponseEntity.ok(loggedUser != null);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
