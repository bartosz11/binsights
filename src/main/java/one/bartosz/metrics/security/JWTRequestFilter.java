package one.bartosz.metrics.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import one.bartosz.metrics.models.User;
import one.bartosz.metrics.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {


    private final JWTTokenUtils tokenUtils;
    private final UserService userService;

    public JWTRequestFilter(JWTTokenUtils tokenUtils, UserService userService) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        HashMap<String, String> cookies = cookieArrayToMap(request.getCookies());
        String token = null;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
        } else if (cookies.containsKey("auth-token")) {
            token = cookies.get("auth-token");
        }
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = null;
        try {
            username = tokenUtils.getUsernameFromToken(token);
        } catch (IllegalArgumentException | JWTVerificationException ignored) {
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = (User) userService.loadUserByUsername(username);
            if (tokenUtils.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }
        filterChain.doFilter(request, response);
    }

    private HashMap<String, String> cookieArrayToMap(Cookie[] array) {
        HashMap<String, String> cookies = new HashMap<>();
        if (array != null) {
            for (Cookie cookie : array) {
                cookies.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookies;
    }
}
