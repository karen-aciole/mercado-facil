package com.ufcg.psoft.mercadofacil.exception;

public class UsuarioAlreadyExists extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UsuarioAlreadyExists (String msg) {
		super(msg);
	}

}
