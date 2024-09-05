package com.laofan.iantha.security;

import static com.laofan.iantha.security.SecurityUtils.AUTHORITIES_KEY;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Utility class for Spring Security.
 */
public final class FanSecurityUtils {

    public static String getCurrentIdent() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            return extractPrincipal(securityContext.getAuthentication()).getIdent() + "";
        } catch (Exception e) {
            return "-1";
        }
    }

    public static String getCurrentPhone() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return extractPrincipal(securityContext.getAuthentication()).getPhone();
    }

    public static Optional<FanUser> getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static FanUser extractPrincipal(Authentication authentication) {
        if (authentication.getPrincipal() instanceof FanUser) {
            return (FanUser) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            Object ident = jwt.getClaim("ident");
            return new FanUser(authentication.getName(), "", authentication.getAuthorities(), Long.parseLong(ident.toString()));
        } else {
            throw new BusinessException("token已失效，请重新登录..");
        }
    }
    /**获取当前用户的权限
     *
     * @return
     */
    //    public static List<AuthoritiesEnum> getCurrentUserAuthority() {
    //        SecurityContext securityContext = SecurityContextHolder.getContext();
    //        DaYouUser daYouUser= Optional.ofNullable(extractPrincipal(securityContext.getAuthentication())).get();
    //        List<AuthoritiesEnum> authoritiesEnums= new ArrayList<>();
    //        daYouUser.getAuthorities().stream().forEach(e->{
    //            authoritiesEnums.add(AuthoritiesEnum.valueOf(e.getAuthority()));
    //        });
    //        return authoritiesEnums;
    //    }
}
