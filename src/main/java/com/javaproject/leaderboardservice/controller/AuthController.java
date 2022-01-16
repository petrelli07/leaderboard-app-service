package com.javaproject.leaderboardservice.controller;

import com.javaproject.leaderboardservice.model.ERole;
import com.javaproject.leaderboardservice.model.Role;
import com.javaproject.leaderboardservice.model.User;
import com.javaproject.leaderboardservice.payload.request.LoginRequest;
import com.javaproject.leaderboardservice.payload.request.SignupRequest;
import com.javaproject.leaderboardservice.payload.request.VerificationRequest;
import com.javaproject.leaderboardservice.payload.response.JwtResponse;
import com.javaproject.leaderboardservice.payload.response.MessageResponse;
import com.javaproject.leaderboardservice.repositories.RoleRepository;
import com.javaproject.leaderboardservice.repositories.UserRepository;
import com.javaproject.leaderboardservice.security.jwt.JwtUtils;
import com.javaproject.leaderboardservice.security.services.UserDetailsImpl;
import com.javaproject.leaderboardservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/send-verification-code")
    public ResponseEntity<?> sendVerificationCode(@Valid @RequestBody VerificationRequest verificationRequest) throws MessagingException, IOException {

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        String email = verificationRequest.getEmail();
        User user = new User();
        user.setEmail(email);
        user.setIs_verified(false);
        user.setRoles(roles);
        roles.add(userRole);

        long verificationCode = userService.generateVerificationCode();
        user.setVerification_code(verificationCode);

        if (userService.sendVerificationMessage(email, verificationCode)) {
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Verification Email Sent"));
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: An error occurred"));
        }
    }

    @PostMapping("/verify-user-code")
    public ResponseEntity<?> verifyUserCode(@RequestBody VerificationRequest verificationRequest){
        long verificationCode = verificationRequest.getVerification_code();
        String email = verificationRequest.getEmail();

        if (userService.verifyUserCode(verificationCode, email)){
            Optional<User> userDetail = userRepository.findByEmail(email);
            return ResponseEntity.ok(userDetail.get());
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: An error occurred"));
        }
    }

    @PutMapping("/complete-registration")
    public ResponseEntity<?> completeUserRegistration(@RequestBody VerificationRequest verificationRequest){

        if (userRepository.existsByUsername(verificationRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        String userName = verificationRequest.getUsername();
        String password = encoder.encode(verificationRequest.getPassword());
        long userId = verificationRequest.getUserId();

        User userDetails =  userService.completeRegistration(userId, userName, password);

        return ResponseEntity.status(HttpStatus.OK).body(userDetails);

    }
}
