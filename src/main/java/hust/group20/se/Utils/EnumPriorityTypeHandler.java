package hust.group20.se.Utils;

import hust.group20.se.Entity.Priority;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumPriorityTypeHandler extends BaseTypeHandler<Priority> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Priority priority, JdbcType jdbcType) throws SQLException {
        if(jdbcType == null) {
            preparedStatement.setString(i,priority.name());
        } else {
            preparedStatement.setObject(i, priority.name(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public Priority getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return Priority.fromString(string);
    }

    @Override
    public Priority getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        return Priority.fromString(string);
    }

    @Override
    public Priority getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return Priority.fromString(string);
    }
}
