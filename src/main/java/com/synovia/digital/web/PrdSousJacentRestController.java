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

import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.service.PrdSousJacentService;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 2 févr. 2017
 */
@RestController
@RequestMapping("/api/prdSousJacents")
public class PrdSousJacentRestController {

	protected final PrdSousJacentService service;

	@Autowired
	public PrdSousJacentRestController(PrdSousJacentService service) {
		this.service = service;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody PrdSousjacentDto inputDto) {
		System.out.println("PrdSousJacentRestController.add()");
		ResponseEntity<?> response = null;
		try {
			PrdSousJacent result = service.add(inputDto);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
					.toUri();
			return ResponseEntity.created(location).build();

		} catch (EavTechnicalException e) {
			response = ResponseEntity.badRequest().build();
		}

		return response;
	}

}
