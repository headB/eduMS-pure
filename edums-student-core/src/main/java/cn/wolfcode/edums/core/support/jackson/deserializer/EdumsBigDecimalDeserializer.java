package cn.wolfcode.edums.core.support.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Leon
 * @date 2020-01-14
 */
public class EdumsBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (Objects.isNull(jsonParser.getDecimalValue())) {
            return null;
        } else {
            return jsonParser.getDecimalValue().setScale(2, RoundingMode.HALF_UP);
        }
    }
}
