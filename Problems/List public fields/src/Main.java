import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 Get list of public fields the object declares (inherited fields should be skipped).
 */
class FieldGetter {

    public String[] getPublicFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        List<String> stringList = new ArrayList<>();
        for (Field field : fields) {
            if (field.getModifiers() == Modifier.PUBLIC) {
                stringList.add(field.getName());
            }
        }
        return stringList.toArray(stringList.toArray(new String[0]));
    }

}