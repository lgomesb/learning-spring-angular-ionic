package com.learning.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {

		this.cod = cod;
		this.descricao = descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		
		if( cod == null ) {
			return null;
		}
		
		for (Perfil item : Perfil.values()) {
			if(cod.equals(item.getCod())) {
				return item;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
