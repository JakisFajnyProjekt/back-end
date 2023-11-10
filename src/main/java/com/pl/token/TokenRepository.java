package com.pl.token;

import com.pl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Modifying
    @Transactional
    @Query(value = """
  delete from Token t where t.user.id = :id
  """)
    Optional<Token> deleteTokenByUserId(@Param ("id") Long id);
    @Modifying
    @Transactional
    @Query(value = """
    delete from Token t where t = :token
    """)
    void deleteToken(@Param("token") Token token);
    Optional<Token> findByToken(String token);
    Optional<Token> findByUser(User user);

}
