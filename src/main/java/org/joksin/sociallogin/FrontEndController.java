package org.joksin.sociallogin;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@Controller
public class FrontEndController {

  @Get("/fe")
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Produces(value = MediaType.TEXT_HTML)
  public String home(Authentication authentication) {
    return String.format(
        """
        <!DOCTYPE html>
            <html>
            <body>

            <h2>Hello, %s</h2>

            </body>
            </html>
        """,
        authentication.getName());
  }
}
