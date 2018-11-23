package ua.com.danit.security;

class SecurityConstants {

  static final String SECRET = "fs4SecretKey";
  static final long EXPIRATION_TIME = 864_000_000; // 10 days
  static final String TOKEN_PREFIX = "Bearer ";
  static final String HEADER_STRING = "Authorization";
  static final String SIGN_UP_URL = "/api/users/auth";

  private SecurityConstants() {
  }

}
