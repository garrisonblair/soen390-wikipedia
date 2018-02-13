package org.wikipedia.testutils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestUtils {

    // code grabbed from stackoverflow for forcing a field to take a value
    // https://stackoverflow.com/questions/40300469/mock-build-version-with-mockito
    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
