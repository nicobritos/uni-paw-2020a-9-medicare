package ar.edu.itba.paw.webapp.controller.rest;

import ar.edu.itba.paw.interfaces.services.LocalityService;
import ar.edu.itba.paw.interfaces.services.StaffService;
import ar.edu.itba.paw.interfaces.services.StaffSpecialtyService;
import ar.edu.itba.paw.models.Locality;
import ar.edu.itba.paw.models.Paginator;
import ar.edu.itba.paw.models.Staff;
import ar.edu.itba.paw.models.StaffSpecialty;
import ar.edu.itba.paw.webapp.media_types.ErrorMIME;
import ar.edu.itba.paw.webapp.media_types.StaffMIME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.*;
import java.util.stream.Collectors;

@Path("/staffs")
@Component
public class StaffController extends GenericRestController {
    @Autowired
    private StaffSpecialtyService staffSpecialtyService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private LocalityService localityService;

    // TODO: i18n
    @GET
    @Produces({StaffMIME.GET_LIST, ErrorMIME.ERROR})
    public Response getStaffs(
            @Context HttpHeaders httpheaders,
            @Context UriInfo uriInfo,
            @QueryParam("name") String name,
            @QueryParam("specialties") String specialties,
            @QueryParam("localities") String localities,
            @QueryParam(PAGINATOR_PAGE_QUERY) Integer page,
            @QueryParam(PAGINATOR_PER_PAGE_QUERY) Integer perPage)
    {
        this.assertAcceptedTypes(httpheaders, StaffMIME.GET_LIST);

        // TODO: <= o < ??
        if (page != null) {
            if (page <= 0)
                return this.error(Status.BAD_REQUEST.getStatusCode(), Status.BAD_REQUEST.toString());
        } else {
            page = 1;
        }
        if (perPage != null) {
            if (perPage <= 0)
                return this.error(Status.BAD_REQUEST.getStatusCode(), Status.BAD_REQUEST.toString());
        } else {
            perPage = PAGINATOR_PER_PAGE_DEFAULT;
        }

        Collection<StaffSpecialty> searchedSpecialties = this.staffSpecialtyService.findByIds(this.parseListAsInteger(specialties));
        Collection<Locality> searchedLocalities = this.localityService.findByIds(this.parseListAsInteger(localities));

        Paginator<Staff> staffPaginator;
        if (name != null && !(name = name.trim()).equals("")) {
            Set<String> words = new HashSet<>(Arrays.asList(name.split(" ")));
            staffPaginator = this.staffService.findBy(words, words, null, searchedSpecialties, searchedLocalities, page, perPage);
        } else {
            staffPaginator = this.staffService.findBy((String) null, null, null, searchedSpecialties, searchedLocalities, page, perPage);
        }

        return this.createPaginatorResponse(staffPaginator, uriInfo).build();
    }

    @GET
    @Path("/{id}")
    @Produces({StaffMIME.GET, ErrorMIME.ERROR})
    public Response getStaff(
            @Context HttpHeaders httpheaders,
            @PathParam("id") Integer id)
    {
        this.assertAcceptedTypes(httpheaders, StaffMIME.GET);

        if (id == null)
            return this.error(Status.BAD_REQUEST.getStatusCode(), Status.BAD_REQUEST.toString());

        Optional<Staff> staffOptional = this.staffService.findById(id);
        if (!staffOptional.isPresent())
            return this.error(Status.NOT_FOUND.getStatusCode(), Status.NOT_FOUND.toString());

        return Response.ok(staffOptional.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({StaffMIME.GET, ErrorMIME.ERROR})
    @Consumes(StaffMIME.UPDATE)
    public Response updateStaff(
            Staff staff,
            @Context HttpHeaders httpheaders,
            @PathParam("id") Integer id)
    {
        this.assertAcceptedTypes(httpheaders, StaffMIME.GET);

        if (id == null || staff.getStaffSpecialties().isEmpty())
            return this.error(Status.BAD_REQUEST.getStatusCode(), Status.BAD_REQUEST.toString());

        Optional<Staff> staffOptional = this.staffService.findById(id);
        if (!staffOptional.isPresent())
            return this.error(Status.NOT_FOUND.getStatusCode(), Status.NOT_FOUND.toString());

        Collection<StaffSpecialty> staffSpecialties = this.staffSpecialtyService.findByIds(
                staff.getStaffSpecialties()
                        .stream()
                        .map(StaffSpecialty::getId)
                        .collect(Collectors.toList())
        ); // TODO: Is this necessary?
        if (staffSpecialties.size() != staff.getStaffSpecialties().size())
            return this.error(Status.BAD_REQUEST.getStatusCode(), Status.BAD_REQUEST.toString());

        Staff savedStaff = staffOptional.get();
        savedStaff.setEmail(staff.getEmail());
        savedStaff.setPhone(staff.getPhone());
        savedStaff.setStaffSpecialties(staffSpecialties);

        this.staffService.update(savedStaff);

        return Response.ok(staffOptional.get()).build();
    }

    private Set<Integer> parseListAsInteger(String list) {
        Set<Integer> specialtiesIds = new HashSet<>();
        if (list != null) {
            // split strings to get all items and create the list
            for (String s : list.split(",")) {
                try {
                    int id = Integer.parseInt(s);
                    if (id >= 0) {
                        specialtiesIds.add(id);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return specialtiesIds;
    }
}
