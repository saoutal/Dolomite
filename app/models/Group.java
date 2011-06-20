package models;
import java.util.Enumeration;
import java.util.List;
import java.util.Iterator;
import notifiers.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import models.Sign;
import models.*;
import play.mvc.*;
import play.libs.Crypto;
import play.data.validation.*;
import play.*;
import play.i18n.Messages;



public class Group {



	public void sendMsgGroup(String from, String message, String subject, String groupName){
		Ldap adminConnection = new Ldap();
		adminConnection.SetEnv(Play.configuration.getProperty("ldap.host"),Play.configuration.getProperty("ldap.admin.dn"), Play.configuration.getProperty("ldap.admin.password"));
		
		ArrayList<String> members = new ArrayList<String>();
		members = LdapGroup.retrieve(groupName).getMembers();
		System.out.println("!!!!!!!avant boucle");
		//for(String member: members){
		Iterator<String> it = members.iterator();
		String member;
		System.out.println("SIZEEEEEUUU " + members.size());
		while(it.hasNext()) {
			member = it.next();
			System.out.println(member);
			member = member.substring(member.indexOf("=")+1, member.indexOf(","));
			System.out.println(member);
			Attributes att=adminConnection.getUserInfo(adminConnection.getLdapEnv(),member);
			String to = att.get("mail").toString();
			to = to.substring(to.indexOf(" ")+1);
			
			System.out.println("!!!!!A QUI ? " +to);
			Mails.sendMsg (from, to, message, subject);
	    }
		System.out.println("!!!!!!!!apres boucle");
		//sssssssssssssssssss

	}


}



