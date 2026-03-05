package com.lbranchese.BookingSystem.infrastructure.persistence.reservation.command;

import com.lbranchese.BookingSystem.application.port.command.ReservationRepository;
import com.lbranchese.BookingSystem.domain.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SqlReservationRepository
        implements ReservationRepository {

    private final JdbcTemplate jdbc;

    public SqlReservationRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(Reservation reservation) {

        String sql = """
            INSERT INTO reservations
            (id, user_id, resource_id, date, status)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbc.update(
                sql,
                reservation.getId(),
                reservation.getUserId(),
                reservation.getResourceId(),
                reservation.getDate(),
                reservation.getStatus().name() // ← traducción
        );
    }

    @Override
    public boolean existsByResourceAndDate(
            UUID resourceId,
            LocalDate date
    ) {

        String sql = """
        SELECT COUNT(*) 
        FROM reservations
        WHERE resource_id = ?
          AND date = ?
          AND status = 'ACTIVE'
    """;

        Integer count = jdbc.queryForObject(
                sql,
                Integer.class,
                resourceId,
                date
        );

        return count != null && count > 0;
    }

    @Override
    public Optional<Reservation> findById(UUID id) {

        String sql = """
        SELECT id, user_id, resource_id, date, status
        FROM reservations
        WHERE id = ?
    """;

        return jdbc.query(sql, rs -> {

            if (!rs.next()) return Optional.empty();

            return Optional.of(
                    Reservation.rehydrate(
                            rs.getObject("id", UUID.class),
                            rs.getObject("user_id", UUID.class),
                            rs.getObject("resource_id", UUID.class),
                            rs.getObject("date", LocalDate.class),
                            Reservation.Estado.valueOf(
                                    rs.getString("status")
                            )
                    )
            );
        }, id);
    }
}