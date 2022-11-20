package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;

public class GetAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

	private PayrollRepository repo = Factory.repository.forPayroll();

	@Override
	public List<PayrollSummaryBLDto> execute() {
		List<Payroll> res = repo.findAll();

		return DtoAssembler.toPayrollDtoList(res);
	}
}
