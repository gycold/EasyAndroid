package com.easytools.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * package: com.easytools.tools.ClassUtil
 * author: gyc
 * description:与类操作相关的工具类，包含了装箱、拆箱操作，根据包名取得类名，取得当前java的版本号，属性查找、
 * 方法查找、类型转换、java关键字过滤等等(Tool, Static, ThreadSafe)
 * time: create at 2017/1/8 20:17
 */

public class ClassUtil {
    public static final String CLASS_EXTENSION = ".class";

    public static final String JAVA_EXTENSION = ".java";

    private static final ConcurrentMap<Class<?>, Map<String, Method>>
            GETTER_CACHE
            = new ConcurrentHashMap<Class<?>, Map<String, Method>>();

    private static final ConcurrentMap<String, Class<?>> CLASS_CACHE
            = new ConcurrentHashMap<String, Class<?>>();

    private ClassUtil() {
    }

    static {
        CLASS_CACHE.put("boolean", boolean.class);
        CLASS_CACHE.put("char", char.class);
        CLASS_CACHE.put("byte", byte.class);
        CLASS_CACHE.put("short", short.class);
        CLASS_CACHE.put("int", int.class);
        CLASS_CACHE.put("long", long.class);
        CLASS_CACHE.put("float", float.class);
        CLASS_CACHE.put("double", double.class);
        CLASS_CACHE.put("void", void.class);
        CLASS_CACHE.put("Boolean", Boolean.class);
        CLASS_CACHE.put("Character", Character.class);
        CLASS_CACHE.put("Byte", Byte.class);
        CLASS_CACHE.put("Short", Short.class);
        CLASS_CACHE.put("Integer", Integer.class);
        CLASS_CACHE.put("Long", Long.class);
        CLASS_CACHE.put("Float", Float.class);
        CLASS_CACHE.put("Double", Double.class);
        CLASS_CACHE.put("Number", Number.class);
        CLASS_CACHE.put("String", String.class);
        CLASS_CACHE.put("Object", Object.class);
        CLASS_CACHE.put("Class", Class.class);
        CLASS_CACHE.put("Void", Void.class);
        CLASS_CACHE.put("java.lang.Boolean", Boolean.class);
        CLASS_CACHE.put("java.lang.Character", Character.class);
        CLASS_CACHE.put("java.lang.Byte", Byte.class);
        CLASS_CACHE.put("java.lang.Short", Short.class);
        CLASS_CACHE.put("java.lang.Integer", Integer.class);
        CLASS_CACHE.put("java.lang.Long", Long.class);
        CLASS_CACHE.put("java.lang.Float", Float.class);
        CLASS_CACHE.put("java.lang.Double", Double.class);
        CLASS_CACHE.put("java.lang.Number", Number.class);
        CLASS_CACHE.put("java.lang.String", String.class);
        CLASS_CACHE.put("java.lang.Object", Object.class);
        CLASS_CACHE.put("java.lang.Class", Class.class);
        CLASS_CACHE.put("java.lang.Void", Void.class);
        CLASS_CACHE.put("java.util.Date", Date.class);
        CLASS_CACHE.put("boolean[]", boolean[].class);
        CLASS_CACHE.put("char[]", char[].class);
        CLASS_CACHE.put("byte[]", byte[].class);
        CLASS_CACHE.put("short[]", short[].class);
        CLASS_CACHE.put("int[]", int[].class);
        CLASS_CACHE.put("long[]", long[].class);
        CLASS_CACHE.put("float[]", float[].class);
        CLASS_CACHE.put("double[]", double[].class);
        CLASS_CACHE.put("Boolean[]", Boolean[].class);
        CLASS_CACHE.put("Character[]", Character[].class);
        CLASS_CACHE.put("Byte[]", Byte[].class);
        CLASS_CACHE.put("Short[]", Short[].class);
        CLASS_CACHE.put("Integer[]", Integer[].class);
        CLASS_CACHE.put("Long[]", Long[].class);
        CLASS_CACHE.put("Float[]", Float[].class);
        CLASS_CACHE.put("Double[]", Double[].class);
        CLASS_CACHE.put("Number[]", Number[].class);
        CLASS_CACHE.put("String[]", String[].class);
        CLASS_CACHE.put("Object[]", Object[].class);
        CLASS_CACHE.put("Class[]", Class[].class);
        CLASS_CACHE.put("Void[]", Void[].class);
        CLASS_CACHE.put("java.lang.Boolean[]", Boolean[].class);
        CLASS_CACHE.put("java.lang.Character[]", Character[].class);
        CLASS_CACHE.put("java.lang.Byte[]", Byte[].class);
        CLASS_CACHE.put("java.lang.Short[]", Short[].class);
        CLASS_CACHE.put("java.lang.Integer[]", Integer[].class);
        CLASS_CACHE.put("java.lang.Long[]", Long[].class);
        CLASS_CACHE.put("java.lang.Float[]", Float[].class);
        CLASS_CACHE.put("java.lang.Double[]", Double[].class);
        CLASS_CACHE.put("java.lang.Number[]", Number[].class);
        CLASS_CACHE.put("java.lang.String[]", String[].class);
        CLASS_CACHE.put("java.lang.Object[]", Object[].class);
        CLASS_CACHE.put("java.lang.Class[]", Class[].class);
        CLASS_CACHE.put("java.lang.Void[]", Void[].class);
        CLASS_CACHE.put("java.util.Date[]", Date[].class);
    }


