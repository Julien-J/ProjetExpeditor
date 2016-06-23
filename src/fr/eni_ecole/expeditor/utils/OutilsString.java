/**
 * 
 */
package fr.eni_ecole.expeditor.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * ProjectExpeditor Version 1.0
 * @author d1408davidj
 * 21 juin 2016
 *
 */
public class OutilsString {
	static final String dictionnaire = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	/**
	 * Méthode en charge de convertir une valeur en sa valeur MD5 
	 * @param input Valeur à convertir
	 * @return La valeur en MD5
	 * @throws NoSuchAlgorithmException 
	 */
	public static String convertTOMD5(String input) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(input.getBytes());

	    byte byteData[] = md.digest();

	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < byteData.length; i++)
	        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    
	    return sb.toString();
	}

	public static String generatePassword() {
		Random rdm = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < 8 ; i++){
			sb.append(dictionnaire.charAt(rdm.nextInt(dictionnaire.length())));
		}
		
		return sb.toString();
	}
	
}
