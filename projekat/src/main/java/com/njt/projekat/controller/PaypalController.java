package com.njt.projekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.njt.projekat.entity.Order;
import com.njt.projekat.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {

	@Autowired
	private PaypalService paypalService;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@PostMapping("/paypal-pay")
	public String paymentProcessing(@RequestBody Order order) {
		System.out.println(order);
		try {
			Payment payment = paypalService.createPayment(order.getTotalPrice(), order.getCurrency(), order.getPaymentMethod(),
					"http://localhost:8080/"+CANCEL_URL, "http://localhost:8080/"+SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					return "redirect:" + link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "redirect:/checkout";
	}

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "/";
	}
	
	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				return "success";
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "redirect:/checkout";
	}
}
