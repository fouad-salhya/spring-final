package com.products.services;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.products.entities.OrderEntity;
import com.products.entities.ProductEntity;
import com.products.entities.ReservationEntity;
import com.products.entities.UserEntity;
import com.products.repository.OrderRepository;
import com.products.repository.ProductRepository;

import jakarta.mail.internet.MimeMessage;


@Service
public class PaymentService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderRepository orderRepository;

	

	private static BraintreeGateway gateway = new BraintreeGateway(
			  Environment.SANDBOX,
			  "fr8ww8xycwk23y7t",
			  "f3tqsn9y7mzvnzt3",
			  "8e03f71fb87e4fa227368ca82e2db55e"
			);
	
	public String generateToken() {
		
			ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
			  
			String clientToken = gateway.clientToken().generate(clientTokenRequest);
			
			return clientToken;
		
		
	}
	
	 public String processPayment(String nonce, BigDecimal amount, long id) {
		 
		 Optional<ProductEntity> product = productRepository.findById(id);
		 
		 OrderEntity order = new OrderEntity();
		 
		 order.setAmount(amount);
		 order.setProductName(product.get().getName());
		 
	        TransactionRequest request = new TransactionRequest()
	                .amount(amount)
	                .paymentMethodNonce(nonce)
	                .options()
	                .submitForSettlement(true)
	                .done();

	        Result<Transaction> result = gateway.transaction().sale(request);

	        if (result.isSuccess()) {
	        	
	        	orderRepository.save(order);
	        	
	            return result.getTarget().getId();
	        } else {
	            return "Erreur de paiement: " + result.getMessage();
	        }
	
	 }
	 
	 private void sendConfirmationEmail( Optional<ProductEntity>  product, BigDecimal amount) {
	        MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        try {
	        	
	        	
	        	
	            helper.setTo("fouad.salhya@uit.ac.ma");
	            helper.setSubject("Confirmation de payment");
	            
	            String htmlContent = "<h4>meel " + product.get().getName()  + ",</h4>" +
	                  
	                    "<h5>Merci pour votre réservation.</h5>"+
	                    "<p>Nombre de personnes: " + product.get().getPrix() + "</p>" +
	                    "<h5>date et time" + amount + ".</h5>" +
	                    "<p>Nous sommes impatients de vous accueillir </p>";

	            helper.setText(htmlContent, true);
	        } catch (Exception e) {
	            e.printStackTrace(); //e
	        }
	        try {
	            javaMailSender.send(message);
	        } catch (MailException e) {
	            e.printStackTrace(); 
	        }
	    }
}


