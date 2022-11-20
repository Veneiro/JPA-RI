package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;

public class ListMechanicsWithAValidContract
		implements Command<List<MechanicDto>> {

	private MechanicRepository repo = Factory.repository.forMechanic();

	@Override
	public List<MechanicDto> execute() {
		// List<Mechanic> res = repo.findWithValidContract();

		return null;
		// DtoAssembler.toMechanicDtoList(res);
	}

}
