package br.com.jluna.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jluna.dscatalog.dto.ProductDTO;
import br.com.jluna.dscatalog.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductResource {

	@Autowired
	private ProductService service;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllPagened(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "linesPerPage", defaultValue = "15") Integer linesPerPage,
			@RequestParam(name = "directioin", defaultValue = "ASC") String direction,
			@RequestParam(name = "orderBy", defaultValue = "name") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return ResponseEntity.ok(service.findAllPagened(pageRequest));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {

		var product = service.insert(dto);

		// URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(product);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
