package indi.monkey.springboot.doc.web.intercepter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import indi.monkey.springboot.doc.conf.prop.DocProperties;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SignIntercepter implements HandlerInterceptor{
	
	@Resource
	DocProperties docProperties;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String sign = request.getParameter("sign");
		if(docProperties.getSign() != null && docProperties.getSign().equalsIgnoreCase(sign)) {
			return true;
		}else {
			log.error("unsupport sign:{}",sign);
			handleNotAuthorized(response);
		}
		return false;
	}

	private void handleNotAuthorized(HttpServletResponse response) throws IOException {
		response.sendError(403);
	}
	
}
