import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 Get sorted list of private fields the object declares (inherited fields should be skipped).
 */
class FieldGetter {

    public List<String> getPrivateFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        List<String> stringList = new ArrayList<>();
        for (Field field : fields) {
            if (field.getModifiers() == Modifier.PRIVATE) {
                stringList.add(field.getName());
            }
        }
        Collections.sort(stringList);
        return stringList;
    }
}