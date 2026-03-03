package com.lbranchese.BookingSystem.application.port.query;

import com.lbranchese.BookingSystem.application.query.model.ReservationByResourceSummary;
import com.lbranchese.BookingSystem.application.query.model.ReservationByUserSummary;

import java.util.List;
import java.util.UUID;

public interface ReservationQueryRepository {

    List<ReservationByUserSummary> findByUserId(UUID userId);

    List<ReservationByResourceSummary> findByResourceId(UUID resourceId);

}
