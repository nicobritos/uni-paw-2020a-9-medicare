package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Country;
import ar.edu.itba.paw.models.Locality;
import ar.edu.itba.paw.models.ModelMetadata;
import ar.edu.itba.paw.models.Province;
import ar.edu.itba.paw.persistence.LocalityDaoImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:sql/schema.sql")
@ContextConfiguration(classes = TestConfig.class)
public class LocalityDaoImplTest
{
    private static final String LOCALITY = "Capital Federal";
    private static final String PROVINCE = "Buenos Aires";
    private static final String LOCALITY_2 = "Palermo";

    private static final int STARTING_ID = 0;
    private static final String COUNTRY = "Argentina";
    private static final String COUNTRY_ID = "AR";

    private static final String LOCALITIES_TABLE = "system_locality";
    private static final String PROVINCES_TABLE = "system_province";
    private static final String COUNTRIES_TABLE = "system_country";


    private LocalityDaoImpl localityDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert localityJdbcInsert;
    private SimpleJdbcInsert provinceJdbcInsert;
    private SimpleJdbcInsert countryJdbcInsert;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private DataSource ds;

    @Before
    public void setUp(){
        this.localityDao = new LocalityDaoImpl(this.ds);
        this.jdbcTemplate = new JdbcTemplate(this.ds);
        this.localityJdbcInsert = new SimpleJdbcInsert(this.ds)
                .withTableName(LOCALITIES_TABLE)
                .usingGeneratedKeyColumns("locality_id");
        this.provinceJdbcInsert = new SimpleJdbcInsert(this.ds)
                .withTableName(PROVINCES_TABLE)
                .usingGeneratedKeyColumns("province_id");
        this.countryJdbcInsert = new SimpleJdbcInsert(this.ds)
                .withTableName(COUNTRIES_TABLE);
    }

    /* ---------------------- FUNCIONES AUXILIARES ---------------------------------------------------------------- */

    private void cleanAllTables(){
        this.jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
    }

    /** Devuelve una locality con id=STARTING_ID, name=PROVINCE y como country el devuelto en countryModel() **/
    private Locality localityModel(){
        Locality l = new Locality();
        l.setName(LOCALITY);
        l.setProvince(provinceModel());
        l.setId(STARTING_ID);
        return l;
    }

    /** Devuelve una province con id=STARTING_ID, name=PROVINCE y como country el devuelto en countryModel() **/
    private Province provinceModel(){
        Province p = new Province();
        p.setId(STARTING_ID);
        p.setCountry(countryModel());
        p.setName(PROVINCE);
        return p;
    }

    /** Devuelve un country con id=COUNTRY_ID y name=COUNTRY **/
    private Country countryModel(){
        Country c = new Country();
        c.setName(COUNTRY);
        c.setId(COUNTRY_ID);
        return c;
    }

    /** Inserta en la db el pais con id=COUNTRY_ID y name=COUNTRY **/
    private void insertCountry(){
        Map<String, Object> map = new HashMap<>();
        map.put("country_id", COUNTRY_ID);
        map.put("name", COUNTRY);
        countryJdbcInsert.execute(map);
    }

    /** Inserta en la db la provincia con country_id=COUNTRY_ID y name=PROVINCE **/
    private void insertProvince(){
        insertCountry();
        Map<String, Object> map = new HashMap<>();
        map.put("country_id", COUNTRY_ID);
        map.put("name", PROVINCE);
        provinceJdbcInsert.execute(map);
    }

