package com.kit.kumovie.common;

import com.kit.kumovie.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Common {

    public static Member getUserContext() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}