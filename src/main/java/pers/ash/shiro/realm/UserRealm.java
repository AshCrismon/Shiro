package pers.ash.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pers.ash.shiro.helper.PasswordHelper;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.User;
import pers.ash.shiro.service.UserService;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	/**
	 * 授权操作，获取身份授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		User user = userService.findByUsername(username);
		String userId = user.getId();
		Set<String> roles = new HashSet<String>(
				userService.findStringRoles(userId));
		Set<String> permissions = new HashSet<String>(
				userService.findStringPermissions(userId));
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}

	/**
	 * 认证操作，获取身份验证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal(); // =>等价于token.getUsername()
		String password = String.valueOf((char[]) token.getCredentials());
		User user = userService.findByUsername(username);
		if (user == null) {
			logger.error("用户名[" + username + "]没找到");
			throw new UnknownAccountException();// 没找到帐号
		}
		String hashedPassword = PasswordHelper
				.encrypt(password, user.getSalt());
		if (!user.getPassword().equals(hashedPassword)) {
			logger.error("用户名和密码不匹配");
			throw new IncorrectCredentialsException();
		}
		if (ModelState.LOCKED.equals(user.getState())) {
			logger.error("用户[" + username + "]被锁定");
			throw new LockedAccountException(); // 帐号锁定
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getUsername(), // 用户名
				password, // 密码
				ByteSource.Util.bytes(user.getSalt()), getName() // realm name
		);
		return authenticationInfo;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
