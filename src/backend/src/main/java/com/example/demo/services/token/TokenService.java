package com.example.demo.services.token;

import com.example.demo.models.users.Token;
import com.example.demo.models.users.User;
import com.example.demo.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService{
    private static final int MAX_TOKENS = 3;
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    private final TokenRepository tokenRepository;

    @Override
    public boolean isMobileDevice(String userAgent){
        return userAgent.toLowerCase().contains("mobile");
    }

    @Transactional // Ensure that function is completed or not ==> roll back
    @Override
    public Token addToken(User user, String token, boolean isMobileDevice) {
        List<Token> userTokens = tokenRepository.findByUser(user);

        /* Check if total tokens is over MAX_TOKENS ==> Delete */
        int tokenCount = userTokens.size();
        if(tokenCount >= MAX_TOKENS){
            // If exit a token which is not com from a mobile
            boolean hasNonMobileToken = !userTokens.stream().allMatch(Token::isMobile);
            Token toDeleteToken;

            if(hasNonMobileToken){
                toDeleteToken = userTokens.stream()
                        .filter(userToken -> !userToken.isMobile())
                        .findFirst()
                        .orElse(userTokens.get(0));
            } else{
                toDeleteToken = userTokens.get(0);
            }

            tokenRepository.delete(toDeleteToken);
        }

        long expirationInSeconds = expiration;
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expirationInSeconds);

        /* Create a new Token */
        Token newToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType("Bearer")
                .expirationDate(expirationDateTime)
                .isMobile(isMobileDevice)
                .revoked(false)
                .expired(false)
                .build();
        newToken.setRefreshToken(UUID.randomUUID().toString());
        newToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));

        tokenRepository.save(newToken);
        return newToken;
    }



}
