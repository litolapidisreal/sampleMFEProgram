package com.mfe.auth.mfespringsecurity.Controller;

import com.mfe.auth.mfespringsecurity.Model.*;
import com.mfe.auth.mfespringsecurity.Services.MyUserDetailService;
import com.mfe.auth.mfespringsecurity.Utility.JwtUtil;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        Map<String, Object> expectedMap = getMapFromIoJsonWebTokenClaims(claims);
        String token = jwtUtil.createToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    public Map<String, Object> getMapFromIoJsonWebTokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validateToken()
            throws Exception {

        return ResponseEntity.ok("This is valid Token");
    }

    @PostMapping("/changeAddress")
    public ResponseEntity<?> changeAddress(@RequestBody Address address)
            throws Exception {
        return ResponseEntity.ok(address);
    }

    @PostMapping("/addFund")
    public ResponseEntity<?> addFund(@RequestBody AddForm addForm)
            throws Exception {
        return ResponseEntity.ok(addForm);
    }

    @PostMapping("/switchFund")
    public ResponseEntity<?> switchFund(@RequestBody SwitchFundForm switchFundForm)
            throws Exception {
        return ResponseEntity.ok(switchFundForm);
    }

    @PostMapping("/redeemFund")
    public ResponseEntity<?> redeemFund(@RequestBody RedeemFund redeemFund) {
        return ResponseEntity.ok(redeemFund);

    }
}
