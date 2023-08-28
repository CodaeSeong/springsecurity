package com.jwt.security.config;

import com.jwt.security.config.auth.PrincipalDetails;
import com.jwt.security.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.*;


@Component
public class MySimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }


    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }
        String userId = authentication.getName();

        String provider = userId.split("_")[0];

        System.out.println("============================================= = " + provider);



        String id = userId;
        String email = null;
        String name = null;


        if(provider.equals("naver")){

            Map<String, String> attributes = (Map<String, String>) ((PrincipalDetails)authentication.getPrincipal()).getAttributes().get("response");
            email = attributes.get("email");
            name = attributes.get("name");

        } else if(provider.equals("google")) {
            Map<String, Object> attributes = ((PrincipalDetails) authentication.getPrincipal()).getAttributes();
            email = attributes.get("email").toString();
            name = attributes.get("name").toString();
        }




        List<String> attributesList = new ArrayList<>();

        attributesList.add(id);
        attributesList.add(email);
        attributesList.add(name);

        String jwtToken = jwtTokenProvider.createToken(id,attributesList);

        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("user_id",id);
        queryParams.add("user_email",email);
        queryParams.add("user_name", URLEncoder.encode(name,"UTF-8"));
//        queryParams.add("token",jwtToken);

        String uri = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(3000)
                .path("/loginprocess")
                .build().toString();

        System.out.println("uri = " + uri);
        response.setHeader("Content-Type", "application/json");


//        redirectStrategy.sendRedirect(request, response, uri);

        ResponseCookie cookie = ResponseCookie.from("jwtToken",jwtToken)
                .httpOnly(false)
                .secure(true)
                .path("/")      // path
                .maxAge(Duration.ofMinutes(3))
                .sameSite("None")  // sameSite
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.sendRedirect("http://localhost:3000/loginprocess");
    }


    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "http://localhost:3000/sample-page");
        roleTargetUrlMap.put("ROLE_ADMIN", "http://localhost:3000/sample-page");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}