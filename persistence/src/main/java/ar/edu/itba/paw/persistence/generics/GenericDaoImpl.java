package ar.edu.itba.paw.persistence.generics;

import ar.edu.itba.paw.interfaces.daos.generic.GenericDao;
import ar.edu.itba.paw.models.GenericModel;
import ar.edu.itba.paw.persistence.utils.Pair;
import ar.edu.itba.paw.persistence.utils.ReflectionGetterSetter;
import ar.edu.itba.paw.persistence.utils.builder.*;
import ar.edu.itba.paw.persistence.utils.builder.JDBCWhereClauseBuilder.ColumnTransformer;
import ar.edu.itba.paw.persistence.utils.builder.JDBCWhereClauseBuilder.Operation;
import ar.edu.itba.paw.persistenceAnnotations.Column;
import ar.edu.itba.paw.persistenceAnnotations.Table;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.*;
import java.util.Map.Entry;

/**
 * This provides a generic DAO abstract class
 * @param <M> the DAO model type
 * @param <I> the Model's id type
 */
public abstract class GenericDaoImpl<M extends GenericModel<I>, I> implements GenericDao<M, I> {
    protected NamedParameterJdbcTemplate jdbcTemplate;
    private String tableName;
    private String primaryKeyName;

    public GenericDaoImpl(DataSource dataSource, Class<M> mClass) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        if (mClass.isAnnotationPresent(Table.class)) {
            Table table = mClass.getAnnotation(Table.class);
            this.tableName = table.name();
            this.primaryKeyName = table.primaryKey();
        }
    }

    @Override
    public M findById(I id) {
        JDBCQueryBuilder queryBuilder = new JDBCSelectQueryBuilder()
                .select(JDBCSelectQueryBuilder.ALL)
                .from(this.getTableName())
                .where(new JDBCWhereClauseBuilder()
                        .where(this.formatColumnFromName(this.getIdColumnName()), Operation.EQ, ":id")
                );

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("id", id);

        List<M> list = this.query(queryBuilder.getQueryAsString(), args);
        return list.size() > 0 ? list.get(0) : null;
    }

    // TODO
    @Override
    public M create(M model) {
        Map<String, Pair<String, Object>> columnsArgumentValue = this.getModelColumnsArgumentValue(model);

        Map<String, String> columnsArguments = new HashMap<>();
        Map<String, Object> argumentsValues = new HashMap<>();
        for (Entry<String, Pair<String, Object>> columnArgumentValue : columnsArgumentValue.entrySet()) {
            columnsArguments.put(columnArgumentValue.getKey(), columnArgumentValue.getValue().getLeft());
            argumentsValues.put(columnArgumentValue.getValue().getLeft(), columnArgumentValue.getValue().getRight());
        }

        JDBCQueryBuilder queryBuilder = new JDBCInsertQueryBuilder()
                .into(this.getTableName())
                .values(columnsArguments)
                .returning(JDBCSelectQueryBuilder.ALL);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValues(argumentsValues);

        return this.querySingle(queryBuilder.getQueryAsString(), parameterSource);
    }

    // TODO
    // TODO: How to know what fields to update
    @Override
    public void save(M model) {

    }

    @Override
    public void remove(M model) {
        JDBCQueryBuilder queryBuilder = new JDBCDeleteQueryBuilder()
                .from(this.getTableName())
                .where(new JDBCWhereClauseBuilder()
                        .where(this.formatColumnFromName(this.getIdColumnName()), Operation.EQ, ":id")
                );

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", model.getId());

        this.query(queryBuilder.getQueryAsString(), parameterSource);
    }

    @Override
    public Collection<M> list() {
        JDBCQueryBuilder queryBuilder = new JDBCSelectQueryBuilder()
                .select(JDBCSelectQueryBuilder.ALL)
                .from(this.getTableName());

        return this.query(queryBuilder.getQueryAsString());
    }

    protected List<M> findByFieldIgnoreCase(String columnName, Operation operation, String value) {
        JDBCQueryBuilder queryBuilder = new JDBCSelectQueryBuilder()
                .select(JDBCSelectQueryBuilder.ALL)
                .from(this.getTableName())
                .where(new JDBCWhereClauseBuilder()
                        .where(this.formatColumnFromName(columnName), operation, ":argument", ColumnTransformer.LOWER)
                );

        value = value.toLowerCase();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("argument", value);

        return this.query(queryBuilder.getQueryAsString(), parameterSource);
    }

    protected List<M> findByField(String columnName, Operation operation, Object value) {
        JDBCQueryBuilder queryBuilder = new JDBCSelectQueryBuilder()
                .select(JDBCSelectQueryBuilder.ALL)
                .from(this.getTableName())
                .where(new JDBCWhereClauseBuilder()
                        .where(this.formatColumnFromName(columnName), operation, ":argument")
                );

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("argument", value);

        return this.query(queryBuilder.getQueryAsString(), parameterSource);
    }

    protected List<M> findByFieldIn(String columnName, Collection<?> values) {
        Map<String, Object> parameters = new HashMap<>();
        int i = 0;
        for (Object value : values) {
            parameters.put("_" + i, value);
            i++;
        }

        JDBCQueryBuilder queryBuilder = new JDBCSelectQueryBuilder()
                .select(JDBCSelectQueryBuilder.ALL)
                .from(this.getTableName())
                .where(new JDBCWhereClauseBuilder()
                        .in(this.formatColumnFromName(columnName), parameters.keySet())
                );

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValues(parameters);

        return this.query(queryBuilder.getQueryAsString(), parameterSource);
    }

    protected List<M> query(String query) {
        System.out.println(query);
        return this.jdbcTemplate.query(query, this.getRowMapper());
    }

    protected List<M> query(String query, MapSqlParameterSource args) {
        return this.jdbcTemplate.query(query, args, this.getRowMapper());
    }

    protected M querySingle(String query) {
        List<M> list = this.jdbcTemplate.query(query, this.getRowMapper());
        return list.size() > 0 ? list.get(0) : null;
    }

    protected M querySingle(String query, MapSqlParameterSource args) {
        List<M> list = this.jdbcTemplate.query(query, args, this.getRowMapper());
        return list.size() > 0 ? list.get(0) : null;
    }

    protected String getTableAlias() {
        return this.getTableName();
    }

    protected String formatColumnFromName(String columnName) {
        return formatColumnFromName(columnName, this.getTableName());
    }

    protected String formatColumnFromAlias(String columnName) {
        return formatColumnFromName(columnName, this.getTableAlias());
    }

    protected abstract RowMapper<M> getRowMapper();

    protected String getTableName() {
        return this.tableName;
    }

    protected String getIdColumnName() {
        return this.primaryKeyName;
    }

    protected Map<String, Pair<String, Object>> getModelColumnsArgumentValue(M model) {
        Map<String, Pair<String, Object>> map = new HashMap<>();

        ReflectionGetterSetter.iterateValues(model, Column.class, (field, o) -> {
            Column column = field.getAnnotation(Column.class);
            // TODO: Process one to one, many to one and many to many
            map.put(column.name(), new Pair<>(":_r_" + column.name(), o));
        });

        return map;
    }

    protected static String formatColumnFromName(String columnName, String tableName) {
        return tableName + "." + columnName;
    }

    protected static <M> M hydrate(Class<M> mClass, ResultSet resultSet) {
        return hydrate(mClass, resultSet, getTableNameFromModel(mClass), new LinkedList<>());
    }

    protected static <M> M hydrate(Class<M> mClass, ResultSet resultSet, String tableName, Collection<Class<?>> classesToAvoid) {
        M m;
        try {
            m = mClass.newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }

        try {
            System.out.println(resultSet.getMetaData().getColumnName(1));
            System.out.println(resultSet.getMetaData().getColumnName(2));
            System.out.println(resultSet.getMetaData().getColumnName(3));
            System.out.println(resultSet.getMetaData().getColumnName(4));
            System.out.println(resultSet.getMetaData().getColumnName(5));
            System.out.println(resultSet.getMetaData().getColumnName(6));
            System.out.println(resultSet.getMetaData().getColumnName(7));
//            ResultSetMetaData rsmd = resultSet.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                System.out.println("asdas");
                System.out.println(resultSet.getString(1));
//                for (int i = 1; i <= columnsNumber; i++) {
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = resultSet.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//                System.out.println("");
            }
        } catch (Exception e) {
            System.err.println("shit");
        }
        return m;

