package org.joksin.sociallogin;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import java.util.Map;

@Controller
public class FrontEndController {

  @Get("/fe/home")
  @Secured(SecurityRule.IS_AUTHENTICATED)
  public Map<String, Object> feHome(Authentication authentication) {
    return authentication.getAttributes();
  }
}
