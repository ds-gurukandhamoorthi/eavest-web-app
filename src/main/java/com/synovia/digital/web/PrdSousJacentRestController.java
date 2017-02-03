/**
 * 
 */
package com.synovia.digital.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.synovia.digital.domain.PrdSousJacent;
import com.synovia.digital.repository.PrdSousJacentRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 2 févr. 2017
 */
@RestController
@RequestMapping("/api/prdSousJacents")
public class PrdSousJacentRestController {

	@Autowired
	protected PrdSousJacentRepository repo;

	public PrdSousJacentRestController(PrdSousJacentRepository ssjacentRepo) {
		this.repo = ssjacentRepo;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody PrdSousJacent input) {
		System.out.println("PrdSousJacentRestController.add()");
		PrdSousJacent result = repo.save(new PrdSousJacent(input.getLabel(), input.getValue()));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
}
