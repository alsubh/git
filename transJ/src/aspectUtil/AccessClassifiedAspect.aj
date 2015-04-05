/**
 * 
 */
package aspectUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import annotation.AccessClassifiedMethod;
import umjdt.util.AccessClassified;
import umjdt.util.AccessType;

/**
 * @author AnasR
 *
 */
aspect AccessClassifiedAspect 
{
	// inner type declaration 
	public Map<Method, AccessType> AccessClassified.methodToAccessTypeMap = new HashMap<Method, AccessType>();
	
	after(): initialization(AccessClassified+.new(..))
	{
		for(Method method : thisJoinPoint.getSignature().getDeclaringType().getDeclaredMethods()) 
		{
			AccessClassifiedMethod methodAnnotation = method.getAnnotation(AccessClassifiedMethod.class);
			if (methodAnnotation != null) 
			{
				AccessType accessKind = methodAnnotation.value();
				AccessClassified accessClassifiedObject = (AccessClassified) thisJoinPoint.getThis();
				accessClassifiedObject.methodToAccessTypeMap.put(method, accessKind);
			}
		}
	}

	public AccessType AccessClassified.getAccessKind(Method m) 
	{
		return methodToAccessTypeMap.get(m);
	}
}