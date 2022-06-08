package com.kit.kumovie.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.kumovie.dto.SignInResultDTO;
import lombok.RequiredArgsConstructor;
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
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper mapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            String token = jwtTokenProvider.resolveToken(((HttpServletRequest) request));
            String refreshToken = jwtTokenProvider.resolveRefreshToken(((HttpServletRequest) request));
            if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
                String reissueAccessToken = jwtTokenProvider.createToken(refreshToken);
                SignInResultDTO build = SignInResultDTO.builder()
                        .accessToken(reissueAccessToken)
                        .refreshToken(refreshToken)
                        .build();
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(httpServletResponse.getWriter(), build);
            }
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}