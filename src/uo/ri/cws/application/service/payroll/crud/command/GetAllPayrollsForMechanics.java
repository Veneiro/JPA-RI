package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class GetAllPayrollsForMechanics
		implements Command<List<PayrollSummaryBLDto>> {

	private String id;
	private PayrollRepository repo = Factory.repository.forPayroll();

	public GetAllPayrollsForMechanics(String id) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotBlank(id);
		this.id = id;
	}

	@Override
	public List<PayrollSummaryBLDto> execute() {
		return null;
		// repo.findByMechanicId(id).map(p -> DtoAssembler.toFullDto(p));
	}
}
