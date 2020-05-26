package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.ModelMetadata;
import ar.edu.itba.paw.models.Picture;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDaoImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:sql/schema.sql")
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoImplTest {
    private static final int STARTING_ID = 0;
    private static final String FIRST_NAME = "Nombre";
    private static final String SURNAME = "Apellido";
    private static final String EMAIL = "napellido@test.com";
    private static final String PHONE = "1123456789";
    private static final String PASSWORD = "pass1234";
    private static final int PROFILE_ID = STARTING_ID;
    private static final String TOKEN = "123";
    private static final String FIRST_NAME_2 = "Roberto";
    private static final String SURNAME_2 = "Rodriguez";
    private static final String EMAIL_2 = "napellido2@test2.com";
    private static final String PHONE_2 = "(011) 1123456789";
    private static final String PASSWORD_2 = "password1234";
    private static final int PROFILE_ID_2 = STARTING_ID + 1;
    private static final String MIME_TYPE = "image/svg+xml";
    private static final String PICTURE = "defaultProfilePic.svg";
    private static final Resource IMG = new ClassPathResource("img/" + PICTURE);
    private static final byte[] IMG_DATA = getImgData(IMG);
    private static final long IMG_SIZE = getImgSize(IMG);


    private static final String USERS_TABLE = "users";
    private static final String PICTURES_TABLE = "picture";

    @Autowired
    private UserDao userDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert userJdbcInsert;
    private SimpleJdbcInsert pictureJdbcInsert;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private DataSource ds;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(this.ds);
        this.userJdbcInsert = new SimpleJdbcInsert(this.ds)
                .withTableName(USERS_TABLE)
                .usingGeneratedKeyColumns("users_id");
        this.pictureJdbcInsert = new SimpleJdbcInsert(this.ds)
                .withTableName(PICTURES_TABLE)
                .usingGeneratedKeyColumns("picture_id");
    }

    /* ---------------------- FUNCIONES AUXILIARES ---------------------------------------------------------------- */

    private static byte[] getImgData(Resource img){
        try {
            return Files.readAllBytes(img.getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static long getImgSize(Resource img){
        try {
            return img.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void cleanAllTables() {
        this.jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
    }

    /**
     * Devuelve un User con
     * firstName = IMG_DATA
     * surname = SURNAME
     * password = PASSWORD
     * email = EMAIL
     * phone = PHONE
     * profileID = PROFILE_ID
     * token = null
     * tokenCreatedDate = null
     * id = STARTING_ID
     **/
    private User userModel() {
        User u = new User();
        try {
            u.setEmail(EMAIL);
            u.setPassword(PASSWORD);
            u.setFirstName(FIRST_NAME);
            u.setSurname(SURNAME);
            u.setPhone(PHONE);
            u.setProfilePicture(pictureModel());
            u.setToken(TOKEN);
            u.setTokenCreatedDate(null);
            u.setVerified(true);
            u.setId(STARTING_ID);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return u;
    }

    private Picture pictureModel(){
        Picture p = new Picture();
        p.setId(STARTING_ID);
        p.setMimeType(MIME_TYPE);
        p.setSize(IMG_SIZE);
        p.setData(IMG_DATA);
        p.setName(PICTURE);
        return p;
    }

    /** Inserta en la db la imagen con
     * data = IMG_DATA
     * mimeType = MIME_TYPE
     * name = NAME
     * size = IMG_SIZE
     **/
    private void insertPicture() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", IMG_DATA);
        map.put("mime_type", MIME_TYPE);
        map.put("size", IMG_SIZE);
        map.put("name", PICTURE);
        pictureJdbcInsert.execute(map);
    }

    /**
     * Inserta en la db el user con
     * firstName = IMG_DATA
     * surname = SURNAME
     * password = PASSWORD
     * email = EMAIL
     * phone = PHONE
     * profileID = PROFILE_ID
     * token = TOKEN
     * tokenCreatedDate = null
     **/
    private void insertUser() {
        insertPicture();
        Map<String, Object> map = new HashMap<>();
        map.put("first_name", FIRST_NAME);
        map.put("surname", SURNAME);
        map.put("password", PASSWORD);
        map.put("email", EMAIL);
        map.put("phone", PHONE);
        map.put("profile_id", PROFILE_ID);
        map.put("token", TOKEN);
        map.put("token_created_date", null);
        userJdbcInsert.execute(map);
    }

    /**
     * Inserta en la db el user con
     * firstName = FIRST_NAME
     * surname = SURNAME
     * password = PASSWORD
     * email = EMAIL
     * phone = PHONE
     * profileID = PROFILE_ID
     * token = null
     * tokenCreatedDate = null
     **/
    private void insertAnotherUser() {
        insertPicture();
        insertPicture();
        Map<String, Object> map = new HashMap<>();
        map.put("first_name", FIRST_NAME_2);
        map.put("surname", SURNAME_2);
        map.put("password", PASSWORD_2);
        map.put("email", EMAIL_2);
        map.put("phone", PHONE_2);
        map.put("profile_id", PROFILE_ID_2);
        map.put("token", null);
        map.put("token_created_date", null);
        userJdbcInsert.execute(map);
    }

    /* --------------------- MÉTODO: userDao.create(User) -------------------------------------------- */

    @Test
    public void testCreateUserSuccessfully() {
        // 1. Precondiciones
        cleanAllTables();
        insertPicture();
        User u = userModel();

        // 2. Ejercitar
        User user = this.userDao.create(u);

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, USERS_TABLE));
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(SURNAME, user.getSurname());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(PHONE, user.getPhone());
        assertEquals(pictureModel(), user.getProfilePicture());
    }

    @Test
    public void testCreateAnotherUserSuccessfully() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();
        User u = userModel();

        // 2. Ejercitar
        User user = this.userDao.create(u);

        // 3. Postcondiciones
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, USERS_TABLE));
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(SURNAME, user.getSurname());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(PHONE, user.getPhone());
        assertEquals(pictureModel(), user.getProfilePicture());
    }

    @Test
    public void testCreateUserNullFail() {
        // 1. Precondiciones
        cleanAllTables();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        User user = this.userDao.create(null);

        // 3. Postcondiciones
        // Que el metodo tire NullPointerException
    }

    @Test
    public void testCreateUserEmptyUserFail() {
        // 1. Precondiciones
        cleanAllTables();
        User u = new User();
        expectedException.expect(CoreMatchers.anyOf( // Falla si no se tira ninguna de las excepciones de la lista
                CoreMatchers.instanceOf(IllegalStateException.class), // Esta excepcion se tira si no tiene data // TODO: chequear esta excepcion (poco descriptiva)
                CoreMatchers.instanceOf(DataIntegrityViolationException.class) // Esta excepcion se tira si no tiene id // TODO: chequear esta excepcion (poco descriptiva)
        ));

        // 2. Ejercitar
        User user = this.userDao.create(u);

        // 3. Postcondiciones
        // Que el metodo tire IllegalStateException (no data) o DataIntegrityViolationException (no id), se hace esto porque depende de cual chequea primero.
    }

    @Test
    public void testCreateUserEmptyFirstNameFail() {
        // 1. Precondiciones
        cleanAllTables();
        User u = userModel();
        u.setFirstName(null);
        expectedException.expect(IllegalStateException.class); // TODO: chequear esta excepcion (poco descriptiva)

        // 2. Ejercitar
        User user = this.userDao.create(u);

        // 3. Postcondiciones
        // Que el metodo tire IllegalStateException
    }

    @Test
    public void testCreateUserEmptySurnameFail() {
        // 1. Precondiciones
        cleanAllTables();
        User u = userModel();
        u.setSurname(null);
        expectedException.expect(IllegalStateException.class); // TODO: chequear esta excepcion (poco descriptiva)

        // 2. Ejercitar
        User user = this.userDao.create(u);

        // 3. Postcondiciones
        // Que el metodo tire IllegalStateException
    }

    @Test
    public void testCreateUserEmptyEmailFail() {
        // 1. Precondiciones
        cleanAllTables();
        User u = userModel();
        u.setEmail(null);
        expectedException.expect(IllegalStateException.class); // TODO: chequear esta excepcion (poco descriptiva)

        // 2. Ejercitar
        User user = this.userDao.create(u);

        // 3. Postcondiciones
        // Que el metodo tire IllegalStateException
    }

    @Test
    public void testCreateUserEmptyPasswordFail() {
        // 1. Precondiciones
        cleanAllTables();
        User u = userModel();
        u.setPassword(null);
        expectedException.expect(IllegalStateException.class); // TODO: chequear esta excepcion (poco descriptiva)

        // 2. Ejercitar
        User user = this.userDao.create(u);

        // 3. Postcondiciones
        // Que el metodo tire IllegalStateException
    }

    /* --------------------- MÉTODO: userDao.findById(String) -------------------------------------------- */

    @Test
    public void testFindUserById() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        Optional<User> user = this.userDao.findById(STARTING_ID);

        // 3. Postcondiciones
        assertTrue(user.isPresent());
        assertEquals(FIRST_NAME, user.get().getFirstName());
    }

    @Test
    public void testFindUserByIdDoesntExist() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();

        // 2. Ejercitar
        Optional<User> user = this.userDao.findById(STARTING_ID + 1);

        // 3. Postcondiciones
        assertFalse(user.isPresent());
    }

    @Test
    public void testFindUserByIdNull() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();

        // 2. Ejercitar
        Optional<User> user = this.userDao.findById(null);

        // 3. Postcondiciones
        assertFalse(user.isPresent());
    }

    /* --------------------- MÉTODO: userDao.findByIds(Collection<String>) -------------------------------------------- */

    @Test
    public void testFindUserByIds() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        Collection<User> users = this.userDao.findByIds(Arrays.asList(STARTING_ID, STARTING_ID + 1));

        // 3. Postcondiciones
        assertNotNull(users);
        assertEquals(2, users.size());
        for (User u : users) {
            assertTrue(u.getId().equals(STARTING_ID) || u.getId().equals(STARTING_ID + 1));
        }
    }

    @Test
    public void testFindUserByIdsNotAllPresent() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();

        // 2. Ejercitar
        Collection<User> users = this.userDao.findByIds(Arrays.asList(STARTING_ID, STARTING_ID + 1));

        // 3. Postcondiciones
        assertNotNull(users);
        assertEquals(1, users.size());
        for (User u : users) {
            assertEquals(userModel(), u);
        }
    }

    @Test
    public void testFindUserByIdsDontExist() {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitar
        Collection<User> users = this.userDao.findByIds(Arrays.asList(STARTING_ID, STARTING_ID + 1));

        // 3. Postcondiciones
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    /* --------------------- MÉTODO: userDao.list() -------------------------------------------- */

    @Test
    public void testUserList() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        Collection<User> users = this.userDao.list();

        // 3. Postcondiciones
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testUserEmptyList() {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitar
        Collection<User> users = this.userDao.list();

        // 3. Postcondiciones
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    /* --------------------- MÉTODO: userDao.update(User) -------------------------------------------- */

    @Test
    public void testUserUpdate() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();
        User u = userModel();
        u.setFirstName("Armenia");

        // 2. Ejercitar
        this.userDao.update(u);

        // 3. Postcondiciones
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, USERS_TABLE, "first_name = 'Armenia'"));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, USERS_TABLE, "first_name = '" + FIRST_NAME + "'"));
    }

    @Test
    public void testUserUpdateNull() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        this.userDao.update(null);

        // 3. Postcondiciones
        // Que el metodo tire NullPointerException
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    @Test
    public void testUserUpdateNotExistentUser() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();
        User u = userModel();
        u.setId(STARTING_ID + 1);
        expectedException.expect(Exception.class);  // <-- TODO: Insert exception class here

        // 2. Ejercitar
        this.userDao.update(u); // TODO: NO HACE NADA, DEBERIA TIRAR EXCEPCION QUE NO EXISTE EL USER CON ESE ID

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    @Test
    public void testUserUpdateUserWithNullName() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        User u = userModel();
        u.setFirstName(null);
        expectedException.expect(IllegalStateException.class);

        // 2. Ejercitar
        this.userDao.update(u);

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
        assertEquals(userModel(), u);
    }

    @Test
    public void testUserUpdateUserWithNullId() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        User u = userModel();
        u.setId(null);
        expectedException.expect(Exception.class); // <-- TODO: Insert exception class here

        // 2. Ejercitar
        this.userDao.update(u); // TODO: NO HACE NADA, DEBERIA TIRAR EXCEPCION QUE DEBE TENER ID NOT NULL

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    /* --------------------- MÉTODO: userDao.remove(String id) -------------------------------------------- */

    @Test
    public void testUserRemoveById() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();

        // 2. Ejercitar
        this.userDao.remove(STARTING_ID);

        // 3. Postcondiciones
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    @Test
    public void testUserRemoveByIdNotExistent() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();

        // 2. Ejercitar
        this.userDao.remove(STARTING_ID + 1);

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    @Test
    public void testUserRemoveByNullId() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();

        // 2. Ejercitar
        this.userDao.remove((Integer) null);

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }
    /* --------------------- MÉTODO: userDao.remove(User) -------------------------------------------- */

    @Test
    public void testUserRemoveByModel() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        User u = userModel();

        // 2. Ejercitar
        this.userDao.remove(u);

        // 3. Postcondiciones
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    @Test
    public void testUserRemoveByModelNotExistent() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();
        User u = userModel();
        u.setId(STARTING_ID + 1);

        // 2. Ejercitar
        this.userDao.remove(u);

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    @Test
    public void testUserRemoveByNullModel() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        this.userDao.remove((User) null);

        // 3. Postcondiciones
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));
    }

    /* --------------------- MÉTODO: userDao.count() -------------------------------------------- */

    @Test
    public void testUserCount() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        ModelMetadata modelMetadata = this.userDao.count();

        // 3. Postcondiciones
        assertEquals(2, (long) modelMetadata.getCount()); // TODO: fix
        System.out.println(modelMetadata.getMax()); // No se que devuelve esto
        System.out.println(modelMetadata.getMin()); // No se que devuelve esto
    }

    @Test
    public void testUserCountEmptyTable() {
        // 1. Precondiciones
        cleanAllTables();

        // 2. Ejercitar
        ModelMetadata modelMetadata = this.userDao.count();

        // 3. Postcondiciones
        assertEquals(0, (long) modelMetadata.getCount()); // TODO: fix
        System.out.println(modelMetadata.getMax()); // No se que devuelve esto
        System.out.println(modelMetadata.getMin()); // No se que devuelve esto
    }

    /* --------------------- MÉTODO: userDao.existsEmail(String) -------------------------------------------- */

    @Test
    public void testUserExistsEmail() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        boolean existsEmail = this.userDao.existsEmail(EMAIL);

        // 3. Postcondiciones
        assertTrue(existsEmail);
    }

    @Test
    public void testUserDoesntExistsEmail() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();

        // 2. Ejercitar
        boolean existsEmail = this.userDao.existsEmail(EMAIL);

        // 3. Postcondiciones
        assertFalse(existsEmail);
    }

    /* --------------------- MÉTODO: userDao.existsToken(String) -------------------------------------------- */

    @Test
    public void testUserExistsToken() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        boolean existsToken = this.userDao.existsToken(TOKEN);

        // 3. Postcondiciones
        assertTrue(existsToken);
    }

    @Test
    public void testUserDoesntExistsToken() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();

        // 2. Ejercitar
        boolean existsToken = this.userDao.existsToken(TOKEN);

        // 3. Postcondiciones
        assertFalse(existsToken);
    }

    /* --------------------- MÉTODO: userDao.findByEmail(String) -------------------------------------------- */

    @Test
    public void testUserFindByEmail() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        Optional<User> userOptional = this.userDao.findByEmail(EMAIL);

        // 3. Postcondiciones
        assertTrue(userOptional.isPresent());
        assertEquals(EMAIL, userOptional.get().getEmail());
    }

    @Test
    public void testUserFindByEmailDoesntExists() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();

        // 2. Ejercitar
        Optional<User> userOptional = this.userDao.findByEmail(EMAIL);

        // 3. Postcondiciones
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void testUserFindByEmailNull() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        Optional<User> userOptional = this.userDao.findByEmail(null);

        // 3. Postcondiciones
        // metodo tira NullPointerException
        assertFalse(userOptional.isPresent());
    }

    /* --------------------- MÉTODO: userDao.findByToken(String) -------------------------------------------- */

    @Test
    public void testUserFindByToken() {
        // 1. Precondiciones
        cleanAllTables();
        insertUser();
        insertAnotherUser();

        // 2. Ejercitar
        Optional<User> userOptional = this.userDao.findByToken(TOKEN);

        // 3. Postcondiciones
        assertTrue(userOptional.isPresent());
        assertEquals(TOKEN, userOptional.get().getToken());
    }

    @Test
    public void testUserFindByTokenDoesntExists() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();

        // 2. Ejercitar
        Optional<User> userOptional = this.userDao.findByToken(TOKEN);

        // 3. Postcondiciones
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void testUserFindByTokenNull() {
        // 1. Precondiciones
        cleanAllTables();
        insertAnotherUser();
        expectedException.expect(NullPointerException.class);

        // 2. Ejercitar
        Optional<User> userOptional = this.userDao.findByToken(null);

        // 3. Postcondiciones
        // metodo tira NullPointerException
        assertFalse(userOptional.isPresent());
    }
}