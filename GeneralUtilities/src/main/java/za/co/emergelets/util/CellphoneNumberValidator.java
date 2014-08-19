package za.co.emergelets.util;

import java.util.Arrays;
import java.util.List;

public final class CellphoneNumberValidator {
	
	public static final int VALID_CELL_PHONE_NUMBER_LENGTH = 11;
	
	private static final Integer[] CELLPHONE_NUMBER_PREFIXES = 
				{ 2771, 2772, 2773, 2774, 2781, 2782, 2783, 2784,
		          2760, 2761, 2778, 2776, 2779 };
	
        /**
         * Checks if the Mobile Number is a valid South African mobile
         * number
         * 
         * @param cellphoneNumber - The mobile number
         * @return 
         */
	public static boolean isCellphoneNumberValid(String cellphoneNumber) {
		
		if (cellphoneNumber == null || cellphoneNumber.length() != VALID_CELL_PHONE_NUMBER_LENGTH)
			return false;
		
		// check that it does not have any FIVE consecutive digits
		// that are the same
		int consecutiveCount = 0;
		String currentDigit = "",
			   previousDigit = "";
		
		for (int i = 0; i < cellphoneNumber.length(); i++) {
			currentDigit = Character.toString(cellphoneNumber.charAt(i));
			
			if (currentDigit.equals(previousDigit)) {
				consecutiveCount++;
				

				if (consecutiveCount >= 4) 
					return false;
			}
			else
				consecutiveCount = 0;
			
			previousDigit = currentDigit;
		}
		
		
		// check if the number has a valid prefix
		String prefix = cellphoneNumber.substring(0, 4);
		List<Integer> validPrefixes = Arrays.asList((Integer[])CELLPHONE_NUMBER_PREFIXES);
		
		if (!validPrefixes.contains(Integer.parseInt(prefix))) {
			return false;
		}
		
		return true;
	}
	
}
