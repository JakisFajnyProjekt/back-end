package com.pl.token;

import com.pl.model.User;
import com.pl.security.authentication.RegisterRequest;
import jakarta.persistence.*;

    @Entity
    public class Token {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(unique = true)
        private String token;
        @Enumerated(EnumType.STRING)
        private TokenType tokenType = TokenType.BEARER;
        private boolean revoked;
        private boolean expired;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;
        public Token() {
        }
        public Token(String token, TokenType tokenType, User user) {
            this.token = token;
            this.tokenType = tokenType;
            this.user = user;
            this.revoked = false;
            this.expired = false;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
        public TokenType getTokenType() {
            return tokenType;
        }
        public void setTokenType(TokenType tokenType) {
            this.tokenType = tokenType;
        }
        public boolean isRevoked() {
            return revoked;
        }
        public void setRevoked(boolean revoked) {
            this.revoked = revoked;
        }
        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }
        public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }
        @Override
        public String toString() {
            return "Token{" +
                    "id=" + id +
                    ", token='" + token + '\'' +
                    ", tokenType=" + tokenType +
                    ", revoked=" + revoked +
                    ", expired=" + expired +
                    '}';
        }
        public static TokenBuilder builder() {
            return new TokenBuilder();
        }

        public static class TokenBuilder {
            private String token;
            private TokenType tokenType = TokenType.BEARER;
            private boolean revoked;
            private boolean expired;
            private User user;

            public TokenBuilder token(String token) {
                this.token = token;
                return this;
            }

            public TokenBuilder tokenType(TokenType tokenType) {
                this.tokenType = tokenType;
                return this;
            }

            public TokenBuilder revoked(boolean revoked) {
                this.revoked = revoked;
                return this;
            }

            public TokenBuilder expired(boolean expired) {
                this.expired = expired;
                return this;
            }

            public TokenBuilder user(User user) {
                this.user = user;
                return this;
            }

            public Token build() {
                Token token = new Token();
                token.token = this.token;
                token.tokenType = this.tokenType;
                token.revoked = this.revoked;
                token.expired = this.expired;
                token.user = this.user;
                return token;
            }
        }
    }
