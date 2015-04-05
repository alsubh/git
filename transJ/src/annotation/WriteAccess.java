package annotation;

import java.lang.annotation.*;

/**
 * Indicates that the annotated method 
 * will perform Write operations only.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface WriteAccess { }
