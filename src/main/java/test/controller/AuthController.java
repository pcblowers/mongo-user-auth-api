package test.controller;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import test.models.AuthRequest;
import test.models.AuthResponse;
import test.models.Users;
import test.repositories.UsersRepository;
import test.security.CustomUserDetailsService;
import test.security.JWTUtil;
import test.security.UserDetailsImpl;

import javax.annotation.security.PermitAll;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("v1/auth")
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PermitAll
    @GetMapping("/")
    public String welcome() {
        return "Welcome!!!";
    }

    @PermitAll
    @GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
    public String getHealthCheck() {
        return "{ \"isWorking\": true }";
    }

    /**
     * Responsible for authenticating a user.
     * @param authRequest Credentials
     * @return 200 - User ID, Token, and List of Authorities
     * @throws Exception Invalid Credentials
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(
                    new AuthResponse(
                            userDetails.getId(),
                            jwt,
                            userDetails.getAuthorities().stream()
                                    .map(item -> item.getAuthority())
                                    .collect(Collectors.toList())
                    )
            );

        } catch (Exception ex) {
            throw new Exception("Invalid Username/Password combination");
        }
    }

    /**
     * Signup endpoint for registering users. <br/>This is responsible for creating an entry
     * in the <i>users</i> collection.
     * @param userInfo Request Body containing fields necessary for registering a user and updating both column families
     */
    @PermitAll
    @PostMapping("/signup")
    public void signUp(@RequestBody String userInfo){
        try{
            JSONObject requestBody = new JSONObject(userInfo);

            List<String> authorities = new ArrayList<>();
            JSONArray jsonArray = requestBody.getJSONArray("roles");

            for(int i = 0; i < jsonArray.length(); i++){
                authorities.add(jsonArray.getString(i));
            }

            Users user = new Users(
                    null,
                    requestBody.getString("firstName"),
                    requestBody.getString("lastName"),
                    requestBody.getString("email"),
                    requestBody.getString("username"),
                    requestBody.getString("password"),
                    authorities,
                    new Date()
                    );

            userDetailsService.save(user);

        } catch (Exception e){
            e.getMessage();
        }
    }
}
