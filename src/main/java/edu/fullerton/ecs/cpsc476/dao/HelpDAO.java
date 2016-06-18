package edu.fullerton.ecs.cpsc476.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class HelpDAO
{		
	public Timestamp getTime()
	{
		java.util.Date date= new java.util.Date();
		return new Timestamp(date.getTime());
	}
	
	public String getMd5(String pw)
	{
		String md5 = null;
		try {
            
	        //Create MessageDigest object for MD5
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	         
	        //Update input string in message digest
	        digest.update(pw.getBytes(), 0, pw.length());
	 
	        //Converts message digest value in base 16 (hex)
	        md5 = new BigInteger(1, digest.digest()).toString(16);
	        pw = md5;
	        } catch (NoSuchAlgorithmException e) {
	 
	            System.out.println(e);
	        }
		return md5;
	}

}
