package admin_user.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
	
	
	 private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	    public RedirectStrategy getRedirectStrategy() {
	        return redirectStrategy;
	    }

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		var authourities = authentication.getAuthorities();
		var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();
		
		
		if (roles.orElse("").equals("ADMIN")) {
			response.sendRedirect("/admin-page");
			
		} else if (roles.orElse("").equals("USER")) {
			response.sendRedirect("/user-page");
		} else {
			response.sendRedirect("/error");
		}
		
		
		
	}
	
//	 @Override
//	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//	        // Obtain the context path
//	        String contextPath = request.getContextPath();
//	        
//	        // Get the default target URL based on user roles
//	        String targetUrl = determineTargetUrl(authentication);
//	        
//	        // If context path is not empty, append it to the target URL
//	        if (contextPath != null && !contextPath.isEmpty()) {
//	            targetUrl = contextPath + targetUrl;
//	        }
//	        
//	        // Redirect the user to the target URL
//	        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//	    }
//	 
//	
//	    
//	
	
	
	  private String determineTargetUrl(Authentication authentication) {
	        // Determine the target URL based on user roles
	        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
	            return "/admin-page"; // Example URL for admin role
	        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
	            return "/user-page"; // Example URL for user role
	        } else {
	            return "/error"; // Default URL for other roles
	        }
	  }

}
