package com.lbranchese.BookingSystem.infrastructure.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=none"
})
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupDatabase() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservations");
        jdbcTemplate.execute("DROP TABLE IF EXISTS resources");
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");

        jdbcTemplate.execute("""
                CREATE TABLE users(
                    id UUID PRIMARY KEY,
                    penalized_until DATE,
                    rol VARCHAR(50)
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE resources(
                    id UUID PRIMARY KEY,
                    status VARCHAR(50)
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE reservations(
                    id UUID PRIMARY KEY,
                    user_id UUID,
                    resource_id UUID,
                    date DATE,
                    status VARCHAR(50)
                )
                """);
    }

    @Test
    void createReservation_endToEnd_shouldPersistReservation() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        LocalDate reservationDate = LocalDate.now().plusDays(1);

        jdbcTemplate.update(
                "INSERT INTO users (id, penalized_until, rol) VALUES (?, ?, ?)",
                userId,
                null,
                "USER"
        );

        jdbcTemplate.update(
                "INSERT INTO resources (id, status) VALUES (?, ?)",
                resourceId,
                "ACTIVE"
        );

        String requestBody = """
                {
                  "userId": "%s",
                  "resourceId": "%s",
                  "date": "%s"
                }
                """.formatted(userId, resourceId, reservationDate);

        mockMvc.perform(
                        post("/reservations")
                                .contentType(APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reservationId").isNotEmpty());

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservations WHERE user_id = ? AND resource_id = ? AND date = ?",
                Integer.class,
                userId,
                resourceId,
                reservationDate
        );

        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1);
    }
}

