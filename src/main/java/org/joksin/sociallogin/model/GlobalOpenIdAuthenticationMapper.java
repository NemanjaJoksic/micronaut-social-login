package org.joksin.sociallogin.model;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.DefaultOpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.Map;

//@Singleton
@Replaces(DefaultOpenIdAuthenticationMapper.class)
public class GlobalOpenIdAuthenticationMapper implements OpenIdAuthenticationMapper {

  @Override
  public Publisher<AuthenticationResponse> createAuthenticationResponse(
      String providerName,
      OpenIdTokenResponse tokenResponse,
      OpenIdClaims openIdClaims,
      State state) {

    String username;
    AuthenticatedUser authenticatedUser;

    if (providerName.equals("google")) {
      var claims = openIdClaims.getClaims();
      authenticatedUser =
          new AuthenticatedUser(
              (String) claims.get("sub"),
              (String) claims.get("email"),
              (String) claims.get("name"),
              (String) claims.get("given_name"),
              (String) claims.get("family_name"),
              "google");
      username = (String) claims.get("name");
    } else {
      throw new RuntimeException("Provider " + providerName + " is not supported");
    }

    return Publishers.just(
        AuthenticationResponse.success(username, Map.of("user", authenticatedUser)));
  }
}
