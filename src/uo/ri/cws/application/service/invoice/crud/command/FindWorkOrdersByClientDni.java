package uo.ri.cws.application.service.invoice.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.cws.application.util.command.Command;

public class FindWorkOrdersByClientDni implements Command<Optional<WorkOrderDto>>{

	private String dni;
	private WorkOrderRepository repo = Factory.repository.forWorkOrder();
	
	public FindWorkOrdersByClientDni(String dni) {
		this.dni = dni;
	}

	@Override
	public Optional<WorkOrderDto> execute() throws BusinessException {
		return null;
	}
	
	
}
