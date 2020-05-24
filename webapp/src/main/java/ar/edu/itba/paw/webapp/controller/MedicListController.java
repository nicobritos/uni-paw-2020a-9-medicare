package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediCareException;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.LocalityService;
import ar.edu.itba.paw.interfaces.services.StaffService;
import ar.edu.itba.paw.interfaces.services.StaffSpecialtyService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.controller.utils.GenericController;
import ar.edu.itba.paw.webapp.controller.utils.JsonResponse;
import ar.edu.itba.paw.webapp.form.RequestTimeslotForm;
import ar.edu.itba.paw.webapp.transformer.AppointmentTimeSlotTransformer;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.*;

@Controller
public class MedicListController extends GenericController {
    private static final long MAX_DAYS_APPOINTMENTS = 7;
    @Autowired
    StaffService staffService;
    @Autowired
    StaffSpecialtyService specialityService;
    @Autowired
    LocalityService localityService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentTimeSlotTransformer appointmentTimeSlotTransformer;

    @RequestMapping("/mediclist")
    public ModelAndView medicList(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "specialties", required = false) String specialties, @RequestParam(value = "localities", required = false) String localities) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (name != null)
            params.add("name", name);
        if (specialties != null)
            params.add("specialties", specialties);
        if (localities != null)
            params.add("localities", localities);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/mediclist/1")
                .queryParams(params);
        return new ModelAndView("redirect:" + uriBuilder.toUriString());
    }

    @RequestMapping(value = "/mediclist/{page}")
    public ModelAndView medicsList(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "specialties", required = false) String specialties, @RequestParam(value = "localities", required = false) String localities, @PathVariable("page") int page) {
        if (page <= 0) {
            return medicList(name, specialties, localities);
        }

        //get modelandview from medicList.jsp
        final ModelAndView mav = new ModelAndView("medicList");
        //staff variable that will be passed to the jsp
        Collection<Staff> staffList;

        Set<StaffSpecialty> searchedSpecialties = new HashSet<>();

        if (specialties != null) {
            // split strings to get all specialties used in search
            // and create the search parameter
            for (String s : specialties.split(",")) {
                try {
                    StaffSpecialty specialty = new StaffSpecialty();
                    specialty.setId(Integer.parseInt(s));
                    if (specialty.getId() >= 0) {
                        searchedSpecialties.add(specialty);
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        Set<Locality> searchedLocalities = new HashSet<>();

        if (localities != null) {
            // split strings to get all specialties used in search
            // and create the search parameter
            for (String s : localities.split(",")) {
                try {
                    Locality locality = new Locality();
                    locality.setId(Integer.parseInt(s));
                    if (locality.getId() >= 0) {
                        searchedLocalities.add(locality);
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        Paginator<Staff> staffPaginator;
        if (name != null && !(name = name.trim()).equals("")) {
            Set<String> words = new HashSet<>(Arrays.asList(name.split(" ")));
            staffPaginator = this.staffService.findBy(words, words, null, searchedSpecialties, searchedLocalities, page);
        } else {
            staffPaginator = this.staffService.findBy((String) null, null, null, searchedSpecialties, searchedLocalities, page);
        }

        Collection<StaffSpecialty> specialtiesList = this.specialityService.list();
        Collection<Locality> localitiesList = this.localityService.list();

        // pass objects to model and view
        Optional<User> user = getUser();
        mav.addObject("user", user);
        if (user.isPresent() && isStaff()) {
            mav.addObject("staffs", staffService.findByUser(user.get()));
        }
        mav.addObject("searchedLocalities", searchedLocalities);
        mav.addObject("searchedSpecialties", searchedSpecialties);
        mav.addObject("staff", staffPaginator.getModels());
        mav.addObject("paginator", staffPaginator);
        mav.addObject("specialties", specialtiesList);
        mav.addObject("localities", localitiesList);
        mav.addObject("name", name);
        mav.addObject("selSpeciality", specialties);
        mav.addObject("selLocality", localities);

        return mav;
    }

    @RequestMapping("/appointment/{id}/{week}")
    public ModelAndView appointment(@PathVariable("id") final int id, @PathVariable("week") final int week) {
        Optional<Staff> staff = staffService.findById(id);
        if (!staff.isPresent()) {
            return new ModelAndView("error/404"); //todo: throw status code instead of this
        }
        ModelAndView mav = new ModelAndView();

        DateTime today = DateTime.now();
        today = today.plusWeeks(week);
        DateTime monday = today.minusDays(today.getDayOfWeek() - 1);

        mav.addObject("today", today);
        mav.addObject("monday", monday);
        Optional<User> user = getUser();
        mav.addObject("user", user);
        if (user.isPresent() && isStaff()) {
            mav.addObject("staffs", staffService.findByUser(user.get()));
        }
        mav.addObject("staff", staff.get());

        List<AppointmentTimeSlot> timeSlots = this.appointmentService.findAvailableTimeslots(staff.get(), monday, monday.plusDays(6).withTime(23, 59, 59, 999));
        List<List<AppointmentTimeSlot>> weekslots = new LinkedList<>();
        for (int i = 0; i <= 7; i++) {
            weekslots.add(new LinkedList<>());
        }
        for (AppointmentTimeSlot timeSlot : timeSlots) {
            if (timeSlot.getDate().getDayOfWeek() < 1 && timeSlot.getDate().getDayOfWeek() > 7) {
                weekslots.get(0).add(timeSlot);
            } else {
                weekslots.get(timeSlot.getDate().getDayOfWeek()).add(timeSlot);
            }
        }
        mav.addObject("weekSlots", weekslots);
        mav.setViewName("selectTurno");
        return mav;
    }

    @RequestMapping(value = "/timeslots/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse timeslots(
            @PathVariable("id") final int id,
            @Valid @RequestBody RequestTimeslotForm form,
            final BindingResult errors
    ) {
        return this.formatJsonResponse(errors, () -> {
            Optional<Staff> staff = this.staffService.findById(id);
            if (!staff.isPresent()) {
                throw new MediCareException("No existe el staff solicitado");
            }
            DateTime dateFrom = new DateTime(form.getFromYear(), form.getFromMonth(), form.getFromDay(), 0, 0);
            DateTime dateTo = new DateTime(form.getToYear(), form.getToMonth(), form.getToDay(), 23, 59, 59, 999);
            long daysBetween = Days.daysBetween(dateFrom.toLocalDate(), dateTo.toLocalDate()).getDays();
            if (daysBetween > MAX_DAYS_APPOINTMENTS || dateTo.isBefore(dateFrom)) {
                throw new MediCareException("Fechas invalidas");
            }

            return this.appointmentTimeSlotTransformer.transform(this.appointmentService.findAvailableTimeslots(
                    staff.get(),
                    dateFrom,
                    dateTo
            ));
        });
    }
}