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

import java.util.Arrays;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    protected final JwtConfiguration jwtConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> {	
                	CorsConfiguration cors = new CorsConfiguration();                 
                    cors.setAllowedMethods(
                      Arrays.asList( 
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.HEAD.name()
                   ));   
                   cors.applyPermitDefaultValues();           
                   return cors;   
               })
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(jwtConfiguration.getLoginUrl(), "/**/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
                
                .antMatchers(HttpMethod.GET, "/warehouses/**").hasAnyRole(Rol.ADMIN, Rol.WAREHOUSE_EMPLOYEE)
                .antMatchers(HttpMethod.POST, "/warehouses/**").hasRole(Rol.ADMIN)
                .antMatchers(HttpMethod.PUT, "/warehouses/**").hasRole(Rol.ADMIN)
                .antMatchers(HttpMethod.DELETE, "/warehouses/**").hasRole(Rol.ADMIN)
                .antMatchers("/warehouses/without_product/**").hasAnyRole(Rol.ADMIN)
                
                .antMatchers(HttpMethod.GET,"/shops/**").hasAnyRole(Rol.SHOP_EMPLOYEE,Rol.ADMIN)
                .antMatchers(HttpMethod.POST,"/shops/**").hasRole(Rol.ADMIN)
                .antMatchers(HttpMethod.PUT,"/shops/**").hasRole(Rol.ADMIN)
                .antMatchers(HttpMethod.DELETE,"/shops/**").hasRole(Rol.ADMIN)
                .antMatchers("/shops/without_product/**").hasAnyRole(Rol.ADMIN)
                
                .antMatchers("/products/**/stocks-without-warehouses").permitAll()
                .antMatchers("/products/**").hasRole(Rol.ADMIN)
                
                .anyRequest().authenticated();
    }
}
