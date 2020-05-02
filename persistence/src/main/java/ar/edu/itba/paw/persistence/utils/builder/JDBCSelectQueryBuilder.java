package ar.edu.itba.paw.persistence.utils.builder;

import ar.edu.itba.paw.persistence.utils.builder.JDBCWhereClauseBuilder.Operation;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class JDBCSelectQueryBuilder extends JDBCQueryBuilder {
    public static final String ALL = " * ";

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
        INNER(" "),
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
    private boolean selectAll;
    private boolean distinct;
    private String orderBy;
    private String table;
    private String alias;
    private int limit;
    private int offset;

    public JDBCSelectQueryBuilder selectAll() {
        this.selectAll = true;
        return this;
    }

    public JDBCSelectQueryBuilder select(String columnName) {
        this.columns.add(columnName);
        return this;
    }

    public JDBCSelectQueryBuilder from(String tableName) {
        return this.from(tableName, tableName);
    }

    public JDBCSelectQueryBuilder from(String tableName, String alias) {
        this.table = tableName + " AS " + alias;
        this.alias = alias;
        return this;
    }

    public JDBCSelectQueryBuilder join(String columnLeft, String tableRight, String columnRight) {
        return this.join(columnLeft, Operation.EQ, tableRight, columnRight, JoinType.INNER);
    }

    public JDBCSelectQueryBuilder join(String columnLeft, String tableRight, String columnRight, JoinType joinType) {
        return this.join(columnLeft, Operation.EQ, tableRight, columnRight, joinType);
    }

    public JDBCSelectQueryBuilder join(String columnLeft, Operation operation, String tableRight, String columnRight) {
        return this.join(columnLeft, operation, tableRight, columnRight, JoinType.INNER);
    }

    public JDBCSelectQueryBuilder join(String columnLeft, Operation operation, String tableRight, String columnRight, JoinType joinType) {
        return this.join(this.alias, columnLeft, operation, tableRight, columnRight, joinType);
    }

    public JDBCSelectQueryBuilder join(String tableLeft, String columnLeft, String tableRight, String columnRight) {
        return this.join(tableLeft, columnLeft, Operation.EQ, tableRight, columnRight, JoinType.INNER);
    }

    public JDBCSelectQueryBuilder join(String tableLeft, String columnLeft, String tableRight, String columnRight, JoinType joinType) {
        return this.join(tableLeft, columnLeft, Operation.EQ, tableRight, columnRight, joinType);
    }

    public JDBCSelectQueryBuilder join(String tableLeft, String columnLeft, Operation operation, String tableRight, String columnRight) {
        return this.join(tableLeft, columnLeft, operation, tableRight, columnRight, JoinType.INNER);
    }

    public JDBCSelectQueryBuilder join(String tableLeft, String columnLeft, Operation operation, String tableRight, String columnRight, JoinType joinType) {
        return this.joinAlias(tableLeft, columnLeft, operation, tableRight, tableRight, columnRight, joinType);
    }

    public JDBCSelectQueryBuilder joinAlias(String columnLeft, String tableRight, String aliasRight, String columnRight) {
        return this.joinAlias(this.alias, columnLeft, Operation.EQ, tableRight, aliasRight, columnRight, JoinType.INNER);
    }

    public JDBCSelectQueryBuilder joinAlias(String columnLeft, String tableRight, String aliasRight, String columnRight, JoinType joinType) {
        return this.joinAlias(this.alias, columnLeft, Operation.EQ, tableRight, aliasRight, columnRight, joinType);
    }

    public JDBCSelectQueryBuilder joinAlias(String tableLeft, String columnLeft, String tableRight, String aliasRight, String columnRight, JoinType joinType) {
        return this.joinAlias(tableLeft, columnLeft, Operation.EQ, tableRight, aliasRight, columnRight, joinType);
    }

    public JDBCSelectQueryBuilder joinAlias(String tableLeft, String columnLeft, Operation operation, String tableRight, String aliasRight, String columnRight, JoinType joinType) {
        String string = joinType.getJoinType() +
                " JOIN " +
                tableRight +
                " " +
                aliasRight +
                " ON " +
                tableLeft + "." + columnLeft +
                operation.getOperation() +
                aliasRight + "." + columnRight;

        this.joins.add(string);
        return this;
    }

    public JDBCSelectQueryBuilder orderBy(String columnName, OrderCriteria orderCriteria) {
        this.orderCriteria = orderCriteria;
        this.orderBy = columnName;
        return this;
    }

    public JDBCSelectQueryBuilder distinct() {
        this.distinct = true;
        return this;
    }

    public JDBCSelectQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public JDBCSelectQueryBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public JDBCSelectQueryBuilder where(JDBCWhereClauseBuilder whereClauseBuilder) {
        if(whereClauseBuilder.toString().isEmpty()) {
            this.whereClauseBuilder = null;
        } else {
            this.whereClauseBuilder = whereClauseBuilder;
        }
        return this;
    }

    @Override
    public String getQueryAsString() {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");

        if (this.distinct) stringBuilder.append(" DISTINCT ");

        if (this.columns.size() > 0) {
            stringBuilder.append(this.joinColumns());
        } else if (this.selectAll) {
            stringBuilder
                    .append(this.alias)
                    .append(".")
                    .append(ALL);
        } else {
            return null;
        }

        stringBuilder
                .append(" FROM ")
                .append(this.table);

        if (this.joins.size() > 0) {
            stringBuilder.append(this.joinStrings(this.joins, " "));
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

        if (this.offset > 0){
            stringBuilder
                    .append(" OFFSET ")
                    .append(this.offset);
        }

        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    @Override
    protected String getTable() {
        return this.table;
    }

    private String joinColumns() {
        return this.columns.stream().map(s -> this.alias + "." + s).collect(Collectors.joining(","));
    }
}
