package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.daos.generic.GenericSearchableDao;
import ar.edu.itba.paw.models.Country;
import ar.edu.itba.paw.models.Province;

import java.util.List;

public interface ProvinceDao extends GenericSearchableDao<Province, Integer> {
    List<Province> findByCountry(Country country);

    /**
     * Returns Provinces with a name similar to the one provided filtered out by Country.
     * The search is not case-sensitive nor exact
     *
     * @param name the province's name
     * @return a collection of matched provinces
     */
    List<Province> findByCountryAndName(Country country, String name);
}
