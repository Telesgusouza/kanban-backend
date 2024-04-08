package com.example.demo.config.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.config.security.SecurityFilter;
import com.example.demo.entity.User;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/*

agora ele esta entrando normal porém tem uma mensagem na tela "Failed to load remote configuration." sendo que eu tenho um arquivo controller com 2 rotas
no console.log do navegador apareceu "undefined /docs-kanban/swagger-config spec-actions.js:19:14	next spec-actions.js:19"
//////////////////////////////////////
  
  
  as soluções apresentadas não fizeram efeito, mas essa mensgem surgiu no meu console e aqui o codigo do ṕrojeto
  
 XHRGET
http://localhost:8080/docs-kanban/swagger-config
[HTTP/1.1 403  2ms]

URI inválida. Falha no carregamento do recurso de mídia . index.html
undefined /docs-kanban/swagger-config spec-actions.js:19:14

URI inválida. Falha no carregamento do recurso de mídia .

XHRGET
http://localhost:8080/docs-kanban/swagger-config
[HTTP/1.1 403  2ms]

URI inválida. Falha no carregamento do recurso de mídia . index.html
undefined /docs-kanban/swagger-config spec-actions.js:19:14

 
@Configuration
public class DocOpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(
				new Info().title("REST API - Kanban").description("Api para gestão de tarefas kanban").version("v1")
						.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
						.contact(new Contact().name("Gustavo Teles").email("gustavo.teles711@gmail.com")));
	}

}


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private SecurityFilter securityFilter;

	private static final String[] DOCUMENT_OPENAPI = { "/docs/index.html", "/docs-park.html", "/docs-park/**",
			"/v3/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**", "/**.html",
			"/webjars/**", "/configuration/**", "/swagger-resources/**" };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
						
						.requestMatchers(DOCUMENT_OPENAPI).permitAll()

						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();

	}

# Swagger
springdoc.swagger-ui.path =/docs-kanban.html
springdoc.api-docs.path =/docs-kanban
springdoc.packagesToScan=com.example.demo.controller
 
 
 

*/

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(User user) {

		try {

			Algorithm algorithm = Algorithm.HMAC256(secret);

			String token = JWT.create().withIssuer("auth-api").withSubject(user.getUsername())
					.withExpiresAt(genExpirationDate()).sign(algorithm);

			return token;

		} catch (JWTCreationException e) {
			throw new RuntimeException("Error generating jwt token ", e);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();

		} catch (JWTVerificationException e) {
			return "";
		}
	}

	public Instant genExpirationDate() {
		return LocalDateTime.now().plusDays(90).toInstant(ZoneOffset.of("-03:00"));
	}

}
