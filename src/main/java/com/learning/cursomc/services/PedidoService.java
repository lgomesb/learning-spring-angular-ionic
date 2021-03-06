package com.learning.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.domain.ItemPedido;
import com.learning.cursomc.domain.PagamentoComBoleto;
import com.learning.cursomc.domain.Pedido;
import com.learning.cursomc.domain.enums.EstadoPagamento;
import com.learning.cursomc.repositories.ItemPedidoRepository;
import com.learning.cursomc.repositories.PagamentoRepository;
import com.learning.cursomc.repositories.PedidoRepository;
import com.learning.cursomc.resources.exception.AuthorizationException;
import com.learning.cursomc.resources.utils.BoletoService;
import com.learning.cursomc.security.UserSecurity;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository ItemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = repository.findById(id);		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido pedido) throws Exception {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		
		pedido = repository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		
		ItemPedidoRepository.saveAll(pedido.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(pedido); 
		
		return pedido;
	}
	
	public Page<Pedido> findPage( Integer page, Integer linesPerPage, String orderBy, String direction) throws Exception {
		UserSecurity user = UserService.authenticated();
		if( user == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repository.findByCliente(cliente, pageRequest);
		
	}
	
}
