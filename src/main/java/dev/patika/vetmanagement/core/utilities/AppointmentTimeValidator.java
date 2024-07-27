package dev.patika.vetmanagement.core.utilities;

import java.time.LocalDateTime;

public class AppointmentTimeValidator {
    public static boolean isHourAligned(LocalDateTime appointmentTime) {
        return appointmentTime.getMinute() == 0 && appointmentTime.getSecond() == 0 && appointmentTime.getNano() == 0;
    }

}
