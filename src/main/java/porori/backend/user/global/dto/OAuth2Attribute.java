package porori.backend.user.global.dto;

import lombok.Builder;
import lombok.Getter;
import porori.backend.user.domain.user.domain.User;

import java.util.HashMap;
import java.util.Map;

import static porori.backend.user.domain.user.domain.UserConstant.Role.ROLE_USER;

@Getter
@Builder
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String appleId;

    public static OAuth2Attribute of(String provider, String attributeKey,
                                     Map<String, Object> attributes) {
        if ("apple".equals(provider)) {
            return ofApple(attributeKey, attributes);
        }
        throw new RuntimeException("Unsupported provider: " + provider);
    }

    private static OAuth2Attribute ofApple(String attributeKey,
                                           Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .appleId((String) attributes.get("sub"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .appleId(appleId)
                .role(ROLE_USER)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("name", name);
        map.put("email", email);
        map.put("appleId", appleId);

        return map;
    }
}

