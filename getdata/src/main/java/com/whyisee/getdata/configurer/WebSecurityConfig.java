/*
package com.whyisee.getdata.configurer;

import com.whyisee.getdata.service.impl.TcAuthUserServiceImpl;
import com.whyisee.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

*/
/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/24 16:03
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 *//*

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许所有用户访问"/"和"/home"
*/
/*        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/login")
                //登录成功后默认跳转到
                .defaultSuccessUrl("/common/index")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                //退出登录后的默认url是"/login"
                .logoutSuccessUrl("/login")
                .permitAll();
        //解决非thymeleaf的form表单提交被拦截问题
        http.csrf().disable();*//*

        System.out.println("===test===>"+123);
        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        //http.addFilterBefore(filter, CsrfFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
     //   auth.userDetailsService(systemUserService()).passwordEncoder(passwordEncoder());
        //也可以将用户名密码写在内存，不推荐
        //auth.inMemoryAuthentication().withUser("admin").password("111111").roles("USER");
    }

    */
/**
     * 设置用户密码的加密方式为MD5加密
     *//*

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new Pbkdf2PasswordEncoder();
    }

    */
/**
     *从数据库中读取用户信息
     *//*

*/
/*    @Bean
    public UserDetailsService systemUserService() {
        return new TcAuthUserServiceImpl();
    }*//*



}
*/
