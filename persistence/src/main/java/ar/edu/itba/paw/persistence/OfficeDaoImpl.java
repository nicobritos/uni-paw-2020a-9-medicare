package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.OfficeDao;
import ar.edu.itba.paw.models.Country;
import ar.edu.itba.paw.models.Office;
import ar.edu.itba.paw.persistence.generics.GenericSearchableDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Repository
public class OfficeDaoImpl extends GenericSearchableDaoImpl<Office, Integer> implements OfficeDao {
    private final RowMapper<Office> rowMapper = (resultSet, rowNum) -> this.hydrate(resultSet);
    public static final String TABLE_NAME = getTableNameFromModel(Office.class);

    @Autowired
    public OfficeDaoImpl(DataSource dataSource) {
        super(dataSource, Office.class);
    }

    @Override
    public Collection<Office> findByCountry(Country country) { // TODO: implement
        return null;
    }

    @Override
    protected RowMapper<Office> getRowMapper() {
        return rowMapper;
    }
}
