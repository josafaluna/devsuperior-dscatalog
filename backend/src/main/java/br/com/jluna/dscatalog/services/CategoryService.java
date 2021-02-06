package br.com.jluna.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jluna.dscatalog.dto.CategoryDTO;
import br.com.jluna.dscatalog.entities.Category;
import br.com.jluna.dscatalog.repositories.CategoryRepository;
import br.com.jluna.dscatalog.services.exceptions.DatabaseException;
import br.com.jluna.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		List<CategoryDTO> listDTO = list.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());
		return listDTO;
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> opt = repository.findById(id);
		Category cat = opt.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada."));
		return new CategoryDTO(cat);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category category = new Category();
		category.setName(dto.getName());

		category = repository.save(category);
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id); // getOne = instancia um objeto sem ir ao banco
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found " + id);
		}
	}

	public void delete(Long id) {

		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found " + id);

		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}

	}

}
