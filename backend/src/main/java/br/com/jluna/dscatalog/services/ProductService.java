package br.com.jluna.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jluna.dscatalog.dto.ProductDTO;
import br.com.jluna.dscatalog.entities.Product;
import br.com.jluna.dscatalog.repositories.CategoryRepository;
import br.com.jluna.dscatalog.repositories.ProductRepository;
import br.com.jluna.dscatalog.services.exceptions.DatabaseException;
import br.com.jluna.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository catRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPagened(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest);
		return list.map(p -> new ProductDTO(p, p.getCategory()));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		Optional<Product> product = repository.findById(id);
		Product entity = product.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

		return new ProductDTO(entity, entity.getCategory());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		var entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {
			Product entity = repository.getOne(id); // getOne = instancia um objeto sem ir ao banco
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found " + id);
		}

	}

	@Transactional
	public void delete(Long id) {

		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}

	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());

		entity.getCategory().clear();

		dto.getCategories().forEach(c -> entity.getCategory().add(catRepository.getOne(c.getId())));

	}

}
