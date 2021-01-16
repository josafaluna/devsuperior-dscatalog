package br.com.jluna.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jluna.dscatalog.entities.Category;
import br.com.jluna.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository respository;

	@Transactional(readOnly = true)
	public List<Category> findAll() {
		List<Category> list = respository.findAll();
		return list;
	}

}
