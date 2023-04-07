package net.lexicodes.API.multiversion

import org.bukkit.Bukkit
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * **ReflectionUtils**
 *
 *
 * This class provides useful methods which makes dealing with reflection much easier, especially when working with Bukkit
 *
 *
 * You are welcome to use it, modify it and redistribute it under the following conditions:
 *
 *  * Don't claim this class as your own
 *  * Don't remove this disclaimer
 *
 *
 *
 * *It would be nice if you provide credit to me if you use this class in a published project*
 *
 * @author DarkBlade12
 * @version 1.1
 */
object ReflectionUtils {
    /**
     * Returns the constructor of a given class with the given parameter types
     *
     * @param clazz Target class
     * @param parameterTypes Parameter types of the desired constructor
     * @return The constructor of the target class with the specified parameter types
     * @throws NoSuchMethodException If the desired constructor with the specified parameter types cannot be found
     * @see DataType
     *
     * @see DataType.getPrimitive
     * @see DataType.compare
     */
    @Throws(NoSuchMethodException::class)
    fun getConstructor(clazz: Class<*>, vararg parameterTypes: Class<*>?): Constructor<*> {
        val primitiveTypes: Array<Class<*>> = DataType.Companion.getPrimitive(parameterTypes)
        for (constructor in clazz.constructors) {
            if (!DataType.compare(DataType.getPrimitive(constructor.parameterTypes), primitiveTypes)) {
                continue
            }
            return constructor
        }
        throw NoSuchMethodException("There is no such constructor in this class with the specified parameter types")
    }

    /**
     * Returns the constructor of a desired class with the given parameter types
     *
     * @param className Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param parameterTypes Parameter types of the desired constructor
     * @return The constructor of the desired target class with the specified parameter types
     * @throws NoSuchMethodException If the desired constructor with the specified parameter types cannot be found
     * @throws ClassNotFoundException ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see .getConstructor
     */
    @Throws(NoSuchMethodException::class, ClassNotFoundException::class)
    fun getConstructor(className: String, packageType: PackageType, vararg parameterTypes: Class<*>?): Constructor<*> {
        return getConstructor(packageType.getClass(className), *parameterTypes)
    }

