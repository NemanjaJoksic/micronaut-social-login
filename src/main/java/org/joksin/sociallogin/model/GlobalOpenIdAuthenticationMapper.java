package org.joksin.sociallogin.model;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.config.AuthenticationModeConfiguration;
import io.micronaut.security.oauth2.configuration.OpenIdAdditionalClaimsConfiguration;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.DefaultOpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;

@Singleton
@Replaces(DefaultOpenIdAuthenticationMapper.class)
public class GlobalOpenIdAuthenticationMapper extends DefaultOpenIdAuthenticationMapper {

  public GlobalOpenIdAuthenticationMapper(
      OpenIdAdditionalClaimsConfiguration openIdAdditionalClaimsConfiguration,
      AuthenticationModeConfiguration authenticationModeConfiguration) {
    super(openIdAdditionalClaimsConfiguration, authenticationModeConfiguration);
  }

  @Override
  public Publisher<AuthenticationResponse> createAuthenticationResponse(
      String providerName,
      OpenIdTokenResponse tokenResponse,
      OpenIdClaims openIdClaims,
      State state) {

    return Mono.from(
            super.createAuthenticationResponse(providerName, tokenResponse, openIdClaims, state))
        .map(
            authenticationResponse ->
                authenticationResponse
                    .getAuthentication()
                    .map(
                        authentication ->
                            AuthenticationResponse.success(
                                authentication.getName(),
                                List.of("USER", "ADMIN"),
                                authentication.getAttributes()))
                    .orElse(authenticationResponse));
  }
}
