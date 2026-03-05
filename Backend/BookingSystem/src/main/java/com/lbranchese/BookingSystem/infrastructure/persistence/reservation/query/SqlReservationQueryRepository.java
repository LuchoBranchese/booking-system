package com.lbranchese.BookingSystem.infrastructure.persistence.reservation.query;

import com.lbranchese.BookingSystem.application.port.query.ReservationQueryRepository;
import com.lbranchese.BookingSystem.application.query.model.ReservationByResourceSummary;
import com.lbranchese.BookingSystem.application.query.model.ReservationByUserSummary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class SqlReservationQueryRepository
        implements ReservationQueryRepository {

    private final JdbcTemplate jdbc;

    public SqlReservationQueryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<ReservationByUserSummary> findByUserId(UUID userId) {

        String sql = """
            SELECT id, resource_id, date, status
            FROM reservations
            WHERE user_id = ?
            ORDER BY date DESC
        """;

        return jdbc.query(
                sql,
                (rs, rowNum) ->
                        new ReservationByUserSummary(
                                rs.getObject("resource_id", UUID.class),
                                rs.getObject("date", LocalDate.class),
                                ReservationByUserSummary.Status.fromDb(rs.getString("status").toUpperCase())
                        ),
                userId
        );
    }

    @Override
    public List <ReservationByResourceSummary> findByResourceId(UUID resourceId) {
        String sql = """
            SELECT id, resource_id, date, status
            FROM reservations
            WHERE resource_id = ?
            ORDER BY date DESC
        """;

        return  jdbc.query(
                sql,
                (rs, rowNum) ->
                        new ReservationByResourceSummary(
                                rs.getObject("userId", UUID.class),
                                rs.getObject("date", LocalDate.class),
                                ReservationByResourceSummary.Status.fromDb(rs.getString("status").toUpperCase())
                        ),
                resourceId
        );
    }
}