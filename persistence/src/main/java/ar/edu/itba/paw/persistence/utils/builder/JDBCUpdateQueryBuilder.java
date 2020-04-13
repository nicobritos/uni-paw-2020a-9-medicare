package ar.edu.itba.paw.persistence.utils.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class JDBCUpdateQueryBuilder extends JDBCQueryBuilder {
    public static final String DEFAULT_COLUMN_VALUE = "DEFAULT";

    private Map<String, String> values = new HashMap<>();
    private Collection<String> returning = new LinkedList<>();

    private JDBCWhereClauseBuilder whereClauseBuilder;
    private String table;

    public JDBCUpdateQueryBuilder update(String tableName) {
        this.table = tableName;
        return this;
    }

    public JDBCUpdateQueryBuilder update(String tableName, String alias) {
        return this.update(tableName + " AS " + alias);
    }

    public JDBCUpdateQueryBuilder value(String columnName, String paramName) {
        this.values.put(columnName, paramName);
        return this;
    }

    public JDBCUpdateQueryBuilder values(Map<String, String> columnNamesParameters) {
        this.values.putAll(columnNamesParameters);
        return this;
    }

    public JDBCUpdateQueryBuilder where(JDBCWhereClauseBuilder whereClauseBuilder) {
        this.whereClauseBuilder = whereClauseBuilder;
        return this;
    }

    public JDBCUpdateQueryBuilder returning(String columnName) {
        this.returning.add(columnName);
        return this;
    }

    public JDBCUpdateQueryBuilder returning(Collection<String> columnNames) {
        this.returning.addAll(columnNames);
        return this;
    }

    @Override
    public String getQueryAsString() {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        stringBuilder
                .append(this.table)
                .append(" SET ")
                .append(this.getValuesAsString());

        if (this.whereClauseBuilder != null) {
            stringBuilder
                    .append(" WHERE ")
                    .append(this.whereClauseBuilder.getClauseAsString());
        } else {
            throw new IllegalStateException("No where clause defined. Is this an error?");
        }

        if (this.returning.size() > 0) {
            stringBuilder
                    .append(" RETURNING ")
                    .append(this.joinStrings(this.returning));
        }

        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    protected String getValuesAsString() {
        StringBuilder keyStringBuilder = new StringBuilder();
        StringBuilder valueStringBuilder = new StringBuilder();

        for (Entry<String, String> pair : this.values.entrySet()) {
            keyStringBuilder.append(pair.getKey());
            valueStringBuilder.append(pair.getValue());
        }

        return " (" + keyStringBuilder.toString() + ") = (" + valueStringBuilder.toString() + ") ";
    }
}
