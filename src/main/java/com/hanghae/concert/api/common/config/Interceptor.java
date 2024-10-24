package com.hanghae.concert.api.common.config;

import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.member.queue.exception.*;
import jakarta.security.auth.message.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.servlet.*;

@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {

    private final static String AUTHORIZATION = "Authorization";

    private final MemberQueueRepository memberQueueRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String token = request.getHeader(AUTHORIZATION);

        if (!StringUtils.hasText(token)) {
            throw new AuthException("토큰이 존재하지 않습니다.");
        }

        MemberQueue memberQueue = memberQueueRepository.findByToken(token)
                .orElseThrow(MemberQueueNotFoundException::new);

        if (memberQueue.isStatusActive()) {
            return true;
        } else {
            throw new AuthException("접근 불가능한 토큰입니다.");
        }
    }
}
