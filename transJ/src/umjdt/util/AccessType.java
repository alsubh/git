package umjdt.util;

//public interface AccessType
//{
//	public static final String READ = "READ";
//	public static final String WRITE = "WRITE";
//	public static final String UPDATE = "UPDATE";
//}


public enum AccessType 
{ 
	// enumeration literals mentioned in structural view
	READ {
		@Override
		public String toString() {
			return "Read";
		}
	}, 
	
	WRITE {
		@Override
		public String toString() {
			return "Write";
		}
	}, 
	
	UPDATE {
		@Override
		public String toString() {
			return "Update";
		}
	};
}