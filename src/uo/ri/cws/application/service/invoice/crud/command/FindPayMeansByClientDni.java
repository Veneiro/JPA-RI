package uo.ri.cws.application.service.invoice.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.PaymentMean;

public class FindPayMeansByClientDni implements Command<List<PaymentMeanDto>> {
	private String dni;
	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();

	public FindPayMeansByClientDni(String dni) {
		this.dni = dni;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		List<PaymentMean> res = repo.findPaymentMeansByClientId(dni);

		return DtoAssembler.toPaymentMeanDtoList(res);
	}
}
