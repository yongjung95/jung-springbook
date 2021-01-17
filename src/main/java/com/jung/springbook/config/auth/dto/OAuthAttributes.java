package com.jung.springbook.config.auth.dto;

import com.jung.springbook.domain.user.Role;
import com.jung.springbook.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * (1) of() 는
 * OAuth2User 에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나를 반환해야한다.
 *
 * (2) toEntity() 는
 * User 엔티티를 생성한다.
 * OAuthAttributes 에서 엔티티를 생성하는 시점은 처음 가입할때이다.
 * 가입할 때의 기본권한을 GUEST 로 주기 위해서 role 빌더값에는 Role.GUEST 를 사용한다.
 *
 * (3) response 는
 * 스프링 시큐리티에선 하위 필드를 명시할 수 없는데, 최상위 필드들만 user_name 으로 지정이 가능하다.
 * 하지만 네이버에 요청했을때 리턴값 최상위 필드는 resultCode, message, response 이다.
 * 이러기 때문에 스프링 시큐리티에서 인식 가능한 필드는 저 3개 중에 골라야한다.
 */
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) { // (1)
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        if("kakao".equals(registrationId)){
            return ofKakao("id",attributes);
        }


        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        System.out.println("구글 로그인 " +userNameAttributeName );
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response"); // (3)
        System.out.println("네이버 로그인 " +userNameAttributeName );
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account"); // (3)
        Map<String, Object> profile = (Map<String, Object>) response.get("profile"); // (3)
        System.out.println("카카오 로그인 " +userNameAttributeName );
        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) profile.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() { // (2)
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
