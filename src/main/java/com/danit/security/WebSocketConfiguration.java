package com.danit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.Objects;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;


@Configuration
@EnableWebSocketMessageBroker
@Order(HIGHEST_PRECEDENCE + 50)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

  @Autowired
  private UserDetailsService userDetailsService;

  private static final String MESSAGE_PREFIX = "/events";

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
    registry.addEndpoint("/socket").setAllowedOrigins("*");
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker(MESSAGE_PREFIX);
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
          String token = accessor.getNativeHeader("Authorization").get(0);

          if (Objects.nonNull(token)) {
            DecodedJWT decodedJwt;
            try {
              decodedJwt = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                  .build()
                  .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
            } catch (JWTVerificationException e) {
              e.printStackTrace();
              return null;
            }
            String user = decodedJwt.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(user);
            Principal principal = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
            System.out.println("Principal: " + principal.getName());
            accessor.setUser(principal);
            accessor.setLeaveMutable(true);
            return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
          }
        }
        return null;
      }
    });
  }
}