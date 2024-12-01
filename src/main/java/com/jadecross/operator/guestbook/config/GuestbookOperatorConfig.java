package com.jadecross.operator.guestbook.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jadecross.operator.guestbook.GuestbookReconciler;

import io.javaoperatorsdk.operator.Operator;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;

@Configuration
public class GuestbookOperatorConfig {

	@Bean
	public GuestbookReconciler guestbookReconciler() {
		return new GuestbookReconciler();
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Operator operator(List<Reconciler<?>> controllers) {
		Operator operator = new Operator();
		controllers.forEach(operator::register);
		return operator;
	}
}