package dk.kea.dat18i.teamsix.biotrio.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource dataSource;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username,password, enabled from user where username=?")
                .authoritiesByUsernameQuery("select username, role from user where username = ?");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/movies/**", "/select-seats", "/create-booking", "/booking-confirmation", "/contact", "/send-contact-email", "/about-us", "/faq", "/movie/**", "/css/**", "/images/**", "/save-movie/**", "/find-booking", "/view-booking", "/delete-booking-by-customer/**", "/search-movie:**" , "/search-movie-post", "/404", "/403").permitAll()
                .antMatchers("/control-panel/**").hasAnyRole("MANAGER", "EMPLOYEE")
                .antMatchers(
                        "/theater-room", "/delete-theater-room/**", "/add-theater-room", "/add-theater-room/save","/edit-theater-room/**",
                "/users", "/delete-user/**", "/add-user", "/add-user/save", "/edit-user/**", "/edit-user/save", "/edit-user-password/**"
                ).hasRole("MANAGER")
                .antMatchers("/**").hasAnyRole("MANAGER", "EMPLOYEE")
                .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
                .permitAll();
        http.exceptionHandling().accessDeniedPage("/403");
    }


}
