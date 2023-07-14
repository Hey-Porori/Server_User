package porori.backend.user.domain.user.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.http.client.fluent.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import porori.backend.user.global.exception.ConnException;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Optional;


@Service
public class UserAppleService {

    private static final String APPLE_JWK_URL = "https://appleid.apple.com/auth/keys";
    private static final String APPLE_ISSUER = "https://appleid.apple.com";

    public Optional<String> verifyIdentityToken(String identityToken) {
        try {

            DecodedJWT jwt = JWT.decode(identityToken);

            String jwkJsonStr = Request.Get(APPLE_JWK_URL).execute().returnContent().asString(StandardCharsets.UTF_8);
            JSONObject jwkJson = new JSONObject(jwkJsonStr);
            JSONArray keys = jwkJson.getJSONArray("keys");

            for (int i = 0; i < keys.length(); i++) {
                JSONObject key = keys.getJSONObject(i);
                if (key.getString("kid").equals(jwt.getKeyId())) {

                    BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.getString("n")));
                    BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.getString("e")));
                    RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));


                    JWTVerifier verifier = JWT.require(Algorithm.RSA256(publicKey, null))
                            .withIssuer(APPLE_ISSUER)
                            .build();
                    DecodedJWT verifiedJwt = verifier.verify(identityToken);

                    return Optional.ofNullable(verifiedJwt.getSubject());
                }
            }
        } catch (IOException e) {
            throw new ConnException();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
