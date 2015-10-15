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

		String requestUri = request.getRequestURI();
		String permissionUri = requestUri.substring(requestUri.indexOf("/", 1));

		boolean isPermitted = true;
		if (permissionUri != null) {
			if (!subject.isPermitted(permissionUri)) {
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
