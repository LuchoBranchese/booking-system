package com.lbranchese.BookingSystem.infrastructure.persistence.user;

import com.lbranchese.BookingSystem.application.port.command.UserRepository;
import com.lbranchese.BookingSystem.domain.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SqlUserRepository implements UserRepository {

    private final JdbcTemplate jdbc;

    public SqlUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<User> findById(UUID userId) {

        String sql = """
            SELECT id, penalized_until, rol
            FROM users
            WHERE id = ?
        """;

        return jdbc.query(sql, (rs) -> {

            if (!rs.next()) return Optional.empty();

            return Optional.of(
                    User.rehydrate(
                            rs.getObject("id", UUID.class),
                            rs.getObject("penalized_until", LocalDate.class),
                            User.Role.valueOf(rs.getString("rol"))
                    )

            );
            }, userId);}

//    @Override
//    public void save (User user) {
//        String sql = """
//            INSERT INTO users
//            (id, penalized_until, rol)
//            VALUES (?, ?, ?)
//        """;
//
//        jdbc.update(
//                sql,
//                user.getId(),
//                user.getBlockedUntil(),
//                user.getRole().name()
//        );
//    }
}