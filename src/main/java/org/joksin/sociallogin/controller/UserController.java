package org.joksin.sociallogin.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.util.Map;

@Controller
public class UserController {

  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Get("/api/users/me")
  public Map<String, Object> getMe(Authentication authentication) {
    return authentication.getAttributes();
  }
}
