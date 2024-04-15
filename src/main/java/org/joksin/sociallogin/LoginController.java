package org.joksin.sociallogin;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Controller
public class LoginController {

  @Value("${micronaut.security.token.cookie.cookie-name:test}")
  private String cookieName;

  @Get("/")
  @Secured(SecurityRule.IS_ANONYMOUS)
  public HttpResponse<?> home(HttpRequest<?> request) throws URISyntaxException {
    var jwtCookie = request.getCookies().get(cookieName);

    if (Objects.nonNull(jwtCookie)) {
      System.out.println("User is already logged in");
      return HttpResponse.redirect(new URI("http://localhost:8080/fe/home"))
          .status(HttpStatus.FOUND);
    } else {
      System.out.println("User not logged in");
      return HttpResponse.redirect(new URI("/login"))
          .status(HttpStatus.FOUND)
          .contentType(MediaType.TEXT_HTML);
    }
  }

  @Get("/login")
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Produces(value = MediaType.TEXT_HTML)
  public String login() {
    return """
            <!DOCTYPE html>
                <html>
                <body>

                <h2><a href="/oauth/login/google">Login with Google</a></h2>

                </body>
                </html>
            """;
  }
}
