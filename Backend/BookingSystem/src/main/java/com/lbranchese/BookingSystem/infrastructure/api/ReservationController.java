package com.lbranchese.BookingSystem.infrastructure.api;

import com.lbranchese.BookingSystem.application.command.dto.CreateReservationRequest;
import com.lbranchese.BookingSystem.application.command.dto.CreateReservationResponse;
import com.lbranchese.BookingSystem.application.command.usecase.CancelReservation;
import com.lbranchese.BookingSystem.application.command.usecase.CreateReservation;
import com.lbranchese.BookingSystem.application.query.model.ReservationByResourceSummary;
import com.lbranchese.BookingSystem.application.query.model.ReservationByUserSummary;
import com.lbranchese.BookingSystem.application.query.usecase.GetReservationsByResource;
import com.lbranchese.BookingSystem.application.query.usecase.GetReservationsByUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final CreateReservation createReservation;
    private final CancelReservation cancelReservation;
    private final GetReservationsByUser getReservationsByUser;
    private final GetReservationsByResource getReservationsByResource;

    public ReservationController(
            CreateReservation createReservation,
            CancelReservation cancelReservation,
            GetReservationsByUser getReservationsByUser,
            GetReservationsByResource getReservationsByResource
    ) {
        this.createReservation = createReservation;
        this.cancelReservation = cancelReservation;
        this.getReservationsByUser = getReservationsByUser;
        this.getReservationsByResource = getReservationsByResource;
    }

    @PostMapping
    public ResponseEntity<CreateReservationResponse> create(@RequestBody CreateReservationRequest request) {
        UUID reservationId = createReservation.execute(
                request.getUserId(),
                request.getResourceId(),
                request.getDate(),
                LocalDate.now()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateReservationResponse(reservationId));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancel(
            @PathVariable UUID reservationId,
            @RequestParam UUID userId
    ) {
        cancelReservation.execute(userId, reservationId, LocalDate.now());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-user/{userId}")
    public List<ReservationByUserSummary> getByUser(@PathVariable UUID userId) {
        return getReservationsByUser.execute(userId);
    }

    @GetMapping("/by-resource/{resourceId}")
    public List<ReservationByResourceSummary> getByResource(@PathVariable UUID resourceId) {
        return getReservationsByResource.execute(resourceId);
    }
}

