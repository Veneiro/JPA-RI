package uo.ri.cws.application.service.professionalgroup.crud.commands;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class AddProfessionalGroup implements Command<ProfessionalGroupBLDto> {

	private ProfessionalGroupBLDto dto;
	private ProfessionalGroupRepository repo = Factory.repository
			.forProfessionalGroup();

	public AddProfessionalGroup(ProfessionalGroupBLDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotNull(dto.name, "The name is Null");
		ArgumentChecks.isNotEmpty(dto.name.trim(), "The name is Empty");
		ArgumentChecks.isTrue(dto.productivityRate >= 0);
		ArgumentChecks.isTrue(dto.trieniumSalary >= 0);
		this.dto = dto;
	}

	@Override
	public ProfessionalGroupBLDto execute() throws BusinessException {
		BusinessChecks.isTrue(repo.findByName(dto.name).isEmpty(),
				"The mechanic already exists");
		ProfessionalGroup pg = new ProfessionalGroup(dto.name,
				dto.productivityRate, dto.trieniumSalary);
		repo.add(pg);
		return DtoAssembler.toDto(pg);
	}

}
