/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Transport;
/**
 *
 * @author Ismael
 */
@Stateless

public class CorreoBean {
    
    
   
    

    public void enviarCorreo(String destino, String asunto,String mensaje){
    try {
        
      
   
        
        
    Properties props = System.getProperties();
    props.put("mail.smtp.host","smtp.gmail.com");
    props.put("mail.smtp.auth","true");
    props.put("mail.smtp.port","465");
    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.port","465");
    props.put("mail.smtp.socketFactory.fallback","false");
    
    
    Session sesionCorreo = Session.getDefaultInstance(props,null);
    sesionCorreo.setDebug(false);
    
    Message mensajeCorreo =new MimeMessage(sesionCorreo);
    String origen="teamalexduoc@gmail.com";
    mensajeCorreo.setFrom(new InternetAddress(origen));
    mensajeCorreo.setRecipient(Message.RecipientType.TO,new InternetAddress(destino));
        mensajeCorreo.setContent(mensaje,"text/html");
        mensajeCorreo.setSubject(asunto);
        Transport transporte = sesionCorreo.getTransport(destino);
        transporte.connect("smtp.gmail.com","teamalexduoc","Alex123456789");
        
       
        
        
        } catch (MessagingException ex) {
            Logger.getLogger(CorreoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
}
