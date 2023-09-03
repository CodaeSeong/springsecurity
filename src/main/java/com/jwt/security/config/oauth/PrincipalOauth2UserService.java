package com.jwt.security.config.oauth;

import com.jwt.security.config.auth.PrincipalDetails;
import com.jwt.security.config.oauth.provider.GoogleUserInfo;
import com.jwt.security.config.oauth.provider.NaverUserInfo;
import com.jwt.security.config.oauth.provider.OAuth2UserInfo;
import com.jwt.security.entity.EmployeeSecret;
import com.jwt.security.repository.EmployeeSecretRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmployeeSecretRepository employeeSecretRepository;

    // 구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration = " + userRequest.getClientRegistration()); //registrationId로 어떤 OAuth로 로그인 했는지 확인가능.
        System.out.println("getAccessToken = " + userRequest.getAccessToken());
        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴 (OAuth-Client라이브러리) -> AccessToken 요청
        // userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다.
        OAuth2User oAuth2User = super.loadUser((userRequest));
        System.out.println("getAttributes = " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {

            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {

            System.out.println("구글과 네이버 로그인만 지원합니다");
        }



        String provider = oAuth2UserInfo.getProvider(); //google
        String providerId = oAuth2UserInfo.getProviderId();
        String empCode = provider+"_"+providerId; // google_1231298712421
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";


        EmployeeSecret userEntity = employeeSecretRepository.findByEmpCode(empCode);
        if (userEntity == null){
            System.out.println("OAuth2 최초 로그인");
            userEntity = EmployeeSecret.builder()
                    .empCode(empCode)
                    .companyCode("COM-01")
                    .userPassword(password)
                    .role(role)
                    .build();

            employeeSecretRepository.save(userEntity);
        }else{
            System.out.println("이미 로그인 한적이 있습니다.");
        }

        return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
    }
}
