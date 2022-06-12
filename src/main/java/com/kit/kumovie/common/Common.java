package com.kit.kumovie.common;

import com.kit.kumovie.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Common {

    public static Member getUserContext() {
        log.info("getUserContext : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return null;
        }
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}