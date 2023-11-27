package com.codej.licoreria.controller;


import com.codej.licoreria.controller.dto.AccessTokenDTO;
import com.codej.licoreria.controller.dto.LoginDTO;
import com.codej.licoreria.security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AuthRestController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
   private final TokenProvider tokenProvider;

    /*@Operation(
            summary = "Obtiene un access token.",
            description = "Obtiene un access token usando un email y contraseña."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccessTokenDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ProblemDetail.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())}),
    })*/
    @PostMapping("/token")
    ResponseEntity<AccessTokenDTO> obtenerToken(@RequestBody LoginDTO loginDTO) {
        log.info("Login: {}", loginDTO);

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePAT);
        log.info("Authentication: {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.crearToken(authentication);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new AccessTokenDTO(token));
    }

}