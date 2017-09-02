package com.prothreading.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.prothreading.services.MailGunSender;
import com.prothreading.util.VerifyUtils;

@WebServlet("/EmailController")
public class EmailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		if(request.getParameter("test") != null)
		{
			MailGunSender sender = new MailGunSender();
			sender.setSubject("THis is second successfull test");
			sender.setFromEmail("kundanparajuli@gmail.com");
			sender.setBody("this looks the final test ");
			try {
				sender.send();
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String g_response = request.getParameter("g-recaptcha-response");
		if(g_response.equals("") || g_response == null){
			out.print("failed");
		}else{
		
			boolean verrified = VerifyUtils.verify(g_response);
			
			if(verrified)
			{
				MailGunSender sender = new MailGunSender();
				sender.setSubject(request.getParameter("subject"));
				sender.setFromEmail(request.getParameter("fromEmail"));
				
				String body = request.getParameter("message");
				body += "\n\rSender Name: "+ request.getParameter("fullName");
				body += "\n\rPhone Number: "+ request.getParameter("phone");
				
				sender.setBody(body);
			
				try {
					sender.send();
					out.print("success");
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}else{
				out.print("reset");
			}
		
		}
		
	}

}
