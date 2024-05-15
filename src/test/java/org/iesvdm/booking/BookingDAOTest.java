package org.iesvdm.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.List;
import java.time.LocalDate;
import java.util.*;

public class BookingDAOTest {

    private BookingDAO bookingDAO;
    private Map<String, BookingRequest> bookings;

    @BeforeEach
    public void setup() {
        bookings = new HashMap<>();
        bookingDAO = new BookingDAO(bookings);
    }

    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas (bookings) con la que
     * construyes el objeto BookingDAO bajo testeo.
     * Comprueba que cuando invocas bookingDAO.getAllBookingRequest
     * obtienes las 2 peticiones.
     */
    @Test
    void  getAllBookingRequestsTest() {
        BookingRequest request1 = new BookingRequest("1", LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 16), 4, false);
        BookingRequest request2 = new BookingRequest("2", LocalDate.of(2023, 5, 11), LocalDate.of(2023, 6, 10), 3, false);

        bookings.put("1", request1);
        bookings.put("2", request2);

        assertThat(bookingDAO.getAllBookingRequests()).isEqualTo(bookings.values());
    }

    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas mediante bookingDAO.save.
     * Comprueba que cuando invocas bookingDAO.getAllUUIDs
     * obtienes las UUIDs de las 2 peticiones guardadas.
     */
    @Test
    void getAllUUIDsTest() {
        BookingRequest request1 = new BookingRequest("1", LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 16), 4, false);
        BookingRequest request2 = new BookingRequest("2", LocalDate.of(2023, 5, 11), LocalDate.of(2023, 6, 10), 3, false);

        bookingDAO.save(request1);
        bookingDAO.save(request2);

        assertThat(bookingDAO.getAllUUIDs()).isEqualTo(bookings.keySet());
    }


    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas mediante bookingDAO.save.
     * Comprueba que cuando invocas bookingDAO.get con el UUID
     * obtienes las respectivas 2 peticiones guardadas.
     */
    @Test
    void getTest() {
        BookingRequest request1 = new BookingRequest("1", LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 16), 4, false);
        BookingRequest request2 = new BookingRequest("2", LocalDate.of(2023, 5, 11), LocalDate.of(2023, 6, 10), 3, false);

        String uuid = bookingDAO.save(request1);
        assertThat(bookingDAO.get(uuid)).isEqualTo(request1);

        uuid = bookingDAO.save(request2);
        assertThat(bookingDAO.get(uuid)).isEqualTo(request2);
    }

    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas mediante bookingDAO.save.
     * A continuación, borra la primera y comprueba
     * que se mantiene 1 reserva, la segunda guardada.
     */
    @Test
    void deleteTest() {
        BookingRequest request1 = new BookingRequest("1", LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 16), 4, false);
        BookingRequest request2 = new BookingRequest("2", LocalDate.of(2023, 5, 11), LocalDate.of(2023, 6, 10), 3, false);

        String uuid = bookingDAO.save(request1);
        bookingDAO.save(request2);

        bookingDAO.delete(uuid);

        assertThat(bookingDAO.getAllBookingRequests()).hasSize(1);
    }

    /**
     * Guarda 2 veces la misma petición de reserva (BookingRequest)
     * y demuestra que en la colección de bookings están repetidas
     * pero con UUID diferente
     *
     */
    @Test
    void saveTwiceSameBookingRequestTest() {
        BookingRequest request1 = new BookingRequest("1", LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 16), 4, false);
        bookingDAO.save(request1);
        bookingDAO.save(request1);
    }
}
