package com.lbranchese.BookingSystem.infrastructure.persistence.resource;

import com.lbranchese.BookingSystem.application.port.command.ResourceRepository;
import com.lbranchese.BookingSystem.domain.model.Resource;
import com.lbranchese.BookingSystem.domain.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SqlResourceRepository implements ResourceRepository {

    private final JdbcTemplate jdbc;

    public SqlResourceRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Resource> findById(UUID resourceId) {

        String sql = """
            SELECT id, status
            FROM resources
            WHERE id = ?
        """;


        return jdbc.query(sql, (rs) ->
                {
                    if (!rs.next()) return Optional.empty();

                    return Optional.of(
                            Resource.rehydrate(
                                    rs.getObject("id", UUID.class),
                                    Resource.Status.valueOf(rs.getString("status"))
                            )

                    );
                },
                resourceId
        );
    }
}