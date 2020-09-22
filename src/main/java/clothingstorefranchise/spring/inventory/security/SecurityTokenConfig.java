package clothingstorefranchise.spring.inventory.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import clothingstorefranchise.spring.common.constants.Rol;
import clothingstorefranchise.spring.common.security.config.JwtConfiguration;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    protected final JwtConfiguration jwtConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(jwtConfiguration.getLoginUrl(), "/**/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
                //.antMatchers("/auth/user/**").hasRole(Rol.ADMIN)
                .antMatchers("/auth/user/**").hasAnyRole(Rol.CUSTOMER,Rol.ADMIN)
                .anyRequest().authenticated();
    }
}
