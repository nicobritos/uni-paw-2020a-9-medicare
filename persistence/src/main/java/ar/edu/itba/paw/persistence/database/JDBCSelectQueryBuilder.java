package ar.edu.itba.paw.persistence.database;

import java.util.LinkedList;
import java.util.List;

public class JDBCSelectQueryBuilder extends JDBCQueryBuilder {
    public enum OrderCriteria {
        ASC(" ASC "),
        DESC(" DESC ");

        private String criteria;

        OrderCriteria(String criteria) {
            this.criteria = criteria;
        }

        public String getCriteria() {
            return this.criteria;
        }

        @Override
        public String toString() {
            return this.criteria;
        }
    }

    public enum JoinType {
        INNER(""),
        LEFT(" LEFT OUTER "),
        RIGHT(" RIGHT OUTER "),
        FULL_OUTER(" FULL OUTER "),
        CROSS_OUTER(" CROSS OUTER ");

        private String joinType;

        JoinType(String joinType) {
            this.joinType = joinType;
        }

        public String getJoinType() {
            return this.joinType;
        }

        @Override
        public String toString() {
            return this.joinType;
        }
    }

    private List<String> columns = new LinkedList<>();
    private List<String> joins = new LinkedList<>();

    private JDBCWhereClauseBuilder whereClauseBuilder;
    private OrderCriteria orderCriteria;
    private boolean distinct;
    private String orderBy;
    private String table;
    private int limit;

    public JDBCSelectQueryBuilder select(String columnName) {
        if (this.columns.size() > 0) this.columns.clear();
        this.columns.add(columnName);
        return this;
    }

    public JDBCSelectQueryBuilder addSelect(String columnName) {
        this.columns.add(columnName);
        return this;
    }

    public JDBCSelectQueryBuilder from(String tableName) {
        this.table = tableName;
        return this;
    }

    public JDBCSelectQueryBuilder from(String tableName, String alias) {
        return this.from(tableName + " AS ");
    }

    public JDBCSelectQueryBuilder join(String table, String columnLeft, JDBCWhereClauseBuilder.Operation operation, String columnRight, JoinType joinType) {
        StringBuilder stringBuilder = new StringBuilder();

        if (joinType != null) stringBuilder.append(joinType.getJoinType());

        stringBuilder
                .append(table)
                .append(" ON ")
                .append(columnLeft)
                .append(operation.getOperation())
                .append(columnRight);

        this.joins.add(stringBuilder.toString());
        return this;
    }

    public JDBCSelectQueryBuilder orderBy(String columnName, OrderCriteria orderCriteria) {
        this.orderCriteria = orderCriteria;
        this.orderBy = columnName;
        return this;
    }

    public JDBCSelectQueryBuilder distinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public JDBCSelectQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public JDBCSelectQueryBuilder where(JDBCWhereClauseBuilder whereClauseBuilder) {
        this.whereClauseBuilder = whereClauseBuilder;
        return this;
    }

    @Override
    public String getQueryAsString() {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");

        if (this.distinct) stringBuilder.append("DISTINCT ");

        stringBuilder
                .append(this.joinStrings(this.columns))
                .append(" FROM ")
                .append(this.table);

        if (this.joins.size() > 0) {
            stringBuilder.append(this.joinStrings(this.joins));
        }

        if (this.whereClauseBuilder != null) {
            stringBuilder
                    .append(" WHERE ")
                    .append(this.whereClauseBuilder.getClauseAsString());
        }

        if (this.orderBy != null) {
            stringBuilder
                    .append(" ORDER BY ")
                    .append(this.orderBy);

            if (this.orderCriteria != null) stringBuilder.append(this.orderCriteria.getCriteria());
        }

        if (this.limit > 0) {
            stringBuilder
                    .append(" LIMIT ")
                    .append(this.limit);
        }

        stringBuilder.append(";");

        return stringBuilder.toString();
    }
}
