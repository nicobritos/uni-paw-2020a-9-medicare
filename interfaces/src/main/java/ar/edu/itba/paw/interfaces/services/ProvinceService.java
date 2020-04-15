package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.services.generic.GenericService;
import ar.edu.itba.paw.models.Country;
import ar.edu.itba.paw.models.Locality;
import ar.edu.itba.paw.models.Province;

import java.util.Collection;

public interface ProvinceService extends GenericService<Province, Integer> {
    Collection<Province> findByCountry(Country country);

    /**
     * Returns Provinces with a name similar to the one provided filtered out by Country.
     * The search is not case-sensitive nor exact
     * @param name the province's name
     * @return a collection of matched provinces
     */
    Collection<Province> findByCountryAndName(Country country, String name);

    void addLocality(Province province, Locality locality);

    void addLocalities(Province province, Collection<Locality> localities);
}