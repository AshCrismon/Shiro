package pers.ash.shiro.base.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.json.JSONObject;

public class PermissionsAuthorizationFilter extends AccessControlFilter {

	private JSONObject message = new JSONObject();

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest,
			ServletResponse servletResponse, Object mappedValue)
			throws Exception {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Subject subject = getSubject(request, response);

		String requestUri = request.getRequestURI();// 形式：/shiro/controller/user/findAllUsers
		String requestMethod = request.getMethod();
		String permissionUri = requestUri.substring(request.getContextPath().length());//获取：/controller/user/findAllUsers
		String permission = permissionUri + ":" + requestMethod;

		boolean isPermitted = true;
		if (subject.hasRole("admin")) { // 如果是超级管理员,直接通过,否则验证角色权限
			return true;
		}

		if (permissionUri != null) {
			if (!subject.isPermitted(permission)) {
				isPermitted = false;
			}
		}

		return isPermitted;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest,
			ServletResponse servletResponse) throws Exception {

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		renderMessage(response, "您不具有访问权限");
		return false;
	}

	private void renderMessage(HttpServletResponse response, String msg)
			throws IOException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		message.put("success", "false");
		message.put("msg", msg);
		out.write(message.toString());
	}

}