    /** Inserta en la db la localidad con country_id=STARTING_ID y name=LOCALITY **/
    private void insertLocality(){
        insertProvince();
        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID);
        map.put("name", LOCALITY);
        localityJdbcInsert.execute(map);
    }

    private void insertAnotherLocality() {
        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID);
        map.put("name", LOCALITY_2);
        localityJdbcInsert.execute(map);
    }

    /* --------------------- MÉTODO: localityDao.create(Locality) -------------------------------------------- */

    @Test
    public void testCreateLocalitySuccessfully()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertProvince();
        Locality l = localityModel();

        // 2. Ejercitar
        Locality locality = this.localityDao.create(l);

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, LOCALITIES_TABLE));
        assertEquals(LOCALITY, locality.getName());
        assertEquals(provinceModel(), locality.getProvince());
    }

    @Test
    public void testCreateAnotherLocalitySuccessfully()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        Locality l = new Locality();
        l.setName(LOCALITY_2);
        l.setProvince(provinceModel());

        // 2. Ejercitar
        Locality locality = this.localityDao.create(l);

        // 3. Postcondiciones
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, LOCALITIES_TABLE));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, PROVINCES_TABLE));
        assertEquals(LOCALITY_2, locality.getName());
        assertEquals(provinceModel(), locality.getProvince());
    }

    @Test
    public void testCreateLocalityNullFail()
    {
        // 1. Precondiciones
        cleanAllTables();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        Locality locality = this.localityDao.create(null);

        // 3. Postcondiciones
        // Que el metodo tire NullPointerException
    }

    @Test
    public void testCreateLocalityEmptyLocalityFail()
    {
        // 1. Precondiciones
        cleanAllTables();
        Locality l = new Locality();
        expectedException.expect(IllegalStateException.class);

        // 2. Ejercitar
        Locality locality = this.localityDao.create(l);

        // 3. Postcondiciones
        // Que el metodo tire IllegalStateException (no name)
    }

    @Test
    public void testCreateLocalityEmptyProvinceFail() // TODO: Tirar un error si no se especifica country
    {
        // 1. Precondiciones
        cleanAllTables();
        Locality l = new Locality();
        l.setName(LOCALITY);
        expectedException.expect(DataIntegrityViolationException.class); // TODO: chequear esta excepcion (poco descriptiva)


        // 2. Ejercitar
        Locality locality = this.localityDao.create(l);

        // 3. Postcondiciones
        // Que el metodo tire DataIntegrityViolationException
    }

    @Test
    public void testCreateProvinceEmptyNameFail()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertProvince();
        Locality l = new Locality();
        l.setProvince(provinceModel());
        expectedException.expect(IllegalStateException.class); // TODO: chequear esta excepcion (poco descriptiva)

        // 2. Ejercitar
        Locality locality = this.localityDao.create(l);

        // 3. Postcondiciones
        // Que el metodo tire IllegalStateException
    }

    /* --------------------- MÉTODO: localityDao.findById(int) -------------------------------------------- */

    @Test
    public void testFindLocalityById()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        Optional<Locality> locality = this.localityDao.findById(STARTING_ID);

        // 3. Postcondiciones
        assertTrue(locality.isPresent());
        assertEquals(LOCALITY, locality.get().getName());
    }

    @Test
    public void testFindLocalityByIdDoesntExist()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();

        // 2. Ejercitar
        Optional<Locality> locality = this.localityDao.findById(STARTING_ID + 1);

        // 3. Postcondiciones
        assertFalse(locality.isPresent());
    }

    @Test
    public void testFindLocalityByIdNull()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertProvince();

        // 2. Ejercitar
        Optional<Locality> locality = this.localityDao.findById(null);

        // 3. Postcondiciones
        assertFalse(locality.isPresent());
    }

    /* --------------------- MÉTODO: localityDao.findByIds(Collection<Integer>) -------------------------------------------- */

    @Test
    public void testFindLocalitiesByIds()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.findByIds(Arrays.asList(STARTING_ID, STARTING_ID + 1));

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(2, localities.size());
        for (Locality l : localities){
            assertTrue(l.getId().equals(STARTING_ID) || l.getId().equals(STARTING_ID+1));
        }
    }

    @Test
    public void testFindLocalitiesByIdsNotAllPresent()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.findByIds(Arrays.asList(STARTING_ID, STARTING_ID+1));

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(1, localities.size());
        for (Locality l : localities){
            assertEquals(localityModel(), l);
        }
    }

    @Test
    public void testFindLocalitiesByIdsDoesntExist()
    {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.findByIds(Arrays.asList(STARTING_ID, STARTING_ID+1));

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    /* --------------------- MÉTODO: localityDao.findByName(String) -------------------------------------------- */

    @Test
    public void testFindLocalitiesByName()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        Map<String, Object> map = new HashMap<>();
        map.put("name", LOCALITY);
        map.put("province_id", STARTING_ID);
        provinceJdbcInsert.execute(map);

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.findByName(LOCALITY);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(1, localities.size());
        for (Locality l : localities){
            assertEquals(LOCALITY, l.getName());
        }
    }

    @Test
    public void testFindLocalitiesByNameDoesntExist()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.findByName(LOCALITY_2);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testFindLocalityByNameNull()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        this.localityDao.findByName(null);

        // 3. Postcondiciones
        // Que el metodo tire NullPointerException
    }

    @Test
    public void testFindLocalityByContainingName()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID);
        map.put("name", LOCALITY);
        localityJdbcInsert.execute(map);

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.findByName("capi");

        // 3. Postcondiciones
        assertEquals(2, localities.size());
    }

    /* --------------------- MÉTODO: localityDao.list() -------------------------------------------- */

    @Test
    public void testLocalityList()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.list();

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(2, localities.size());
    }

    @Test
    public void testLocalitysEmptyList()
    {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitar
        Collection<Locality> localities = this.localityDao.list();

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    /* --------------------- MÉTODO: localityDao.update(Locality) -------------------------------------------- */


    @Test
    public void testLocalityUpdate()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        Locality l = localityModel();
        l.setName("Centro");

        // 2. Ejercitar
        this.localityDao.update(l);

        // 3. Postcondiciones
        assertEquals(2,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
        assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, LOCALITIES_TABLE, "name = 'Centro'"));
        assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, LOCALITIES_TABLE, "name = '" + LOCALITY + "'"));

    }

    @Test
    public void testLocalityUpdateNull()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        this.localityDao.update(null);

        // 3. Postcondiciones
        // Que el metodo tire NullPointerException
        assertEquals(2,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    @Test
    public void testLocalityUpdateNotExistentLocality()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertProvince();
        insertAnotherLocality();
        expectedException.expect(Exception.class);  // <-- TODO: Insert exception class here

        // 2. Ejercitar
        this.localityDao.update(localityModel()); // TODO: NO HACE NADA, DEBERIA TIRAR EXCEPCION QUE NO EXISTE EL COUNTRY CON ESE ID

        // 3. Postcondiciones
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, PROVINCES_TABLE));
    }

    @Test
    public void testLocalityUpdateLocalityWithNullName()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        Locality l = new Locality();
        l.setProvince(provinceModel());
        l.setId(STARTING_ID);
        expectedException.expect(IllegalStateException.class);

        // 2. Ejercitar
        this.localityDao.update(l);

        // 3. Postcondiciones
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    @Test
    public void testLocalityUpdateLocalityWithNullId()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        Locality l = new Locality();
        l.setName(LOCALITY);
        expectedException.expect(Exception.class); // <-- TODO: Insert exception class here

        // 2. Ejercitar
        this.localityDao.update(l); // TODO: NO HACE NADA, DEBERIA TIRAR EXCEPCION QUE DEBE TENER ID NOT NULL

        // 3. Postcondiciones
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, COUNTRIES_TABLE));
    }

    /* --------------------- MÉTODO: localityDao.remove(int id) -------------------------------------------- */

    @Test
    public void testLocalityRemoveById()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();

        // 2. Ejercitar
        this.localityDao.remove(STARTING_ID);

        // 3. Postcondiciones
        assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    @Test
    public void testProvinceRemoveByIdNotExistent()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();

        // 2. Ejercitar
        this.localityDao.remove(STARTING_ID + 1);

        // 3. Postcondiciones
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    @Test
    public void testProvinceRemoveByNullId()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();

        // 2. Ejercitar
        this.localityDao.remove((Integer) null);

        // 3. Postcondiciones
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    /* --------------------- MÉTODO: localityDao.remove(Locality) -------------------------------------------- */

    @Test
    public void testProvinceRemoveByModel()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        Locality l = localityModel();

        // 2. Ejercitar
        this.localityDao.remove(l);

        // 3. Postcondiciones
        assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    @Test
    public void testLocalityRemoveByModelNotExistent()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertProvince();
        insertAnotherLocality();
        Locality l = localityModel();
        l.setId(STARTING_ID + 1);

        // 2. Ejercitar
        this.localityDao.remove(l);

        // 3. Postcondiciones
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    @Test
    public void testLocalityRemoveByNullModel()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        this.localityDao.remove((Locality) null);

        // 3. Postcondiciones
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCALITIES_TABLE));
    }

    /* --------------------- MÉTODO: countryDao.count() -------------------------------------------- */

    @Test
    public void testLocalityCount()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        ModelMetadata modelMetadata = this.localityDao.count();

        // 3. Postcondiciones
        assertEquals(2, (int) modelMetadata.getCount()); // TODO: fix
        System.out.println(modelMetadata.getMax()); // No se que devuelve esto
        System.out.println(modelMetadata.getMin()); // No se que devuelve esto
    }

    @Test
    public void testLocalityCountEmptyTable()
    {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitar
        ModelMetadata modelMetadata = this.localityDao.count();

        // 3. Postcondiciones
        assertEquals(2, (int) modelMetadata.getCount()); // TODO: fix
        System.out.println(modelMetadata.getMax()); // No se que devuelve esto
        System.out.println(modelMetadata.getMin()); // No se que devuelve esto
    }

    /* --------------------- MÉTODO: localityDao.findByField(field, value) -------------------------------------------- */

    @Test
    public void testLocalityFindByFieldName()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByField("name", LOCALITY);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(1, localities.size());
        for (Locality l : localities){
            assertEquals(LOCALITY, l.getName());
        }
    }

    @Test
    public void testLocalityFindByFieldId()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByField("locality_id", STARTING_ID);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(1, localities.size());
        for (Locality l : localities){
            assertEquals(STARTING_ID, (int) l.getId());
        }
    }

    @Test
    public void testLocalityFindByFieldProvince()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByField("province_id", STARTING_ID);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(2, localities.size());
        for (Locality l : localities){
            assertEquals(provinceModel(), l.getProvince());
        }
    }

    @Test
    public void testLocalityFindByFieldNull() {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByField("locality_id", null); //TODO: Deberia tirar NullPointer (?)

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testLocalityFindByFieldNotExistent() {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        expectedException.expect(BadSqlGrammarException.class);

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByField("locality_id_no_existo", STARTING_ID); //TODO: Deberia tirar otro tipo de error (?)

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testProvinceFindByFieldContentNotExistent()
    {
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByField("locality_id", -1); //TODO: Deberia tirar NullPointer (?)

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    /* --------------------- MÉTODO: countryDao.findByCountry(Country) -------------------------------------------- */

    @Test
    public void testLocalityFindByProvince(){
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        Map<String, Object> provinceMap = new HashMap<>();
        provinceMap.put("country_id", COUNTRY_ID);
        provinceMap.put("name", "Corrientes");
        provinceJdbcInsert.execute(provinceMap);

        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID + 1);
        map.put("name", "Santa Ana");
        localityJdbcInsert.execute(map);

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvince(provinceModel());

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(2, localities.size());
    }

    @Test
    public void testLocalityFindByProvinceDoesntExists(){
        // 1. Precondiciones
        cleanAllTables();
        insertCountry();

        Map<String, Object> provinceMap = new HashMap<>();
        provinceMap.put("country_id", COUNTRY_ID);
        provinceMap.put("name", "Corrientes");
        provinceJdbcInsert.execute(provinceMap);

        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID);
        map.put("name", "Santa Ana");
        localityJdbcInsert.execute(map);

        Province p = provinceModel();
        p.setId(STARTING_ID + 1);
        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvince(p);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testProvinceFindByCountryNull(){
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvince(null);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    /* --------------------- MÉTODO: localityDao.findByProvinceAndName(Province, String) -------------------------------------------- */

    @Test
    public void testLocalityFindByProvinceAndName(){
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        Map<String, Object> provinceMap = new HashMap<>();
        provinceMap.put("country_id", COUNTRY_ID);
        provinceMap.put("name", "Corrientes");
        provinceJdbcInsert.execute(provinceMap);

        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID + 1);
        map.put("name", "Santa Ana");
        localityJdbcInsert.execute(map);

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvinceAndName(provinceModel(), LOCALITY);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(1, localities.size());
        for (Locality l: localities){
            assertEquals(LOCALITY, l.getName());
            assertEquals(provinceModel(), l.getProvince());
        }
    }

    @Test
    public void testLocalityFindByProvinceAndNameProvinceDoesntExists(){
        // 1. Precondiciones
        cleanAllTables();
        insertCountry();

        Map<String, Object> provinceMap = new HashMap<>();
        provinceMap.put("country_id", COUNTRY_ID);
        provinceMap.put("name", "Corrientes");
        provinceJdbcInsert.execute(provinceMap);

        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID);
        map.put("name", "Santa Ana");
        localityJdbcInsert.execute(map);

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvinceAndName(provinceModel(), LOCALITY);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testProvinceFindByProvinceAndNameProvinceNull(){
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvinceAndName(null, LOCALITY);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testLocalitiesFindByProvinceAndNameNameDoesntExists(){
        // 1. Precondiciones
        cleanAllTables();
        insertCountry();

        Map<String, Object> provinceMap = new HashMap<>();
        provinceMap.put("country_id", COUNTRY_ID);
        provinceMap.put("name", "Corrientes");
        provinceJdbcInsert.execute(provinceMap);

        Map<String, Object> map = new HashMap<>();
        map.put("province_id", STARTING_ID);
        map.put("name", "Santa Ana");
        localityJdbcInsert.execute(map);

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvinceAndName(provinceModel(), LOCALITY);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testLocalityFindByProvinceAndNameNameNull(){
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvinceAndName(provinceModel(), null);

        // 3. Postcondiciones
        assertNotNull(localities);
        assertTrue(localities.isEmpty());
    }

    @Test
    public void testLocalityFindByProvinceAndNameNameContains(){
        // 1. Precondiciones
        cleanAllTables();
        insertLocality();
        insertAnotherLocality();

        // 2. Ejercitar
        List<Locality> localities = this.localityDao.findByProvinceAndName(provinceModel(), "pal");

        // 3. Postcondiciones
        assertNotNull(localities);
        assertEquals(1, localities.size());
        for (Locality l: localities){
            assertEquals(LOCALITY_2, l.getName());
            assertEquals(provinceModel(), l.getProvince());
        }
    }
}
