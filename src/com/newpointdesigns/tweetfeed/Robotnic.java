package com.newpointdesigns.tweetfeed;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

public class Robotnic {

	private Connection connection;
	private Properties settings;

	public Robotnic() {
		this.settings = this.getSettings();
		this.connect();
		// TODO check if target is subscribed
	}

	private Properties getSettings() {
		Properties prop = new Properties();
		InputStream is = getClass().getResourceAsStream("/config.properties");
		try {
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	private void connect() {
		String strUser = this.settings.getProperty("username");
		String strPass = this.settings.getProperty("password");
		String strServer = this.settings.getProperty("jabber_server");
		try {
			this.connection = new XMPPConnection(strServer);
			this.connection.connect();
			this.connection.login(strUser, strPass);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	private Boolean isSubscribed(String strUsename) {
		// TODO iterate through roster, returning existence of target
		return Boolean.FALSE;
	}
	
	private void subscribeRequest() {
		String strTarget = this.settings.getProperty("target");
		Presence subscribe = new Presence(Presence.Type.subscribe);
		subscribe.setTo(strTarget);
		this.connection.sendPacket(subscribe);
	}
	
	private Collection<RosterEntry> getRoster() {
		Roster roster = connection.getRoster();
		return roster.getEntries();
	}

	public void sendMessage(String strMsg) {
		String strTarget = this.settings.getProperty("target");
		// TODO check if target is online
		Chat chat = this.connection.getChatManager().createChat(
				strTarget, new MessageListener() {
					public void processMessage(Chat chat, Message message) {
						System.out.println("Received message: " + message);
					}
				});
		try {
			chat.sendMessage(strMsg);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Robotnic robo = new Robotnic();
		robo.sendMessage("#thanksobama");
	}

}
