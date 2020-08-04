package com.groupten.project2.config;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class CustomSessionManager extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest srequest, ServletResponse response) {
        HttpServletRequest request = (HttpServletRequest) srequest;
        String sessionId = request.getHeader("X-cskaoyan-mall-Admin-Token");
        if(sessionId != null && !"".equals(sessionId)){
            return sessionId;
        }
        return super.getSessionId(request, response);
    }
}
