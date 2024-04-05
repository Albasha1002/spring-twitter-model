package admin_user.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ContextPathUtils {

    private HttpServletRequest request;

    public ContextPathUtils(HttpServletRequest request) {
        this.request = request;
    }

    public String getContextPath() {
        return request.getContextPath();
    }
    
}