    /**
     * 根据对象的类（Class）新建一个实例对象，用于反射
     * 即：通过反射取得实例化对象，和使用关键字New效果一样，如：
     *  Class<?> cls = Class.forName("com.easyandroid.ItemBean");//取得Class对象
     *  Object obj = cls.newInstance();//实例化对象，和使用关键字new一样
     *  Person per = (Person) obj ; //向下转型
     *
     * @param className 对象
     * @return 实例化对象
     */
    public static Object newInstance(String className) {
        try {
            return forName(className).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 动态加载类，返回Class类的对象，这个Class就是所有反射操作的源头
     * 作用是要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段
     *
     * @param className //类的完整名称，格式为"包.类"，如"com.easyandroid.ItemBean"
     * @return Class对象
     */
    public static Class<?> forName(String className) {
        try {
            return _forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 动态加载类，返回Class类的对象，这个Class就是所有反射操作的源头
     * 作用是要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段
     *
     * @param packages 包名的数组，如{"com.easyandroid"}
     * @param className 类名，如"ItemBean"
     * @return Class对象
     */
    public static Class<?> forName(String[] packages, String className) {
        // import class
        if (packages != null && packages.length > 0 &&
                !className.contains(".") &&
                !CLASS_CACHE.containsKey(className)) {
            for (String pkg : packages) {
                try {
                    return _forName(pkg + "." + className);
                } catch (ClassNotFoundException e2) {
                }
            }
            try {
                return _forName("java.lang." + className);
            } catch (ClassNotFoundException e2) {
            }
        }
        try {
            return _forName(className);
        } catch (ClassNotFoundException e) {
            // inner class
            int i = className.lastIndexOf('.');
            if (i > 0 && i < className.length() - 1) {
                try {
                    return _forName(className.substring(0, i) + "$" +
                            className.substring(i + 1));
                } catch (ClassNotFoundException e2) {
                }
            }
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static Class<?> _forName(String name)
            throws ClassNotFoundException {
        if (StringUtil.isEmpty(name)) return null;
        String key = name;
        Class<?> clazz = CLASS_CACHE.get(key);
        if (clazz == null) {
            int index = name.indexOf('[');
            if (index > 0) {
                int i = (name.length() - index) / 2;
                name = name.substring(0, index);
                StringBuilder sb = new StringBuilder();
                while (i-- > 0) {
                    sb.append("["); // int[][]
                }
                if ("void".equals(name)) {
                    sb.append("V");
                } else if ("boolean".equals(name)) {
                    sb.append("Z");
                } else if ("byte".equals(name)) {
                    sb.append("B");
                } else if ("char".equals(name)) {
                    sb.append("C");
                } else if ("double".equals(name)) {
                    sb.append("D");
                } else if ("float".equals(name)) {
                    sb.append("F");
                } else if ("int".equals(name)) {
                    sb.append("I");
                } else if ("long".equals(name)) {
                    sb.append("J");
                } else if ("short".equals(name)) {
                    sb.append("S");
                } else {
                    sb.append('L').append(name).append(';');
                }
                name = sb.toString();
            }
            try {
                clazz = Class.forName(name, true,
                        Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException cne) {
                clazz = Class.forName(name);
            }
            Class<?> old = CLASS_CACHE.putIfAbsent(key, clazz);
            if (old != null) {
                clazz = old;
            }
        }
        return clazz;
    }

    /**
     * 取得拆箱类对象
     *
     * @param type 基本类型的Class对象
     * @return
     */
    public static Class<?> getUnboxedClass(Class<?> type) {
        if (type == Boolean.class) {
            return boolean.class;
        } else if (type == Character.class) {
            return char.class;
        } else if (type == Byte.class) {
            return byte.class;
        } else if (type == Short.class) {
            return short.class;
        } else if (type == Integer.class) {
            return int.class;
        } else if (type == Long.class) {
            return long.class;
        } else if (type == Float.class) {
            return float.class;
        } else if (type == Double.class) {
            return double.class;
        } else {
            return type;
        }
    }

    /**
     * 取得装箱类的对象
     * @param type 基本类型的对象
     * @return
     */
    public static Class<?> getBoxedClass(Class<?> type) {
        if (type == boolean.class) {
            return Boolean.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == short.class) {
            return Short.class;
        } else if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == double.class) {
            return Double.class;
        } else {
            return type;
        }
    }


    public static Boolean boxed(boolean v) {
        return Boolean.valueOf(v);
    }


    public static Character boxed(char v) {
        return Character.valueOf(v);
    }


    public static Byte boxed(byte v) {
        return Byte.valueOf(v);
    }


    public static Short boxed(short v) {
        return Short.valueOf(v);
    }


    public static Integer boxed(int v) {
        return Integer.valueOf(v);
    }


    public static Long boxed(long v) {
        return Long.valueOf(v);
    }


    public static Float boxed(float v) {
        return Float.valueOf(v);
    }


    public static Double boxed(double v) {
        return Double.valueOf(v);
    }


    public static <T> T boxed(T v) {
        return v;
    }


    public static boolean unboxed(Boolean v) {
        return v == null ? false : v.booleanValue();
    }


    public static char unboxed(Character v) {
        return v == null ? '\0' : v.charValue();
    }


    public static byte unboxed(Byte v) {
        return v == null ? 0 : v.byteValue();
    }


    public static short unboxed(Short v) {
        return v == null ? 0 : v.shortValue();
    }


    public static int unboxed(Integer v) {
        return v == null ? 0 : v.intValue();
    }


    public static long unboxed(Long v) {
        return v == null ? 0 : v.longValue();
    }


    public static float unboxed(Float v) {
        return v == null ? 0 : v.floatValue();
    }


    public static double unboxed(Double v) {
        return v == null ? 0 : v.doubleValue();
    }


    public static <T> T unboxed(T v) {
        return v;
    }


    public static boolean isTrue(boolean object) {
        return object;
    }


    public static boolean isTrue(char object) {
        return object != '\0';
    }


    public static boolean isTrue(byte object) {
        return object != (byte) 0;
    }


    public static boolean isTrue(short object) {
        return object != (short) 0;
    }


    public static boolean isTrue(int object) {
        return object != 0;
    }


    public static boolean isTrue(long object) {
        return object != 0l;
    }


    public static boolean isTrue(float object) {
        return object != 0f;
    }


    public static boolean isTrue(double object) {
        return object != 0d;
    }


    public static boolean isTrue(Object object) {
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        }
        return getSize(object) != 0;
    }


    public static boolean isNotEmpty(Object object) {
        return isTrue(object);
    }


    public static int getSize(Object object) {
        if (object == null) {
            return 0;
        } else if (object instanceof Collection<?>) {
            return ((Collection<?>) object).size();
        } else if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).size();
        } else if (object instanceof Object[]) {
            return ((Object[]) object).length;
        } else if (object instanceof int[]) {
            return ((int[]) object).length;
        } else if (object instanceof long[]) {
            return ((long[]) object).length;
        } else if (object instanceof float[]) {
            return ((float[]) object).length;
        } else if (object instanceof double[]) {
            return ((double[]) object).length;
        } else if (object instanceof short[]) {
            return ((short[]) object).length;
        } else if (object instanceof byte[]) {
            return ((byte[]) object).length;
        } else if (object instanceof char[]) {
            return ((char[]) object).length;
        } else if (object instanceof boolean[]) {
            return ((boolean[]) object).length;
        } else {
            return -1;
        }
    }

    /**
     * 根据资源名称获取URI
     * @param name
     * @return
     */
    public static URI toURI(String name) {
        try {
            return new URI(name);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取得一个方法的全名，如"getPersonInfo(String name, int age)"
     *
     * @param name 方法名称
     * @param parameterTypes 参数的类型，如"String.class"、"int.class"等
     * @return 方法的全名
     */
    public static String getMethodFullName(String name, Class<?>[] parameterTypes) {
        StringBuilder buf = new StringBuilder();
        buf.append(name);
        buf.append("(");
        if (parameterTypes != null && parameterTypes.length > 0) {
            boolean first = true;
            for (Class<?> type : parameterTypes) {
                if (type != null) {
                    if (first) {
                        first = false;
                    } else {
                        buf.append(",");
                    }
                    buf.append(type.getCanonicalName());//在根据类名字创建文件的时候最好使用getCanonicalName()
                }
            }
        }
        buf.append(")");
        return buf.toString();
    }

    /**
     *
     * @param cls
     * @return
     */
    public static Class<?> getGenericClass(Class<?> cls) {
        return getGenericClass(cls, 0);
    }


    public static Class<?> getGenericClass(Class<?> cls, int i) {
        try {
            ParameterizedType parameterizedType;//ParameterizedType,表示参数化类型(泛型)。
            // 比如：java.lang.Comparable<? super T>、List<T>、List<String>等

            if (cls.getGenericInterfaces().length > 0 &&
                    cls.getGenericInterfaces()[0] instanceof ParameterizedType) {
                parameterizedType
                        = ((ParameterizedType) cls.getGenericInterfaces()[0]);
            } else if (cls.getGenericSuperclass() instanceof ParameterizedType) {//
                // getGenericSuperclass和getGenericInterfaces:用于返回带参数化类型的超类与接口

                parameterizedType
                        = (ParameterizedType) cls.getGenericSuperclass();
            } else {
                parameterizedType = null;
            }
            if (parameterizedType != null) {
                Object genericClass
                        = parameterizedType.getActualTypeArguments()[i];//得到声明此参数化类型的类(如：java.lang.Comparable)
                // 和实际的类型参数数组([? super T]),? super T又是一个WildcardType类型.
                // WildcardType:它用来描述通配符表达式

                if (genericClass instanceof ParameterizedType) { // 处理多级泛型
                    return (Class<?>) ((ParameterizedType) genericClass).getRawType();
                } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
                    Class<?> componentType
                            = (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
                    if (componentType.isArray()) {
                        return componentType;
                    } else {
                        return Array.newInstance(componentType, 0).getClass();
                    }
                } else if (genericClass instanceof Class) {
                    return (Class<?>) genericClass;
                }
            }
        } catch (Exception e) {
        }
        if (cls.getSuperclass() != null &&
                cls.getSuperclass() != Object.class) {
            return getGenericClass(cls.getSuperclass(), i);
        } else {
            throw new IllegalArgumentException(
                    cls.getName() + " generic type undefined!");
        }
    }

    /**
     * 取得Java版本
     *
     * @return
     */
    public static String getJavaVersion() {
        return System.getProperty("java.specification.version");
    }


    /**
     * 判断Java版本
     * @param javaVersion
     * @return
     */
    public static boolean isBeforeJava5(String javaVersion) {
        return (StringUtil.isEmpty(javaVersion) || "1.0".equals(javaVersion) ||
                "1.1".equals(javaVersion) || "1.2".equals(javaVersion) ||
                "1.3".equals(javaVersion) || "1.4".equals(javaVersion));
    }


    /**
     * 判断Java版本
     * @param javaVersion
     * @return
     */
    public static boolean isBeforeJava6(String javaVersion) {
        return isBeforeJava5(javaVersion) || "1.5".equals(javaVersion);
    }

    /**
     * 将错误或异常转换为字符串信息
     *
     * @param e
     * @return
     */
    public static String toString(Throwable e) {
        StringWriter w = new StringWriter();
        PrintWriter p = new PrintWriter(w);
        p.print(e.getClass().getName() + ": ");
        if (e.getMessage() != null) {
            p.print(e.getMessage() + "\n");
        }
        p.println();
        try {
            e.printStackTrace(p);
            return w.toString();
        } finally {
            p.close();
        }
    }


    private static final int JIT_LIMIT = 5 * 1024;


    /**
     * 判断字节数组大小，是否影响JIT编译器的性能
     *
     * @param name
     * @param bytecode
     */
    public static void checkBytecode(String name, byte[] bytecode) {
        if (bytecode.length > JIT_LIMIT) {
            System.err.println(
                    "The template bytecode too long, may be affect the JIT compiler. template class: " +
                            name);
        }
    }

    /**
     * 取得一系列方法名
     * @param cls Class类对象
     * @param sizers 所需方法的名称集合
     * @return
     */
    public static String getSizeMethod(Class<?> cls, String[] sizers) {
        for (String sizer : sizers) {
            try {
                return cls.getMethod(sizer, new Class<?>[0]).getName() + "()";
            } catch (NoSuchMethodException e) {
            }
        }
        return null;
    }


    /**
     * 取得方法名称
     * @param method
     * @param parameterClasses
     * @param rightCode
     * @return
     */
    public static String getMethodName(Method method, Class<?>[] parameterClasses, String rightCode) {
        if (method.getParameterTypes().length > parameterClasses.length) {
            Class<?>[] types = method.getParameterTypes();
            StringBuilder buf = new StringBuilder(rightCode);
            for (int i = parameterClasses.length; i < types.length; i++) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                Class<?> type = types[i];
                String def;
                if (type == boolean.class) {
                    def = "false";
                } else if (type == char.class) {
                    def = "\'\\0\'";
                } else if (type == byte.class || type == short.class ||
                        type == int.class || type == long.class ||
                        type == float.class || type == double.class) {
                    def = "0";
                } else {
                    def = "null";
                }
                buf.append(def);
            }
        }
        return method.getName() + "(" + rightCode + ")";
    }


    @SuppressWarnings("unchecked")
    public static Object searchProperty(Object leftParameter, String name)
            throws Exception {
        Class<?> leftClass = leftParameter.getClass();
        Object result;
        if (leftParameter.getClass().isArray() && "length".equals(name)) {
            result = Array.getLength(leftParameter);
        } else if (leftParameter instanceof Map) {
            result = ((Map<Object, Object>) leftParameter).get(name);
        } else {
            try {
                String getter = "get" + name.substring(0, 1).toUpperCase() +
                        name.substring(1);
                Method method = leftClass.getMethod(getter, new Class<?>[0]);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                result = method.invoke(leftParameter, new Object[0]);
            } catch (NoSuchMethodException e2) {
                try {
                    String getter = "is" + name.substring(0, 1).toUpperCase() +
                            name.substring(1);
                    Method method = leftClass.getMethod(getter,
                            new Class<?>[0]);
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    result = method.invoke(leftParameter, new Object[0]);
                } catch (NoSuchMethodException e3) {
                    Field field = leftClass.getField(name);
                    result = field.get(leftParameter);
                }
            }
        }
        return result;
    }


    public static Method searchMethod(Class<?> currentClass, String name, Class<?>[] parameterTypes)
            throws NoSuchMethodException {
        return searchMethod(currentClass, name, parameterTypes, false);
    }


    public static Method searchMethod(Class<?> currentClass, String name, Class<?>[] parameterTypes, boolean boxed)
            throws NoSuchMethodException {
        if (currentClass == null) {
            throw new NoSuchMethodException("class == null");
        }
        try {
            return currentClass.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Method likeMethod = null;
            for (Method method : currentClass.getMethods()) {
                if (method.getName().equals(name) && parameterTypes.length ==
                        method.getParameterTypes().length &&
                        Modifier.isPublic(method.getModifiers())) {
                    if (parameterTypes.length > 0) {
                        Class<?>[] types = method.getParameterTypes();
                        boolean eq = true;
                        boolean like = true;
                        for (int i = 0; i < parameterTypes.length; i++) {
                            Class<?> type = types[i];
                            Class<?> parameterType = parameterTypes[i];
                            if (type != null && parameterType != null &&
                                    !type.equals(parameterType)) {
                                eq = false;
                                if (boxed) {
                                    type = ClassUtil.getBoxedClass(type);
                                    parameterType = ClassUtil.getBoxedClass(
                                            parameterType);
                                }
                                if (!type.isAssignableFrom(parameterType)) {
                                    eq = false;
                                    like = false;
                                    break;
                                }
                            }
                        }
                        if (!eq) {
                            if (like && (likeMethod == null ||
                                    likeMethod.getParameterTypes()[0].isAssignableFrom(
                                            method.getParameterTypes()[0]))) {
                                likeMethod = method;
                            }
                            continue;
                        }
                    }
                    return method;
                }
            }
            if (likeMethod != null) {
                return likeMethod;
            }
            throw e;
        }
    }


    public static String getInitCode(Class<?> type) {
        if (byte.class.equals(type) || short.class.equals(type) ||
                int.class.equals(type) || long.class.equals(type) ||
                float.class.equals(type) || double.class.equals(type)) {
            return "0";
        } else if (char.class.equals(type)) {
            return "'\\0'";
        } else if (boolean.class.equals(type)) {
            return "false";
        } else {
            return "null";
        }
    }


    public static String getInitCodeWithType(Class<?> type) {
        if (byte.class.equals(type)) {
            return "(byte) 0";
        } else if (short.class.equals(type)) {
            return "(short) 0";
        } else if (int.class.equals(type)) {
            return "0";
        } else if (long.class.equals(type)) {
            return "0l";
        } else if (float.class.equals(type)) {
            return "0f";
        } else if (double.class.equals(type)) {
            return "0d";
        } else if (char.class.equals(type)) {
            return "'\\0'";
        } else if (boolean.class.equals(type)) {
            return "false";
        } else {
            return "(" + type.getCanonicalName() + ") null";
        }
    }


    public static boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return value == null ? false : toBoolean(String.valueOf(value));
    }


    public static char toChar(Object value) {
        if (value instanceof Character) {
            return (Character) value;
        }
        return value == null ? '\0' : toChar(String.valueOf(value));
    }


    public static byte toByte(Object value) {
        if (value instanceof Number) {
            return ((Number) value).byteValue();
        }
        return value == null ? 0 : toByte(String.valueOf(value));
    }


    public static short toShort(Object value) {
        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }
        return value == null ? 0 : toShort(String.valueOf(value));
    }


    public static int toInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return value == null ? 0 : toInt(String.valueOf(value));
    }


    public static long toLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return value == null ? 0 : toLong(String.valueOf(value));
    }


