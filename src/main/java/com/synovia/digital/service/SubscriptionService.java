/**
 * 
 */
package com.synovia.digital.service;

import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 7 févr. 2017
 */
public interface SubscriptionService {

	/**
	 * Sends an email to verify an account.
	 * 
	 * @param to
	 *            The e-mail address to send the mail to.
	 * @param account
	 *            The account to verify.
	 */
	public void sendMailVerifyAccount(String to, EavAccount account);

	/**
	 * Sends an email to inform of the creation of a new account.
	 * 
	 * @param to
	 *            The e-mail addresses to send the mail to.
	 * @param account
	 *            The new account.
	 */
	public void sendMailAccountCreated(String[] to, EavAccount account);

	/**
	 * Sends an email to reset a user password.
	 * 
	 * @param to
	 *            The e-mail address of the user.
	 */
	public void sendMailResetPassword(String to, String secureToken);

}