//        classesToAvoid.add(mClass);
//        ReflectionGetterSetter.iterateFields(mClass, Column.class, field -> {
//            if (!classesToAvoid.contains(field.getDeclaringClass())) {
//                Column column = field.getAnnotation(Column.class);
//                try {
//                    if (column.relation() != TableRelation.NULL) {
//                        // Esta columna es de relacion, tengo que ver como la meto
//                        switch (column.relation()) {
//                            case ONE_TO_ONE:
//                                Object o = hydrate(field.getDeclaringClass(), resultSet, getTableNameFromModel(field.getDeclaringClass()), classesToAvoid);
//                                ReflectionGetterSetter.set(m, field, o);
//                                break;
//                            case ONE_TO_MANY:
//                                break;
//                            case MANY_TO_ONE:
//                                break;
//                            case MANY_TO_MANY:
//                                break;
//                        }
//                    } else {
//                        ReflectionGetterSetter.set(m, field, resultSet.getObject(formatColumnFromName(column.name(), tableName)));
//                    }
//                } catch (SQLException e) {
//                    // TODO
//                    e.printStackTrace();
//                }
//            }
//        });
//        return m;
    }

    protected static <M> String getTableNameFromModel(Class<M> mClass) {
        if (mClass.isAnnotationPresent(Table.class)) {
            return mClass.getAnnotation(Table.class).name();
        }
        return null;
    }
}
