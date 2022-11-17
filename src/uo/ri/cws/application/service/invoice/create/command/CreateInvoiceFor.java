package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds);
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Long number = invsRepo.getNextInvoiceNumber();
		List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);
		checkConditions(workOrders);
		Invoice i = new Invoice(number, workOrders);
		return DtoAssembler.toDto(i);
	}

	private void checkConditions(List<WorkOrder> workOrders)
			throws BusinessException {
		BusinessChecks.isTrue(workOrders.size() == workOrderIds.size());
		for (WorkOrder wo : workOrders) {
			BusinessChecks.isTrue(wo.isFinished());
		}
	}

}
