package com.qt.mail.modules.sys.oauth2;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.entity.UserToken;
import com.qt.mail.modules.sys.service.ShiroService;

/**
 * 认证
 *
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      /*  //SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
        User user=(User) principals.getPrimaryPrincipal();
        String userId=user.getId();
       // Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);*/
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        
    	String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        UserToken tokenEntity = shiroService.queryByToken(accessToken);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //token失效
        try {
			if(tokenEntity == null || sdf.parse(tokenEntity.getExpireTime()).getTime() < System.currentTimeMillis()){
			    throw new IncorrectCredentialsException("token失效，请重新登录");
			}
		} catch (ParseException e) {
			 throw new IncorrectCredentialsException("token失效，请重新登录"); 
		}

        //查询用户信息
        User user = shiroService.queryUser(tokenEntity.getUserId());
        //账号锁定
        if(!user.getState().equals("0")){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
