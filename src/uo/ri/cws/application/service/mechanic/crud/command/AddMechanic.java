package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		this.dto = dto;
	}

	@Override
	public MechanicDto execute() throws BusinessException {
		BusinessChecks.isTrue(repo.findByDni(dto.dni).isEmpty(),
				"The mechanic already exists");
		BusinessChecks.isNotEmpty(dto.name.trim(), "The name is empty");
		BusinessChecks.isNotEmpty(dto.surname, "The surname is empty");

		Mechanic m = new Mechanic(dto.dni, dto.surname, dto.name);

		repo.add(m);

		return DtoAssembler.toDto(m);
	}

}
