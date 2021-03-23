package pe.seller.integration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebMvc
@ComponentScan("pe.seller.integration.controllers")
public class WebConfiguration {

    @Bean
    public Filter CorsFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
                final var response = (HttpServletResponse) res;
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept, authorization, token");
                response.setHeader("Access-Control-Allow-Credentials", "true");

                chain.doFilter(req, res);
            }
            @Override
            public void init(FilterConfig filterConfig) {}

            @Override
            public void destroy() {}
        };
    }
}