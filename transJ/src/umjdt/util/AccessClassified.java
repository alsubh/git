package umjdt.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;


public interface AccessClassified 
{
	// public method signatures from structural view
	AccessType getAccessKind(Method m);
}
