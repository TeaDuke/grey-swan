package com.chernytskyi.university.wad.service;

import com.chernytskyi.university.wad.domain.RefreshToken;
import com.chernytskyi.university.wad.domain.User;
import com.chernytskyi.university.wad.domain.dto.TokenResponse;
import com.chernytskyi.university.wad.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {

    // TODO: take this values from application.yaml
    private static final Duration ACCESS_TTL = Duration.ofMinutes(15);
    private static final Duration REFRESH_TTL = Duration.ofDays(7);

    @Value("${app.name}")
    private String issuer;

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom random = new SecureRandom();

    @Autowired
    public TokenService(JwtEncoder jwtEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public TokenResponse issuePair(User user) {
        return new TokenResponse(createAccessToken(user), createRefreshToken(user, UUID.randomUUID()));
    }

//    @Transactional
    public TokenResponse refresh(String rawRefreshToken) {
        RefreshToken old = refreshTokenRepository.findByTokenHash(hash(rawRefreshToken))
                .orElseThrow(TokenService::invalid);

        if (old.isRevoked()) {
            refreshTokenRepository.revokeFamily(old.getFamilyId());
            throw invalid();
        }
        if (old.getExpiresAt().isBefore(Instant.now())) throw invalid();

        User user = old.getUser();

        old.setRevoked(true);
        return new TokenResponse(createAccessToken(user), createRefreshToken(user, old.getFamilyId()));
    }


    @Transactional
    public void revoke(String rawRefreshToken) {
        refreshTokenRepository.findByTokenHash(hash(rawRefreshToken))
                .ifPresent(t -> refreshTokenRepository.revokeFamily(t.getFamilyId()));
    }

    @Transactional
    public void revokeAll(User user) {
        refreshTokenRepository.revokeAllForUser(user.getId());
    }

    private String createAccessToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(ACCESS_TTL))
                .subject(user.getUsername())
                .claim("roles", List.of(user.getRole().name()))
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    private String createRefreshToken(User user, UUID familyId) {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String raw = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        refreshTokenRepository.save(new RefreshToken(user, hash(raw), familyId, Instant.now().plus(REFRESH_TTL)));
        return raw;
    }

    private static String hash(String raw) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static BadCredentialsException invalid() {
        return new BadCredentialsException("Invalid refresh token");
    }

}


























