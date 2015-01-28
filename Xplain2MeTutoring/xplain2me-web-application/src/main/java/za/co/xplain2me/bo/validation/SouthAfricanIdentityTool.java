package za.co.xplain2me.bo.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SouthAfricanIdentityTool {
	
	public static final int SA_ID_NUMBER_LENGTH = 13;
	private static final String YYMMDD_DATE_FORMAT = "yyMMdd";
	
	/**
	 * Validates the South African Identity Number
	 * by using the checksum algorithm.
	 * 
	 * @param idNumber
	 * @return
	 */
	public static boolean isValid(String idNumber) {
		
		if (idNumber == null || idNumber.length() != SA_ID_NUMBER_LENGTH) {
			return false;
		}
		
		int i = 0;
		
		// (a) Add all the digits in the odd positions (excluding last digit).
		int A = 0;
		for (i = 0; i < idNumber.length() - 1 ; i++) {
			
			if ((i + 1) % 2 != 0)
				A += Integer.parseInt(((Character)idNumber.charAt(i)).toString());
		}
		
		// (b) Move the even positions into a field and multiply the number by 2
		String BString = "";
		int B = 0;
		for (i = 0; i < idNumber.length(); i++) {
			if ((i + 1) % 2 == 0)
				BString += ((Character)idNumber.charAt(i)).toString();
		}
		
		B = Integer.parseInt(BString) * 2;
		
		// (c) Add the digits of the result in b).
		int C = 0;
		for (i = 0; i < String.valueOf(B).length(); i++) {
			C += Integer.parseInt(((Character)String.valueOf(B).charAt(i)).toString());
		}
		
		// (d) Add the answer in [c] to the answer in [a]
		int D = A + C;
		
		// the checksum digit
		int checksum = Integer.parseInt(((Character)idNumber
							.charAt(SA_ID_NUMBER_LENGTH - 1)).toString());
		int E = 10 - (D % 10);
		
		// ID number is valid if the checksum is equal to E
		return (checksum == E);
	}
	
	/**
	 * Gets the date of birth from a valid South African
	 * Identity Number
	 * 
	 * @param idNumber
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateOfBirth(String idNumber) throws ParseException {
		if (!isValid(idNumber)) 
			return null;
		
		DateFormat format = new SimpleDateFormat(YYMMDD_DATE_FORMAT);
		return format.parse(idNumber.substring(0, 6));
		
	}

	/**
	 * Extracts the citizenship from a valid South African
	 * Identity Number.
	 * 
	 * @param idNumber
	 * @return
	 */
	public static CitizenshipType getCitizenship(String idNumber) {
		
		if (!isValid(idNumber))
			return null;
		
		int citizenship = Integer.parseInt(((Character)idNumber.charAt(10)).toString());
		if (citizenship == 0)
			return CitizenshipType.SouthAfrican;
		else
			return CitizenshipType.Other;
		
	}
}