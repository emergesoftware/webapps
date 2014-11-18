package za.co.emergelets.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailAddressValidator {
	
	/**
	 * The minimum number of characters allowed for
	 * an email address.
	 */
	public static final int MINIMUM_NUMBER_OF_CHARACTERS = 7;
	
	/**
	 * The regular expression for matching an
	 * email address
	 */
	private static final String REGULAR_EXPRESSION = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9]+)*@" + 
			"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	//Propose that you change that to this 
	//^(?!\.)(""([^""\r\\]|\\[""\r\\])*""|([-a-z0-9!#$%&'*+/=?^_`{|}~]|
	//(?<!\.)\.)*)(?<!\.)@[a-z0-9][\w\.-]*[a-z0-9]\.[a-z][a-z\.]*[a-z]$
	//as per http://haacked.com/archive/2007/08/21/i-knew-how-to-validate-an-email-address-until-i.aspx
	
	
	/**
	 * Determines if the email address is valid
	 * when matched against the regular expression.
	 * 
	 * @param emailAddress
	 * @return
	 */
	public static final boolean isEmailAddressValid(String emailAddress) {
		if (emailAddress == null || emailAddress.length() < MINIMUM_NUMBER_OF_CHARACTERS)
			return false;
		
		Pattern pattern = Pattern.compile(REGULAR_EXPRESSION);
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
		
	}
	
}
