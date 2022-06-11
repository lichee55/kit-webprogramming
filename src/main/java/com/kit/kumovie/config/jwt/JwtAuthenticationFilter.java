package com.kit.kumovie.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.kumovie.dto.SignInResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper mapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        log.info(httpRequest.getMethod() + " " + httpRequest.getRequestURI() + " " + httpRequest.getQueryString());

        String token = jwtTokenProvider.resolveToken(httpRequest);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(httpRequest);
        log.info("token : {}", token);
        log.info("refreshToken : {}", refreshToken);
        if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
            log.info("refreshToken is valid");
            String reissueAccessToken = jwtTokenProvider.createToken(refreshToken);
            SignInResultDTO build = SignInResultDTO.builder()
                    .accessToken(reissueAccessToken)
                    .refreshToken(refreshToken)
                    .build();
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(httpServletResponse.getWriter(), build);
            return;
        } else if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}