    /**
     * Returns an instance of a class with the given arguments
     *
     * @param clazz Target class
     * @param arguments Arguments which are used to construct an object of the target class
     * @return The instance of the target class with the specified arguments
     * @throws InstantiationException If you cannot create an instance of the target class due to certain circumstances
     * @throws IllegalAccessException If the desired constructor cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException If the types of the arguments do not match the parameter types of the constructor (this should not occur since it searches for a constructor with the types of the arguments)
     * @throws InvocationTargetException If the desired constructor cannot be invoked
     * @throws NoSuchMethodException If the desired constructor with the specified arguments cannot be found
     */
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        IllegalArgumentException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class
    )
    fun instantiateObject(clazz: Class<*>, vararg arguments: Any?): Any {
        return getConstructor(clazz, *DataType.Companion.getPrimitive(arguments)).newInstance(*arguments)
    }

    /**
     * Returns an instance of a desired class with the given arguments
     *
     * @param className Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param arguments Arguments which are used to construct an object of the desired target class
     * @return The instance of the desired target class with the specified arguments
     * @throws InstantiationException If you cannot create an instance of the desired target class due to certain circumstances
     * @throws IllegalAccessException If the desired constructor cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException If the types of the arguments do not match the parameter types of the constructor (this should not occur since it searches for a constructor with the types of the arguments)
     * @throws InvocationTargetException If the desired constructor cannot be invoked
     * @throws NoSuchMethodException If the desired constructor with the specified arguments cannot be found
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see .instantiateObject
     */
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        IllegalArgumentException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class,
        ClassNotFoundException::class
    )
    fun instantiateObject(className: String, packageType: PackageType, vararg arguments: Any?): Any {
        return instantiateObject(packageType.getClass(className), *arguments)
    }

    /**
     * Returns a method of a class with the given parameter types
     *
     * @param clazz Target class
     * @param methodName Name of the desired method
     * @param parameterTypes Parameter types of the desired method
     * @return The method of the target class with the specified name and parameter types
     * @throws NoSuchMethodException If the desired method of the target class with the specified name and parameter types cannot be found
     * @see DataType.getPrimitive
     * @see DataType.compare
     */
    @Throws(NoSuchMethodException::class)
    fun getMethod(clazz: Class<*>?, methodName: String, vararg parameterTypes: Class<*>?): Method {
        val primitiveTypes: Array<Class<*>> = DataType.Companion.getPrimitive(parameterTypes)
        for (method in clazz!!.methods) {
            if (method.name != methodName || !DataType.compare(
                    DataType.getPrimitive(method.parameterTypes),
                    primitiveTypes
                )
            ) {
                continue
            }
            return method
        }
        throw NoSuchMethodException("There is no such method in this class with the specified name and parameter types")
    }

    /**
     * Returns a method of a desired class with the given parameter types
     *
     * @param className Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param methodName Name of the desired method
     * @param parameterTypes Parameter types of the desired method
     * @return The method of the desired target class with the specified name and parameter types
     * @throws NoSuchMethodException If the desired method of the desired target class with the specified name and parameter types cannot be found
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see .getMethod
     */
    @Throws(NoSuchMethodException::class, ClassNotFoundException::class)
    fun getMethod(
        className: String,
        packageType: PackageType,
        methodName: String,
        vararg parameterTypes: Class<*>?
    ): Method {
        return getMethod(packageType.getClass(className), methodName, *parameterTypes)
    }

    /**
     * Invokes a method on an object with the given arguments
     *
     * @param instance Target object
     * @param methodName Name of the desired method
     * @param arguments Arguments which are used to invoke the desired method
     * @return The result of invoking the desired method on the target object
     * @throws IllegalAccessException If the desired method cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException If the types of the arguments do not match the parameter types of the method (this should not occur since it searches for a method with the types of the arguments)
     * @throws InvocationTargetException If the desired method cannot be invoked on the target object
     * @throws NoSuchMethodException If the desired method of the class of the target object with the specified name and arguments cannot be found
     * @see .getMethod
     * @see DataType.getPrimitive
     */
    @Throws(
        IllegalAccessException::class,
        IllegalArgumentException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class
    )
    fun invokeMethod(instance: Any, methodName: String, vararg arguments: Any?): Any {
        return getMethod(instance.javaClass, methodName, *DataType.Companion.getPrimitive(arguments)).invoke(
            instance,
            *arguments
        )
    }

    /**
     * Invokes a method of the target class on an object with the given arguments
     *
     * @param instance Target object
     * @param clazz Target class
     * @param methodName Name of the desired method
     * @param arguments Arguments which are used to invoke the desired method
     * @return The result of invoking the desired method on the target object
     * @throws IllegalAccessException If the desired method cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException If the types of the arguments do not match the parameter types of the method (this should not occur since it searches for a method with the types of the arguments)
     * @throws InvocationTargetException If the desired method cannot be invoked on the target object
     * @throws NoSuchMethodException If the desired method of the target class with the specified name and arguments cannot be found
     * @see .getMethod
     * @see DataType.getPrimitive
     */
    @Throws(
        IllegalAccessException::class,
        IllegalArgumentException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class
    )
    fun invokeMethod(instance: Any?, clazz: Class<*>?, methodName: String, vararg arguments: Any?): Any {
        return getMethod(clazz, methodName, *DataType.Companion.getPrimitive(arguments)).invoke(instance, *arguments)
    }

    /**
     * Invokes a method of a desired class on an object with the given arguments
     *
     * @param instance Target object
     * @param className Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param methodName Name of the desired method
     * @param arguments Arguments which are used to invoke the desired method
     * @return The result of invoking the desired method on the target object
     * @throws IllegalAccessException If the desired method cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException If the types of the arguments do not match the parameter types of the method (this should not occur since it searches for a method with the types of the arguments)
     * @throws InvocationTargetException If the desired method cannot be invoked on the target object
     * @throws NoSuchMethodException If the desired method of the desired target class with the specified name and arguments cannot be found
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see .invokeMethod
     */
    @Throws(
        IllegalAccessException::class,
        IllegalArgumentException::class,
        InvocationTargetException::class,
        NoSuchMethodException::class,
        ClassNotFoundException::class
    )
    fun invokeMethod(
        instance: Any?,
        className: String,
        packageType: PackageType,
        methodName: String,
        vararg arguments: Any?
    ): Any {
        return invokeMethod(instance, packageType.getClass(className), methodName, *arguments)
    }

    /**
     * Returns a field of the target class with the given name
     *
     * @param clazz Target class
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The field of the target class with the specified name
     * @throws NoSuchFieldException If the desired field of the given class cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     */
    @Throws(NoSuchFieldException::class, SecurityException::class)
    fun getField(clazz: Class<*>?, declared: Boolean, fieldName: String?): Field {
        val field = if (declared) clazz!!.getDeclaredField(fieldName) else clazz!!.getField(fieldName)
        field.isAccessible = true
        return field
    }

    /**
     * Returns a field of a desired class with the given name
     *
     * @param className Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The field of the desired target class with the specified name
     * @throws NoSuchFieldException If the desired field of the desired class cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see .getField
     */
    @Throws(NoSuchFieldException::class, SecurityException::class, ClassNotFoundException::class)
    fun getField(className: String, packageType: PackageType, declared: Boolean, fieldName: String?): Field {
        return getField(packageType.getClass(className), declared, fieldName)
    }

    /**
     * Returns the value of a field of the given class of an object
     *
     * @param instance Target object
     * @param clazz Target class
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The value of field of the target object
     * @throws IllegalArgumentException If the target object does not feature the desired field
     * @throws IllegalAccessException If the desired field cannot be accessed
     * @throws NoSuchFieldException If the desired field of the target class cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     * @see .getField
     */
    @Throws(
        IllegalArgumentException::class,
        IllegalAccessException::class,
        NoSuchFieldException::class,
        SecurityException::class
    )
    fun getValue(instance: Any?, clazz: Class<*>?, declared: Boolean, fieldName: String?): Any {
        return getField(clazz, declared, fieldName)[instance]
    }

    /**
     * Returns the value of a field of a desired class of an object
     *
     * @param instance Target object
     * @param className Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The value of field of the target object
     * @throws IllegalArgumentException If the target object does not feature the desired field
     * @throws IllegalAccessException If the desired field cannot be accessed
     * @throws NoSuchFieldException If the desired field of the desired class cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see .getValue
     */
    @Throws(
        IllegalArgumentException::class,
        IllegalAccessException::class,
        NoSuchFieldException::class,
        SecurityException::class,
        ClassNotFoundException::class
    )
    fun getValue(
        instance: Any?,
        className: String,
        packageType: PackageType,
        declared: Boolean,
        fieldName: String?
    ): Any {
        return getValue(instance, packageType.getClass(className), declared, fieldName)
    }

    /**
     * Returns the value of a field with the given name of an object
     *
     * @param instance Target object
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The value of field of the target object
     * @throws IllegalArgumentException If the target object does not feature the desired field (should not occur since it searches for a field with the given name in the class of the object)
     * @throws IllegalAccessException If the desired field cannot be accessed
     * @throws NoSuchFieldException If the desired field of the target object cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     * @see .getValue
     */
    @Throws(
        IllegalArgumentException::class,
        IllegalAccessException::class,
        NoSuchFieldException::class,
        SecurityException::class
    )
    fun getValue(instance: Any, declared: Boolean, fieldName: String?): Any {
        return getValue(instance, instance.javaClass, declared, fieldName)
    }

    /**
     * Sets the value of a field of the given class of an object
     *
     * @param instance Target object
     * @param clazz Target class
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @param value New value
     * @throws IllegalArgumentException If the type of the value does not match the type of the desired field
     * @throws IllegalAccessException If the desired field cannot be accessed
     * @throws NoSuchFieldException If the desired field of the target class cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     * @see .getField
     */
    @Throws(
        IllegalArgumentException::class,
        IllegalAccessException::class,
        NoSuchFieldException::class,
        SecurityException::class
    )
    fun setValue(instance: Any?, clazz: Class<*>?, declared: Boolean, fieldName: String?, value: Any?) {
        getField(clazz, declared, fieldName)[instance] = value
    }

    /**
     * Sets the value of a field of a desired class of an object
     *
     * @param instance Target object
     * @param className Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @param value New value
     * @throws IllegalArgumentException If the type of the value does not match the type of the desired field
     * @throws IllegalAccessException If the desired field cannot be accessed
     * @throws NoSuchFieldException If the desired field of the desired class cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see .setValue
     */
    @Throws(
        IllegalArgumentException::class,
        IllegalAccessException::class,
        NoSuchFieldException::class,
        SecurityException::class,
        ClassNotFoundException::class
    )
    fun setValue(
        instance: Any?,
        className: String,
        packageType: PackageType,
        declared: Boolean,
        fieldName: String?,
        value: Any?
    ) {
        setValue(instance, packageType.getClass(className), declared, fieldName, value)
    }

    /**
     * Sets the value of a field with the given name of an object
     *
     * @param instance Target object
     * @param declared Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @param value New value
     * @throws IllegalArgumentException If the type of the value does not match the type of the desired field
     * @throws IllegalAccessException If the desired field cannot be accessed
     * @throws NoSuchFieldException If the desired field of the target object cannot be found
     * @throws SecurityException If the desired field cannot be made accessible
     * @see .setValue
     */
    @Throws(
        IllegalArgumentException::class,
        IllegalAccessException::class,
        NoSuchFieldException::class,
        SecurityException::class
    )
    fun setValue(instance: Any, declared: Boolean, fieldName: String?, value: Any?) {
        setValue(instance, instance.javaClass, declared, fieldName, value)
    }

    /**
     * Represents an enumeration of dynamic packages of NMS and CraftBukkit
     *
     *
     * This class is part of the **ReflectionUtils** and follows the same usage conditions
     *
     * @author DarkBlade12
     * @since 1.0
     */
    enum class PackageType
    /**
     * Construct a new package type
     *
     * @param path Path of the package
     */(
        /**
         * Returns the path of this package type
         *
         * @return The path
         */
        val path: String
    ) {
        MINECRAFT_SERVER("net.minecraft.server." + serverVersion), CRAFTBUKKIT("org.bukkit.craftbukkit." + serverVersion), CRAFTBUKKIT_BLOCK(
            CRAFTBUKKIT, "block"
        ),
        CRAFTBUKKIT_CHUNKIO(CRAFTBUKKIT, "chunkio"), CRAFTBUKKIT_COMMAND(
            CRAFTBUKKIT,
            "command"
        ),
        CRAFTBUKKIT_CONVERSATIONS(
            CRAFTBUKKIT, "conversations"
        ),
        CRAFTBUKKIT_ENCHANTMENS(CRAFTBUKKIT, "enchantments"), CRAFTBUKKIT_ENTITY(
            CRAFTBUKKIT, "entity"
        ),
        CRAFTBUKKIT_EVENT(CRAFTBUKKIT, "event"), CRAFTBUKKIT_GENERATOR(CRAFTBUKKIT, "generator"), CRAFTBUKKIT_HELP(
            CRAFTBUKKIT, "help"
        ),
        CRAFTBUKKIT_INVENTORY(CRAFTBUKKIT, "inventory"), CRAFTBUKKIT_MAP(CRAFTBUKKIT, "map"), CRAFTBUKKIT_METADATA(
            CRAFTBUKKIT, "metadata"
        ),
        CRAFTBUKKIT_POTION(CRAFTBUKKIT, "potion"), CRAFTBUKKIT_PROJECTILES(
            CRAFTBUKKIT,
            "projectiles"
        ),
        CRAFTBUKKIT_SCHEDULER(
            CRAFTBUKKIT, "scheduler"
        ),
        CRAFTBUKKIT_SCOREBOARD(CRAFTBUKKIT, "scoreboard"), CRAFTBUKKIT_UPDATER(
            CRAFTBUKKIT, "updater"
        ),
        CRAFTBUKKIT_UTIL(CRAFTBUKKIT, "util");

        /**
         * Construct a new package type
         *
         * @param parent Parent package of the package
         * @param path Path of the package
         */
        constructor(parent: PackageType, path: String) : this("$parent.$path")

        /**
         * Returns the class with the given name
         *
         * @param className Name of the desired class
         * @return The class with the specified name
         * @throws ClassNotFoundException If the desired class with the specified name and package cannot be found
         */
        @Throws(ClassNotFoundException::class)
        fun getClass(className: String): Class<*> {
            return Class.forName("$this.$className")
        }

        // Override for convenience
        override fun toString(): String {
            return path
        }

        companion object {
            val serverVersion: String
                /**
                 * Returns the version of your server
                 *
                 * @return The server version
                 */
                get() = Bukkit.getServer().javaClass.getPackage().name.substring(23)
        }
    }

    /**
     * Represents an enumeration of Java data types with corresponding classes
     *
     *
     * This class is part of the **ReflectionUtils** and follows the same usage conditions
     *
     * @author DarkBlade12
     * @since 1.0
     */
    enum class DataType
    /**
     * Construct a new data type
     *
     * @param primitive Primitive class of this data type
     * @param reference Reference class of this data type
     */(
        /**
         * Returns the primitive class of this data type
         *
         * @return The primitive class
         */
        val primitive: Class<*>?,
        /**
         * Returns the reference class of this data type
         *
         * @return The reference class
         */
        val reference: Class<*>
    ) {
        BYTE(Byte::class.javaPrimitiveType, Byte::class.java), SHORT(
            Short::class.javaPrimitiveType,
            Short::class.java
        ),
        INTEGER(
            Int::class.javaPrimitiveType, Int::class.java
        ),
        LONG(Long::class.javaPrimitiveType, Long::class.java), CHARACTER(
            Char::class.javaPrimitiveType, Char::class.java
        ),
        FLOAT(Float::class.javaPrimitiveType, Float::class.java), DOUBLE(
            Double::class.javaPrimitiveType, Double::class.java
        ),
        BOOLEAN(Boolean::class.javaPrimitiveType, Boolean::class.java);

        companion object {
            private val CLASS_MAP: MutableMap<Class<*>?, DataType> = HashMap()

            // Initialize map for quick class lookup
            init {
                for (type in values()) {
                    CLASS_MAP[type.primitive] = type
                    CLASS_MAP[type.reference] = type
                }
            }

            /**
             * Returns the data type with the given primitive/reference class
             *
             * @param clazz Primitive/Reference class of the data type
             * @return The data type
             */
            fun fromClass(clazz: Class<*>?): DataType? {
                return CLASS_MAP[clazz]
            }

            /**
             * Returns the primitive class of the data type with the given reference class
             *
             * @param clazz Reference class of the data type
             * @return The primitive class
             */
            fun getPrimitive(clazz: Class<*>): Class<*> {
                val type = fromClass(clazz)
                return if (type == null) clazz else type.primitive!!
            }

            /**
             * Returns the reference class of the data type with the given primitive class
             *
             * @param clazz Primitive class of the data type
             * @return The reference class
             */
            fun getReference(clazz: Class<*>?): Class<*>? {
                val type = fromClass(clazz)
                return type?.reference ?: clazz
            }

            /**
             * Returns the primitive class array of the given class array
             *
             * @param classes Given class array
             * @return The primitive class array
             */
            fun getPrimitive(classes: Array<Class<*>>?): Array<Class<*>> {
                val length = classes?.size ?: 0
                val types: Array<Class<*>> = arrayOfNulls(length)
                for (index in 0 until length) {
                    types[index] = getPrimitive(classes!![index])
                }
                return types
            }

            /**
             * Returns the reference class array of the given class array
             *
             * @param classes Given class array
             * @return The reference class array
             */
            fun getReference(classes: Array<Class<*>?>?): Array<Class<*>?> {
                val length = classes?.size ?: 0
                val types = arrayOfNulls<Class<*>?>(length)
                for (index in 0 until length) {
                    types[index] = getReference(classes!![index])
                }
                return types
            }

            /**
             * Returns the primitive class array of the given object array
             *
             * @param objects Given object array
             * @return The primitive class array
             */
            fun getPrimitive(objects: Array<Any>?): Array<Class<*>> {
                val length = objects?.size ?: 0
                val types: Array<Class<*>> = arrayOfNulls(length)
                for (index in 0 until length) {
                    types[index] = getPrimitive(objects!![index].javaClass)
                }
                return types
            }

            /**
             * Returns the reference class array of the given object array
             *
             * @param objects Given object array
             * @return The reference class array
             */
            fun getReference(objects: Array<Any>?): Array<Class<*>?> {
                val length = objects?.size ?: 0
                val types = arrayOfNulls<Class<*>?>(length)
                for (index in 0 until length) {
                    types[index] = getReference(objects!![index].javaClass)
                }
                return types
            }

            /**
             * Compares two class arrays on equivalence
             *
             * @param primary Primary class array
             * @param secondary Class array which is compared to the primary array
             * @return Whether these arrays are equal or not
             */
            fun compare(primary: Array<Class<*>>?, secondary: Array<Class<*>>?): Boolean {
                if (primary == null || secondary == null || primary.size != secondary.size) {
                    return false
                }
                for (index in primary.indices) {
                    val primaryClass = primary[index]
                    val secondaryClass = secondary[index]
                    if (primaryClass == secondaryClass || primaryClass.isAssignableFrom(secondaryClass)) {
                        continue
                    }
                    return false
                }
                return true
            }
        }
    }
}