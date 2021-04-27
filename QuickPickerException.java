package edu.cuny.csi.csc330.lab6;

public class QuickPickerException extends Exception {

	public static int UNSET = 0; 
	public static int INVALID_FILE = 1;
	public static int INVALID_FILENAME = 2;
	public static int MISSING_PROPERTY = 3;
	
	protected int code;
	
	public static String[] MESSAGE = {
			"Code Unspecified",
			".propertise file was NOT PASSED",
			".propertise file name NOT FOUND",
			"Required property is missing from property file",
			"The <GameName> property is missing from specified file.",
			"The <PoolSelection1> property is missing from specified file.",
			"The <PoolSize1> property is missing from specified file.",
			"The <PoolSelection2> property is missing from specified file.",
			"The <PoolSize2> property is missing from specified file.",
			"The <Vendor> property is missing from specified file."
	};

	public QuickPickerException() {
	}
	
    public QuickPickerException(String message) { 
    	super(message); 
    } 
	
    public QuickPickerException(String message, int code) { 
    	super(message);
    	this.code = code;
    } 
	
	public int getCode() { 
		return code;
	}

	@Override
	public String toString() {
		return "QuickPickerException [code=" + code + ", toString()=" + super.toString() + "]\n"
				+ MESSAGE[code];
	}

	public static void main(String[] args) {
		
		QuickPickerException pickExc = new QuickPickerException("QuickPicker Exception message ... ");
		System.out.println("Ex: " + pickExc);

		QuickPickerException pickExc1 = new QuickPickerException("QuickPicker Exception message ... ", QuickPickerException.INVALID_FILE);
		System.out.println("Ex: " + pickExc1);
		
		QuickPickerException pickExc2 = new QuickPickerException("QuickPicker Exception message ... ", QuickPickerException.INVALID_FILENAME);
		System.out.println("Ex: " + pickExc2);
		
		QuickPickerException pickExc3 = new QuickPickerException("QuickPicker Exception message ... ", QuickPickerException.MISSING_PROPERTY);
		System.out.println("Ex: " + pickExc3);
		
	}

}
