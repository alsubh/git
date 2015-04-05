package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LockableClass 
{
	public Class<? extends java.util.concurrent.locks.ReadWriteLock> value() default java.util.concurrent.locks.ReentrantReadWriteLock.class;
}