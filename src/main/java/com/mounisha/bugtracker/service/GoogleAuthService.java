
package com.mounisha.bugtracker.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthService {

    private final GoogleIdTokenVerifier verifier;

    public GoogleAuthService(
            @Value("${google.client-id}") String clientId) {

        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public GoogleIdToken.Payload verifyToken(String idToken)
            throws GeneralSecurityException, IOException {

        GoogleIdToken verifiedToken = verifier.verify(idToken);

        if (verifiedToken == null) {
            throw new IllegalArgumentException(
                    "Invalid Google ID token");
        }

        GoogleIdToken.Payload payload = verifiedToken.getPayload();

        if (!Boolean.TRUE.equals(payload.getEmailVerified())
                || payload.getEmail() == null
                || payload.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Google email is not verified");
        }

        return payload;
    }
}
