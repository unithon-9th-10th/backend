package center.unit.beggar.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/api/*")
public class ApiLogFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		log.info("[API URI] : {}", request.getRequestURI());

		// ContentCachingXXXWrapper -> i/o Stream의 유실을 방지하기 위해 body를 caching하는 wrapper
		ContentCachingRequestWrapper requestToCache = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseToCache = new ContentCachingResponseWrapper(response);

		filterChain.doFilter(requestToCache, responseToCache);

		String beggarMemberId = requestToCache.getHeader("X-BEGGAR-MEMBER-ID");
		if (beggarMemberId != null) {
			log.info("X-BEGGAR-MEMBER-ID : {}", beggarMemberId);
		}

		log.info("Request Body : {}", objectMapper.readTree(requestToCache.getContentAsByteArray()));
		log.info("Response Body : {}", objectMapper.readTree(responseToCache.getContentAsByteArray()));

		responseToCache.copyBodyToResponse();
	}

}
