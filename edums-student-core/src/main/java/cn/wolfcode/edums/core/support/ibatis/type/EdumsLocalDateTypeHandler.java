package cn.wolfcode.edums.core.support.ibatis.type;

import org.apache.ibatis.type.LocalDateTypeHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author Leon
 * @date 2020-01-04
 */
public class EdumsLocalDateTypeHandler extends LocalDateTypeHandler {

    @Override
    public LocalDate getResult(ResultSet rs, String columnName) throws SQLException {
        Object object = rs.getObject(columnName);
        if (object == null) {
            return null;
        }
        //在这里强行转换，将sql的时间转换为LocalDateTime
        if (object instanceof Timestamp) {
            // 可以根据自己的需要进行转化
            return ((Timestamp) object).toLocalDateTime().toLocalDate();
        } else if (object instanceof Date) {
            return ((Date) object).toLocalDate();
        }
        return super.getResult(rs, columnName);
    }
}
