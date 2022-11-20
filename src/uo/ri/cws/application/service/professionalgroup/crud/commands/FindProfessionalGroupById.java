package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProfessionalGroupById
		implements Command<Optional<ProfessionalGroupBLDto>> {

	private String id;
	private ProfessionalGroupRepository repo = Factory.repository
			.forProfessionalGroup();

	public FindProfessionalGroupById(String id) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotBlank(id, "Empty");
		this.id = id;
	}

	@Override
	public Optional<ProfessionalGroupBLDto> execute() throws BusinessException {
		return repo.findById(id).map(m -> DtoAssembler.toDto(m));
	}

}
