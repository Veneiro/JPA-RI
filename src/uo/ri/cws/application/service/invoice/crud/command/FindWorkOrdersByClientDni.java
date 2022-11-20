package uo.ri.cws.application.service.invoice.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrdersByClientDni
		implements Command<List<InvoicingWorkOrderDto>> {

	private String dni;
	private WorkOrderRepository repo = Factory.repository.forWorkOrder();

	public FindWorkOrdersByClientDni(String dni) {
		this.dni = dni;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		List<WorkOrder> res = repo.findNotInvoicedWorkOrdersByClientDni(dni);

		return DtoAssembler.toInvoicingWorkOrderDtoList(res);

	}

}
