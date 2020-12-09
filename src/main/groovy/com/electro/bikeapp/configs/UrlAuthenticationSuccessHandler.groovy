package com.electro.bikeapp.configs

import groovy.util.logging.Slf4j
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

@Slf4j
@SuppressWarnings(['VariableName', 'PrivateFieldCouldBeFinal', 'UnnecessaryConstructor'])
class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy()

    UrlAuthenticationSuccessHandler() {
        super()
    }

    // API

    @Override
    void onAuthenticationSuccess(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final Authentication authentication) throws IOException {
        handle(request, response, authentication)
        clearAuthenticationAttributes(request)
    }

    // IMPL

    protected void handle(final HttpServletRequest request,
                          final HttpServletResponse response,
                          final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication)

        if (response.isCommitted()) {
            log.debug('Response has already been committed. Unable to redirect to ' + targetUrl)
            return
        }

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    protected String determineTargetUrl(final Authentication authentication) {
        Map<String, String> roleTargetUrlMap = new HashMap<>()
        roleTargetUrlMap.put('OWNER', '/owner')
        roleTargetUrlMap.put('MANAGER', '/manager')
        roleTargetUrlMap.put('BOOKKEEPER', '/bookkeeper')
        roleTargetUrlMap.put('EMPLOYEE', '/employee')

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities()
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority()
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName)
            }
        }

        throw new IllegalStateException()
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the session
     * during the authentication process.
     */
    protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false)

        if (session == null) {
            return
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)
    }

}