    public static float toFloat(Object value) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        return value == null ? 0 : toFloat(String.valueOf(value));
    }


    public static double toDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return value == null ? 0 : toDouble(String.valueOf(value));
    }


    public static Class<?> toClass(Object value) {
        if (value instanceof Class) {
            return (Class<?>) value;
        }
        return value == null ? null : toClass(String.valueOf(value));
    }


    public static boolean toBoolean(String value) {
        return StringUtil.isEmpty(value) ? false : Boolean.parseBoolean(value);
    }


    public static char toChar(String value) {
        return StringUtil.isEmpty(value) ? '\0' : value.charAt(0);
    }


    public static byte toByte(String value) {
        return StringUtil.isEmpty(value) ? 0 : Byte.parseByte(value);
    }


    public static short toShort(String value) {
        return StringUtil.isEmpty(value) ? 0 : Short.parseShort(value);
    }


    public static int toInt(String value) {
        return StringUtil.isEmpty(value) ? 0 : Integer.parseInt(value);
    }


    public static long toLong(String value) {
        return StringUtil.isEmpty(value) ? 0 : Long.parseLong(value);
    }


    public static float toFloat(String value) {
        return StringUtil.isEmpty(value) ? 0 : Float.parseFloat(value);
    }


    public static double toDouble(String value) {
        return StringUtil.isEmpty(value) ? 0 : Double.parseDouble(value);
    }


    public static Class<?> toClass(String value) {
        return StringUtil.isEmpty(value) ? null : ClassUtil.forName(value);
    }


    public static Method getGetter(Object bean, String property) {
        Map<String, Method> cache = GETTER_CACHE.get(bean.getClass());
        if (cache == null) {
            cache = new ConcurrentHashMap<String, Method>();
            for (Method method : bean.getClass().getMethods()) {
                if (Modifier.isPublic(method.getModifiers()) &&
                        !Modifier.isStatic(method.getModifiers()) &&
                        !void.class.equals(method.getReturnType()) &&
                        method.getParameterTypes().length == 0) {
                    String name = method.getName();
                    if (name.length() > 3 && name.startsWith("get")) {
                        cache.put(name.substring(3, 4).toLowerCase() +
                                name.substring(4), method);
                    } else if (name.length() > 2 && name.startsWith("is")) {
                        cache.put(name.substring(2, 3).toLowerCase() +
                                name.substring(3), method);
                    }
                }
            }
            Map<String, Method> old = GETTER_CACHE.putIfAbsent(bean.getClass(),
                    cache);
            if (old != null) {
                cache = old;
            }
        }
        return cache.get(property);
    }


    public static Object getProperty(Object bean, String property) {
        if (bean == null || StringUtil.isEmpty(property)) {
            return null;
        }
        try {
            Method getter = getGetter(bean, property);
            if (getter != null) {
                if (!getter.isAccessible()) {
                    getter.setAccessible(true);
                }
                return getter.invoke(bean, new Object[0]);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public static void setProperties(Object bean, Map<String, Object> properties) {
        for (Method method : bean.getClass().getMethods()) {
            String name = method.getName();
            if (name.length() > 3 && name.startsWith("set") &&
                    Modifier.isPublic(method.getModifiers()) &&
                    method.getParameterTypes().length == 1 &&
                    method.getDeclaringClass() != Object.class) {
                String key = name.substring(3, 4).toLowerCase() +
                        name.substring(4);
                try {
                    Object value = properties.get(key);
                    if (value != null) {
                        method.invoke(bean, new Object[]{
                                convertCompatibleType(value,
                                        method.getParameterTypes()[0])});
                    }
                } catch (Exception e) {
                }
            }
        }
    }


    public static Map<String, Object> getProperties(Object bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Method method : bean.getClass().getMethods()) {
            String name = method.getName();
            if ((name.length() > 3 && name.startsWith("get") ||
                    name.length() > 2 && name.startsWith("is")) &&
                    Modifier.isPublic(method.getModifiers()) &&
                    method.getParameterTypes().length == 0 &&
                    method.getDeclaringClass() != Object.class) {
                int i = name.startsWith("get") ? 3 : 2;
                String key = name.substring(i, i + 1).toLowerCase() +
                        name.substring(i + 1);
                try {
                    map.put(key, method.invoke(bean, new Object[0]));
                } catch (Exception e) {
                }
            }
        }
        return map;
    }


    public static <K, V> Set<Map.Entry<K, V>> entrySet(Map<K, V> map) {
        return map == null ? null : map.entrySet();
    }


    public static String dumpException(Throwable e) {
        StringWriter sw = new StringWriter(160);
        sw.write(e.getClass().getName());
        sw.write(":\n");
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }


    public static String filterJavaKeyword(String name) {
        if ("abstract".equals(name) || "assert".equals(name) ||
                "boolean".equals(name) || "break".equals(name) ||
                "byte".equals(name) || "case".equals(name) ||
                "catch".equals(name) || "char".equals(name) ||
                "class".equals(name) || "continue".equals(name) ||
                "default".equals(name) || "do".equals(name) ||
                "double".equals(name) || "else".equals(name) ||
                "enum".equals(name) || "extends".equals(name) ||
                "final".equals(name) || "finally".equals(name) ||
                "float".equals(name) || "for".equals(name) ||
                "if".equals(name) || "implements".equals(name) ||
                "import".equals(name) || "instanceof".equals(name) ||
                "int".equals(name) || "interface".equals(name) ||
                "long".equals(name) || "native".equals(name) ||
                "new".equals(name) || "package".equals(name) ||
                "private".equals(name) || "protected".equals(name) ||
                "public".equals(name) || "return".equals(name) ||
                "strictfp".equals(name) || "short".equals(name) ||
                "static".equals(name) || "super".equals(name) ||
                "switch".equals(name) || "synchronized".equals(name) ||
                "this".equals(name) || "throw".equals(name) ||
                "throws".equals(name) || "transient".equals(name) ||
                "try".equals(name) || "void".equals(name) ||
                "volatile".equals(name) || "while".equals(name)) {
            return "$" + name;
        }
        return name;
    }


    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 兼容类型转换。null值是OK的。如果不需要转换，则返回原来的值。
     * 进行的兼容类型转换如下：（基本类对应的Wrapper类型不再列出。）
     *
     * @param value 转换的类型
     * @param type  字节码
     * @return 返回转换之后的对象
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object convertCompatibleType(Object value, Class<?> type) {

        if (value == null || type == null ||
                type.isAssignableFrom(value.getClass())) {
            return value;
        }
        if (value instanceof String) {
            String string = (String) value;
            if (char.class.equals(type) || Character.class.equals(type)) {
                if (string.length() != 1) {
                    throw new IllegalArgumentException(String.format(
                            "CAN NOT convert String(%s) to char!" +
                                    " when convert String to char, the String MUST only 1 char.",
                            string));
                }
                return string.charAt(0);
            } else if (type.isEnum()) {
                return Enum.valueOf((Class<Enum>) type, string);
            } else if (type == BigInteger.class) {
                return new BigInteger(string);
            } else if (type == BigDecimal.class) {
                return new BigDecimal(string);
            } else if (type == Short.class || type == short.class) {
                return new Short(string);
            } else if (type == Integer.class || type == int.class) {
                return new Integer(string);
            } else if (type == Long.class || type == long.class) {
                return new Long(string);
            } else if (type == Double.class || type == double.class) {
                return new Double(string);
            } else if (type == Float.class || type == float.class) {
                return new Float(string);
            } else if (type == Byte.class || type == byte.class) {
                return new Byte(string);
            } else if (type == Boolean.class || type == boolean.class) {
                return new Boolean(string);
            } else if (type == Date.class) {
                try {
                    return new SimpleDateFormat(DATE_FORMAT).parse(
                            (String) value);
                } catch (ParseException e) {
                    throw new IllegalStateException(
                            "Failed to parse date " + value + " by format " +
                                    DATE_FORMAT + ", cause: " + e.getMessage(),
                            e);
                }
            } else if (type == Class.class) {
                return ClassUtil.forName((String) value);
            }
        } else if (value instanceof Number) {
            Number number = (Number) value;
            if (type == byte.class || type == Byte.class) {
                return number.byteValue();
            } else if (type == short.class || type == Short.class) {
                return number.shortValue();
            } else if (type == int.class || type == Integer.class) {
                return number.intValue();
            } else if (type == long.class || type == Long.class) {
                return number.longValue();
            } else if (type == float.class || type == Float.class) {
                return number.floatValue();
            } else if (type == double.class || type == Double.class) {
                return number.doubleValue();
            } else if (type == BigInteger.class) {
                return BigInteger.valueOf(number.longValue());
            } else if (type == BigDecimal.class) {
                return BigDecimal.valueOf(number.doubleValue());
            } else if (type == Date.class) {
                return new Date(number.longValue());
            }
        } else if (value instanceof Collection) {
            Collection collection = (Collection) value;
            if (type.isArray()) {
                int length = collection.size();
                Object array = Array.newInstance(type.getComponentType(),
                        length);
                int i = 0;
                for (Object item : collection) {
                    Array.set(array, i++, item);
                }
                return array;
            } else if (!type.isInterface()) {
                try {
                    Collection result = (Collection) type.newInstance();
                    result.addAll(collection);
                    return result;
                } catch (Throwable e) {
                }
            } else if (type == List.class) {
                return new ArrayList<Object>(collection);
            } else if (type == Set.class) {
                return new HashSet<Object>(collection);
            }
        } else if (value.getClass().isArray() &&
                Collection.class.isAssignableFrom(type)) {
            Collection collection;
            if (!type.isInterface()) {
                try {
                    collection = (Collection) type.newInstance();
                } catch (Throwable e) {
                    collection = new ArrayList<Object>();
                }
            } else if (type == Set.class) {
                collection = new HashSet<Object>();
            } else {
                collection = new ArrayList<Object>();
            }
            int length = Array.getLength(value);
            for (int i = 0; i < length; i++) {
                collection.add(Array.get(value, i));
            }
            return collection;
        }
        return value;
    }

}
