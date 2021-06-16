package com.mfe.auth.mfespringsecurity.Controller;

import com.mfe.auth.mfespringsecurity.Model.*;
import com.mfe.auth.mfespringsecurity.Services.MyUserDetailService;
import com.mfe.auth.mfespringsecurity.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.mfe.auth.mfespringsecurity.Services.RandomString.getAlphaNumericString;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SampleController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/home")
    public String home() {
        return "This is home";
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> users(){
        List<Users> users = new ArrayList<>();
        users.add(new Users("userOne","sample@gmail.com"));
        users.add(new Users("userTwo","sample@gmail.com"));
        users.add(new Users("userThree","sample@gmail.com"));

        return ResponseEntity.ok(users);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                           authenticationRequest.getPassword()));
       } catch (BadCredentialsException exception) {
           throw new Exception("Incorrect username or password", exception);
       }

       final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

       final String jwt = jwtUtil.generateToken(userDetails);
       return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @GetMapping("/token")
    public ResponseEntity<?> getToken()
            throws Exception {

        return ResponseEntity.ok(getAlphaNumericString(30));
    }


    @PostMapping("/changeAddress")
    public ResponseEntity<?> changeAddress(@RequestBody Address address, @RequestHeader("token") String token)
            throws Exception {
        return ResponseEntity.ok(address);
    }

    @PostMapping("/redeemFund")
    public ResponseEntity<?> redeemFund(@RequestBody RedeemForm redeemForm,
                                        @RequestHeader("token") String token) {
        return ResponseEntity.ok(redeemForm);
    }
}
