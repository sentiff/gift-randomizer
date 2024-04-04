package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.sentiff.gift.randomizer.commons.utils.JsonUtils;

class JsonUtilsTest {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Test
    void testToJson() throws JsonProcessingException {
        val rectangle = new Rectangle(1, 2);
        val expected = "{\"width\":1,\"length\":2}";
        val actual = jsonUtils.toJson(rectangle);
        assertEquals(expected, actual);
    }

    public record Rectangle(int width, int length) {
    }
}
