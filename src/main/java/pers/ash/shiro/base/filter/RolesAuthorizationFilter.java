package pers.ash.shiro.base.filter;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

public class RolesAuthorizationFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest,
			ServletResponse servletResponse, Object mappedValue) throws Exception {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;

		if (rolesArray == null || rolesArray.length == 0) {
			// no roles specified, so nothing to check - allow access.
			return true;
		}

		Set<String> roles = CollectionUtils.asSet(rolesArray);
		for(String role : roles){
			if(subject.hasRole(role)){
				return false; //有管理员角色 --> false --> onAccessDenied --> 什么都不做，结束过滤器链
			}
		}
		//没有管理员角色 --> true --> 继续过滤器链 --> permissionsAuthorizationFilter
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest,
			ServletResponse servletResponse) throws Exception {
		return false;
	}

}
