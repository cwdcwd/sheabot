package com.lazybaer.sheabot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class SheaBot
{

	/**
	 * @param args
	 */
	
	public SheaBot()
	{
		try
		{
			Properties prop = new Properties();
			InputStream is=getClass().getResourceAsStream("/config.properties");
			prop.load(is);
			
			Connection connection = new XMPPConnection("gmail.com");
			System.out.println("firing up conn...");
			connection.connect();
			String strUser=prop.getProperty("username");
			System.out.println("logging in as: "+strUser);
			connection.login(strUser, prop.getProperty("password"));
			Chat chat = connection.getChatManager().createChat("j.fidlow@gmail.com", new MessageListener() {

			    public void processMessage(Chat chat, Message message) {
			        System.out.println("Received message: " + message);
			    }
			});
			chat.sendMessage("Howdy!");
			
		} catch (XMPPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args)
	{
		new SheaBot();		
	}

}
