package com.how2java.tmall.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.how2java.tmall.pojo.User;

/**
 * 在Controller执行之前，将其拦截，进行执行前处理
 * @author qiuzelin
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	
	/**
	 * 在业务处理器调用请求之前被调用
	 * 如果返回false：从当前的拦截器往回执行所有拦截器的afterCompletion()，再退出拦截器链
	 * 如果返回true：执行下一个拦截器，直到所有的拦截器都执行完毕，再执行被拦截的Controller，然后
	 * 进入拦截器链，从最后一个拦截器往回执行所有的postHandler()，接着再从最后一个拦截器往回执行
	 * 所有的afterCompletion()
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	throws Exception {
		
		HttpSession session = request.getSession();
		
		/* 点击加入购物车之后，contextPath为:/tmall_ssm_myself*/
		String contextPath = session.getServletContext().getContextPath();
		

		//noNeedAuthPage是存放那些不需要登录就能访问的路径
		String[] noNeedAuthPage = 
		{"home", "checkLogin", "register", "loginAjax", "login", "product", "category", "search"};
		
		/* 例如点击加入购物车之后，uri为: /tmall_ssm_myself/foreaddCart */
		String uri = request.getRequestURI();

		/* 修改之后的uri为: /foreaddCart  */
		uri = StringUtils.remove(uri, contextPath);
		
		if (uri.startsWith("/fore")) {
			
			/* 点击加入购物车之后，method为: addCart  */
			String method = StringUtils.substringAfterLast(uri, "/fore");
			if (!Arrays.asList(noNeedAuthPage).contains(method)) {
				User user = (User)session.getAttribute("user");
				if (user == null) {
					response.sendRedirect("loginPage");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后，生成视图之前执行的动作，
	 * 可在modelAndView中加入数据，比如当前时间
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
						   Object handler, ModelAndView modelAndView) throws Exception {
		
	}
	
	
	/**
	 * 在DispatcherServlet完全处理请求后被调用，可用于清理资源等
	 * 当有拦截器抛出异常时，会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception ex) throws Exception {
		
	}
}












