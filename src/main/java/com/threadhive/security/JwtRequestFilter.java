package com.threadhive.security;

import com.threadhive.services.impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter that intercepts incoming HTTP requests and processes JWT authentication.
 * This class extends Spring Security's OncePerRequestFilter to ensure that the JWT
 * token is processed once per request, extracting user information and validating
 * the token before setting authentication in the security context.
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtUtility utility;
    private final CustomUserDetailsService userService;

    /**
     * This method filters incoming requests, extracts the JWT token from the
     * "Authorization" header, validates the token, and sets the authentication
     * object in the security context if the token is valid.
     *
     * @param request The HttpServletRequest containing the request data.
     * @param response The HttpServletResponse used to send the response.
     * @param filterChain The FilterChain to pass the request and response further down the filter chain.
     * @throws ServletException If an error occurs while processing the request.
     * @throws IOException If an input or output error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Extract the Authorization header from the request
            String authorizationHeader = request.getHeader("Authorization");

            // Check if the header is present and contains a valid "Bearer" token
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                // Extract the token from the header by removing the "Bearer " prefix
                String token = authorizationHeader.substring(7);

                // Initialize username as null in case token parsing fails
                String username = utility.extractUsername(token);


                // If username is valid and the security context doesn't already have authentication
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Load user details from username
                    CustomUserDetails userDetails = userService.loadUserByUsername(username);

                    // Validate the token for user
                    if (utility.validateToken(token, userDetails)) {
                        // Create a new authentication token with the user details
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities()
                                );

                        // Set the authentication in the security context for the current request
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing JWT authentication", e);
        }

        // Continue the filter chain if no authentication-related issues are encountered
        filterChain.doFilter(request, response);
    }
}
