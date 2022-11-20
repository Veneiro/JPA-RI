package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateProfessionalGroup implements Command<Void> {

	private ProfessionalGroupBLDto dto;
	private ProfessionalGroupRepository repo = Factory.repository
			.forProfessionalGroup();

	public UpdateProfessionalGroup(ProfessionalGroupBLDto pg) {
		ArgumentChecks.isNotNull(pg);
		ArgumentChecks.isNotNull(pg.name, "The name is Null");
		ArgumentChecks.isNotEmpty(pg.name.trim(), "The name is Empty");
		ArgumentChecks.isTrue(pg.productivityRate >= 0);
		ArgumentChecks.isTrue(pg.trieniumSalary >= 0);
		this.dto = pg;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroup> om = repo.findByName(dto.name);
		BusinessChecks.exists(om, "The mechanic does not exist");

		ProfessionalGroup pg = om.get();
		BusinessChecks.hasVersion(pg, pg.getVersion());

		pg.setName(dto.name);
		pg.setProductivityRate(dto.productivityRate);
		pg.setTrienniumSalary(dto.trieniumSalary);

		return null;
	}

}
