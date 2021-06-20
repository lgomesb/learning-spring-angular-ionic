package com.learning.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.cursomc.domain.enums.Perfil;
import com.learning.cursomc.domain.enums.TipoCliente;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String cpfOuCnpf;
	private Integer tipo;
	
	@JsonIgnore
	private String senha; 
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos =  new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	public Cliente() {
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String name, String email, String cprOuCnpf, TipoCliente tipo, String senha) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOuCnpf = cprOuCnpf;
		this.tipo = (tipo == null) ? null : tipo.getCod();
		this.senha = senha; 
		addPerfil(Perfil.CLIENTE);
	} 
	
	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}
	
	public void setTipo( TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}
	
	public Set<Perfil> getPerfils() {
		return perfis.stream().map(p -> Perfil.toEnum(p)).collect(Collectors.toSet()) ;
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}


	
}
