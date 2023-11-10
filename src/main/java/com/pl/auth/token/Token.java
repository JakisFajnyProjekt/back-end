package com.pl.auth.token;

import com.pl.model.User;
import jakarta.persistence.*;


//    @Table(name = "tokens")
@Entity
    public class Token {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(unique = true)
        private String token;
        @Enumerated(EnumType.STRING)
        private TokenType tokenType = TokenType.BEARER;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;
        public Token() {
        }
        public Token(String token, TokenType tokenType, User user) {
            this.token = token;
            this.tokenType = tokenType;
            this.user = user;
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
                    '}';
        }
        public static TokenBuilder builder() {
            return new TokenBuilder();
        }

        public static class TokenBuilder {
            private String token;
            private TokenType tokenType = TokenType.BEARER;
            private User user;

            public TokenBuilder token(String token) {
                this.token = token;
                return this;
            }

            public TokenBuilder tokenType(TokenType tokenType) {
                this.tokenType = tokenType;
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
                token.user = this.user;
                return token;
            }
        }
    }
