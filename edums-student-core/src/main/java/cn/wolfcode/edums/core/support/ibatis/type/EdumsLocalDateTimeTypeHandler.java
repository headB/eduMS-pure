package cn.wolfcode.edums.core.support.ibatis.type;

import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Leon
 * @date 2020-01-04
 */
public class EdumsLocalDateTimeTypeHandler extends LocalDateTimeTypeHandler {

    @Override
    public LocalDateTime getResult(ResultSet rs, String columnName) throws SQLException {
        Object object = rs.getObject(columnName);
        if (object == null) {
            return null;
        }
        //在这里强行转换，将sql的时间转换为LocalDateTime
        if (object instanceof Timestamp) {
            // 可以根据自己的需要进行转化
            return ((Timestamp) object).toLocalDateTime();
        }
        return super.getResult(rs, columnName);
    }
}
