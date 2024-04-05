package admin_user.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws jakarta.servlet.ServletException, IOException {
        // Obtain the context path
        String contextPath = request.getContextPath();
        
        // Get the default target URL
        String targetUrl = determineTargetUrl(request, response);
        
        // If context path is not empty, append it to the target URL
        if (contextPath != null && !contextPath.isEmpty()) {
            targetUrl = contextPath + targetUrl;
        }
        
        // Redirect the user to the target URL
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

