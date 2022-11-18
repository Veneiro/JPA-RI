package uo.ri.cws.application.service.invoice.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindInvoice implements Command<Optional<InvoiceDto>>{

	private Long number;
	private InvoiceRepository repo = Factory.repository.forInvoice();
	
	public FindInvoice(Long number) {
		this.number = number;
	}
	
	@Override
	public Optional<InvoiceDto> execute() throws BusinessException {
		return repo.findByNumber(number).map(i -> DtoAssembler.toDto(i));
	}

}
