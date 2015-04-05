package annotation;
/**
 * Marks a method as AccessClassified for the AspectOPTIMA Framework<br>
 * The parameter value() should provide the kind of access (i.e. AccessKind.READ,
 * AccessKind.WRITE, AccessKind.UPDATE).<br>
 */
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import umjdt.util.AccessType;


@Target( { ElementType.METHOD })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessClassifiedMethod 
{
	AccessType value();
}