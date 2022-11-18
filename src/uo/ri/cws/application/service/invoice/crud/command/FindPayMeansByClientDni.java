package uo.ri.cws.application.service.invoice.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.util.command.Command;

public class FindPayMeansByClientDni implements Command<Optional<PaymentMeanDto>>{
	private String dni;
	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();
	
	public FindPayMeansByClientDni(String dni) {
		this.dni = dni;
	}

	@Override
	public Optional<PaymentMeanDto> execute() throws BusinessException {
		return null;
	}
}
