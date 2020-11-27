import java.lang.reflect.Method;

class MethodFinder {

    public static String findMethod(String methodName, String[] classNames) throws ClassNotFoundException {
        String name = null;
        for (String className : classNames) {
            Method[] method = Class.forName(className).getMethods();
            for (Method method1 : method) {
                if (method1.getName().equals(methodName)) {
                    name = Class.forName(className).getName();
                }
            }
        }
        return name;
    }
}