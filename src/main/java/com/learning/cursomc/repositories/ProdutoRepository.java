package com.learning.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.learning.cursomc.domain.Categoria;
import com.learning.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

//	@Query("SELECT DISTINCT prod FROM Produto prod INNER JOIN prod.categorias cat WHERE prod.name LIKE %:name% AND cat IN :categorias")
//	Page<Produto> search(@Param("name") String name, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNameContainingAndCategoriasIn(String name, List<Categoria> categorias, Pageable pageRequest);	
	
}
