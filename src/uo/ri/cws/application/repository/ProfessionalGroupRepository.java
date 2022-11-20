package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.ProfessionalGroup;

public interface ProfessionalGroupRepository
		extends Repository<ProfessionalGroup> {

	Optional<ProfessionalGroup> findByName(String name);

	List<ProfessionalGroup> findAll();
}
