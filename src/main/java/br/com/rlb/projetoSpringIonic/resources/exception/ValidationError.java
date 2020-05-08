package br.com.rlb.projetoSpringIonic.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;
	

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String message, Long timestamp) {
		super( status, message, timestamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}

}
