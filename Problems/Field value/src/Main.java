import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Get value for a given public field or null if the field does not exist or is not accessible.
 */
class FieldGetter {

    public Object getFieldValue(Object object, String fieldName) throws IllegalAccessException {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (field.getModifiers() == Modifier.PROTECTED ||
                field.getModifiers() == Modifier.PRIVATE) {
                return null;
            }
            return field.get(object);
        } catch (NoSuchFieldException ex) {
            return null;
        }

    }

}
