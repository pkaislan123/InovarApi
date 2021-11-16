package com.inovar.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.inovar.email.EMail;
import freemarker.template.Configuration;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("v1")
public class EmailController {

	 @Autowired     
	 Configuration fmConfiguration;
	 
	 
    @Autowired 
    private JavaMailSender mailSender;

    /*
    @ApiOperation(value = "Envia um E-mail de Confirmação de Cadastro")
	@PostMapping(path = "protected/email/enviarconfirmacao")
    public int sendEmailWithTemplate(@RequestBody Cliente cliente) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {

        	
        	EMail mail = new EMail();
            
        	mail.setTo(cliente.getEmail());
        	mail.setFrom("comprasaislan@gmail.com");
        	mail.setSubject("Código de Confirmação de Cadastro");
        	mail.setContent("Sending mail");
             Map<String, Object> model = new HashMap<>();
             model.put("firstName", cliente.getNome().trim());
             model.put("lastName", cliente.getSobrenome().trim());
             model.put("codigo", cliente.getCodigo_ativacao() + "");

             mail.setModel(model);
        	
        	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setTo(mail.getTo());
            mail.setContent(geContentFromTemplate(mail.getModel()));
            mimeMessageHelper.setText(mail.getContent(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
            

            return 1;
        } catch (MessagingException e) {
        	 e.printStackTrace();
             return 0;
        }
    }*/
    
    public String geContentFromTemplate(Map < String, Object >model)     { 
        StringBuffer content = new StringBuffer();
 
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
 
    


    
}
