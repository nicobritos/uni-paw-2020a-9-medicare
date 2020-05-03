package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.services.generic.GenericService;
import ar.edu.itba.paw.models.*;

import java.util.List;

public interface WorkdayService extends GenericService<Workday, Integer> {
    List<Workday> findByUser(User user);

    List<Workday> findByStaff(Staff staff);

    List<Workday> findByStaff(Staff staff, WorkdayDay day);

    boolean isStaffWorking(Staff staff, AppointmentTimeSlot timeSlot);
}
