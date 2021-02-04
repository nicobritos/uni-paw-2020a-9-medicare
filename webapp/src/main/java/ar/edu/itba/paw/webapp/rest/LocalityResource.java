package ar.edu.itba.paw.webapp.rest;

import ar.edu.itba.paw.interfaces.services.CountryService;
import ar.edu.itba.paw.interfaces.services.LocalityService;
import ar.edu.itba.paw.interfaces.services.ProvinceService;
import ar.edu.itba.paw.models.Country;
import ar.edu.itba.paw.models.Locality;
import ar.edu.itba.paw.models.Province;
import ar.edu.itba.paw.webapp.media_types.ErrorMIME;
import ar.edu.itba.paw.webapp.media_types.LocalityMIME;
import ar.edu.itba.paw.webapp.media_types.MIMEHelper;
import ar.edu.itba.paw.webapp.models.error.ErrorConstants;
import ar.edu.itba.paw.webapp.rest.utils.GenericResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Component
public class LocalityResource extends GenericResource {
    @Autowired
    private CountryService countryService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private LocalityService localityService;

    @GET
    @Path("/countries/{countryId}/provinces/{provinceId}/localities")
    @Produces({LocalityMIME.GET_LIST, ErrorMIME.ERROR})
    public Response getCollection(
            @Context HttpHeaders httpheaders,
            @PathParam("provinceId") Integer provinceId,
            @PathParam("countryId") String countryId) {
        MIMEHelper.assertServerType(httpheaders, LocalityMIME.GET_LIST);

        Collection<Locality> localities;
        Optional<Country> country = this.countryService.findById(countryId);
        if (!country.isPresent()) {
            throw this.unprocessableEntity(ErrorConstants.PROVINCE_GET_NONEXISTENT_COUNTRY);
        }

        Optional<Province> province = this.provinceService.findByCountryAndId(country.get(), provinceId);
        if (!province.isPresent()) {
            throw this.unprocessableEntity(ErrorConstants.LOCALITY_GET_NONEXISTENT_PROVINCE);
        }

        localities = this.localityService.findByProvince(province.get());

        return Response
                .ok()
                .entity(localities)
                .type(LocalityMIME.GET_LIST)
                .build();
    }

    @GET
    @Path("/localities")
    @Produces({LocalityMIME.GET_LIST, ErrorMIME.ERROR})
    public Response getAllCollection(@Context HttpHeaders httpheaders) {
        MIMEHelper.assertServerType(httpheaders, LocalityMIME.GET_LIST);

        Collection<Locality> localities = this.localityService.list();

        return Response
                .ok()
                .entity(localities)
                .type(LocalityMIME.GET_LIST)
                .build();
    }

    @GET
    @Path("/countries/{countryId}/provinces/{provinceId}/localities/{id}")
    @Produces({LocalityMIME.GET, ErrorMIME.ERROR})
    public Response getEntity(
            @Context HttpHeaders httpheaders,
            @PathParam("provinceId") Integer provinceId,
            @PathParam("countryId") String countryId,
            @PathParam("id") Integer id) {
        MIMEHelper.assertServerType(httpheaders, LocalityMIME.GET);

        if (id == null) throw this.missingPathParams();

        Optional<Country> country = this.countryService.findById(countryId);
        if (!country.isPresent()) {
            throw this.unprocessableEntity(ErrorConstants.PROVINCE_GET_NONEXISTENT_COUNTRY);
        }

        Optional<Province> province = this.provinceService.findByCountryAndId(country.get(), provinceId);
        if (!province.isPresent()) {
            throw this.unprocessableEntity(ErrorConstants.LOCALITY_GET_NONEXISTENT_PROVINCE);
        }

        Optional<Locality> localityOptional = this.localityService.findByProvinceAndId(province.get(), id);
        if (!localityOptional.isPresent()) throw this.notFound();

        return Response.ok(localityOptional.get()).type(LocalityMIME.GET).build();
    }

    @GET
    @Path("/localities/{id}")
    @Produces({LocalityMIME.GET, ErrorMIME.ERROR})
    public Response getEntityById(
            @Context HttpHeaders httpheaders,
            @PathParam("id") Integer id) {
        MIMEHelper.assertServerType(httpheaders, LocalityMIME.GET);

        if (id == null) throw this.missingPathParams();

        Optional<Locality> localityOptional = this.localityService.findById(id);
        if (!localityOptional.isPresent()) throw this.notFound();

        return Response.ok(localityOptional.get()).type(LocalityMIME.GET).build();
    }
}
