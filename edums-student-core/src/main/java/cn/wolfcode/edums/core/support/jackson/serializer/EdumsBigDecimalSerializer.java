package cn.wolfcode.edums.core.support.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Leon
 * @date 2020-01-14
 */
public class EdumsBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(bigDecimal)) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeNumber(bigDecimal.setScale(2, RoundingMode.HALF_UP));
        }
    }
}
