package com.ideia.projetoideia.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ideia.projetoideia.security.filtro.AuthTokenFilter;
import com.ideia.projetoideia.security.util.AuthEntryPointJwt;
import com.ideia.projetoideia.services.AuthenticacaoService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticacaoService authenticacaoService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/resources/**", "/webjars/**").permitAll()
			.antMatchers(HttpMethod.POST, "/ideia/seguranca/login").permitAll()
			.antMatchers(HttpMethod.GET, "/ideia/usuario-logado").permitAll()
			.antMatchers(HttpMethod.POST, "/ideia/usuario").permitAll()
			.antMatchers(HttpMethod.PUT, "/ideia/usuario/resetar-senha").permitAll()
			.antMatchers(HttpMethod.GET, "/ideia/*").hasAuthority("USUARIO")
			.antMatchers(HttpMethod.POST, "/ideia/*").hasAuthority("USUARIO")
			.antMatchers(HttpMethod.PUT, "/ideia/*").hasAuthority("USUARIO")
			.antMatchers(HttpMethod.DELETE, "/ideia/*").hasAuthority("USUARIO")
			.anyRequest().authenticated()
			.and()
			.cors().and()
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}

}
