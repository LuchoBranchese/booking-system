package com.lbranchese.BookingSystem.infrastructure.persistence.reservation.query;

import com.lbranchese.BookingSystem.application.port.query.ReservationQueryRepository;
import com.lbranchese.BookingSystem.application.query.model.ReservationByUserSummary;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class JdbcReservationQueryRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationQueryRepository reservationQueryRepository;

    @Test
    void shouldReturnReservationsByUser() {

        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();

        jdbcTemplate.update("""
            INSERT INTO reservations(id, user_id, resource_id, date, status)
            VALUES (?, ?, ?, ?, ?)
        """,
                reservationId,
                userId,
                resourceId,
                LocalDate.now().plusDays(1),
                "ACTIVE"
        );

        List<ReservationByUserSummary> result =
                reservationQueryRepository.findByUserId(userId);

        assertEquals(1, result.size());

        ReservationByUserSummary reservation = result.get(0);

        assertEquals(resourceId, reservation.getResourceId());
    }
}