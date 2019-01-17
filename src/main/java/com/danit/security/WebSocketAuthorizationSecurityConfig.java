package com.danit.security;

import com.danit.utils.WebSocketUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketAuthorizationSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

  private WebSocketUtils webSocketUtils;

  public WebSocketAuthorizationSecurityConfig(WebSocketUtils webSocketUtils) {
    this.webSocketUtils = webSocketUtils;
  }

  @Override
  protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
    messages
        .nullDestMatcher().permitAll()
        .simpSubscribeDestMatchers(webSocketUtils.getPrefix() + "/**").authenticated()
        .anyMessage().authenticated();
  }

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }
}
