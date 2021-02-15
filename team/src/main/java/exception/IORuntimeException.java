package exception;

public class IORuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 4362278528085871568L;

	public IORuntimeException() {
		super();
	}
	
	public IORuntimeException(String message){
		super(message);
	}
	
	public IORuntimeException(String message,Throwable cause){
		super(message,cause);
	}
	
	public IORuntimeException(Throwable cause){
		super(cause);
	}

}
