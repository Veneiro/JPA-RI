package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProfessionalGroupByName
		implements Command<Optional<ProfessionalGroupBLDto>> {

	private String name;
	private ProfessionalGroupRepository repo = Factory.repository
			.forProfessionalGroup();

	public FindProfessionalGroupByName(String name) {
		ArgumentChecks.isNotNull(name);
		ArgumentChecks.isNotBlank(name, "Blank name");
		this.name = name;
	}

	@Override
	public Optional<ProfessionalGroupBLDto> execute() throws BusinessException {
		return repo.findByName(name).map(m -> DtoAssembler.toDto(m));

	}

}
