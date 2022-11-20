package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteProfessionalGroup implements Command<Void> {

	private String profId;
	private ProfessionalGroupRepository repo = Factory.repository
			.forProfessionalGroup();

	public DeleteProfessionalGroup(String profId) {
		ArgumentChecks.isNotBlank(profId);
		this.profId = profId;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroup> om = repo.findById(profId);
		BusinessChecks.exists(om);
		ProfessionalGroup pg = om.get();
		repo.remove(pg);

		return null;
	}

}
