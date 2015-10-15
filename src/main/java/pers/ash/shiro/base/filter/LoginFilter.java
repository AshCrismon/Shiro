package pers.ash.shiro.base.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFilter extends PathMatchingFilter {
	
	private JSONObject message = new JSONObject();

	private String loginRequest = "/admin/login.do";
	private String loginUrl = "/admin/login.html";
	private String successUrl = "/admin/index.html";

	@Override
	protected boolean onPreHandle(ServletRequest servletRequest,
			ServletResponse servletResponse, Object mappedValue) throws Exception {

		Subject subject = SecurityUtils.getSubject();
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Pragma", "no-cache");

		//已经登录
		if (subject.isAuthenticated()) {
			//请求login.html或login.do,转到index.html
			if (isLoginUrl(request) || isLoginRequest(request)) {
				return redirectToSuccessUrl(request, response);
			}
			return true;// 继续过滤器链
		}
		//未登录,请求login.html或login.do
		if (isLoginUrl(request) || isLoginRequest(request)) {
			if ("post".equalsIgnoreCase(request.getMethod())) {// form表单提交
				boolean loginSuccess = login(request, response); // 登录
				if (loginSuccess) {
					return redirectToSuccessUrl(request, response);
				}else{
					return false;
				}
			}
			return true;// 继续过滤器链
		} else {//未登录, 保存当前地址并重定向到登录界面
			saveRequestAndRedirectToLogin(request, response);
			return false;
		}
	}

	private boolean redirectToSuccessUrl(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		WebUtils.redirectToSavedRequest(request, response, successUrl);
		return false;
	}

	private void saveRequestAndRedirectToLogin(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		WebUtils.saveRequest(req);
		WebUtils.issueRedirect(req, resp, loginUrl);
	}

	private boolean login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			SecurityUtils.getSubject().login(
					new UsernamePasswordToken(username, password));
		} catch (UnknownAccountException ex) {
			renderMessage(response, ex.getMessage());
			return false;
		}catch (IncorrectCredentialsException ex) {
			renderMessage(response, ex.getMessage());
			return false;
		}catch (LockedAccountException ex) {
			renderMessage(response, ex.getMessage());
			return false;
		}
		return true;
	}
	
	private void renderMessage(HttpServletResponse response, String msg) throws IOException{
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		message.put("success", "false");
		message.put("msg", msg);
		out.write(message.toString());
	}

	private boolean isLoginRequest(HttpServletRequest req) {
		return pathsMatch(loginRequest, WebUtils.getPathWithinApplication(req));
	}
	
	private boolean isLoginUrl(HttpServletRequest req) {
		return pathsMatch(loginUrl, WebUtils.getPathWithinApplication(req));
	}
}
