package uo.ri.cws.application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CardDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CashDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.InvoicingService.VoucherDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.vehicleType.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class DtoAssembler {

	public static ClientDto toDto(Client c) {
		ClientDto dto = new ClientDto();

		dto.id = c.getId();
		dto.version = c.getVersion();

		dto.dni = c.getDni();
		dto.name = c.getName();
		dto.surname = c.getSurname();

		return dto;
	}

	public static List<ClientDto> toClientDtoList(List<Client> clientes) {
		List<ClientDto> res = new ArrayList<>();
		for (Client c : clientes) {
			res.add(DtoAssembler.toDto(c));
		}
		return res;
	}

	public static MechanicDto toDto(Mechanic m) {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getId();
		dto.version = m.getVersion();

		dto.dni = m.getDni();
		dto.name = m.getName();
		dto.surname = m.getSurname();
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(List<Mechanic> list) {
		List<MechanicDto> res = new ArrayList<>();
		for (Mechanic m : list) {
			res.add(toDto(m));
		}
		return res;
	}

	public static List<VoucherDto> toVoucherDtoList(List<Voucher> list) {
		List<VoucherDto> res = new ArrayList<>();
		for (Voucher b : list) {
			res.add(toDto(b));
		}
		return res;
	}

	public static List<ProfessionalGroupBLDto> toProfessionalGroupDtoList(
			List<ProfessionalGroup> list) {
		List<ProfessionalGroupBLDto> res = new ArrayList<>();
		for (ProfessionalGroup pg : list) {
			res.add(toDto(pg));
		}
		return res;
	}

	public static VoucherDto toDto(Voucher v) {
		VoucherDto dto = new VoucherDto();
		dto.id = v.getId();
		dto.version = v.getVersion();

		dto.clientId = v.getClient().getId();
		dto.accumulated = v.getAccumulated();
		dto.code = v.getCode();
		dto.description = v.getDescription();
		dto.available = v.getAvailable();
		return dto;
	}

	public static CardDto toDto(CreditCard cc) {
		CardDto dto = new CardDto();
		dto.id = cc.getId();
		dto.version = cc.getVersion();

		dto.clientId = cc.getClient().getId();
		dto.accumulated = cc.getAccumulated();
		dto.cardNumber = cc.getNumber();
		dto.cardExpiration = cc.getValidThru();
		dto.cardType = cc.getType();
		return dto;
	}

	public static CashDto toDto(Cash m) {
		CashDto dto = new CashDto();
		dto.id = m.getId();
		dto.version = m.getVersion();

		dto.clientId = m.getClient().getId();
		dto.accumulated = m.getAccumulated();
		return dto;
	}

	public static InvoiceDto toDto(Invoice invoice) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = invoice.getId();
		dto.version = invoice.getVersion();

		dto.number = invoice.getNumber();
		dto.date = invoice.getDate();
		dto.total = invoice.getAmount();
		dto.vat = invoice.getVat();
		dto.state = invoice.getState().toString();
		return dto;
	}

	public static List<PaymentMeanDto> toPaymentMeanDtoList(
			List<PaymentMean> list) {
		return list.stream().map(mp -> toDto(mp)).collect(Collectors.toList());
	}

	private static PaymentMeanDto toDto(PaymentMean mp) {
		if (mp instanceof Voucher) {
			return toDto((Voucher) mp);
		} else if (mp instanceof CreditCard) {
			return toDto((CreditCard) mp);
		} else if (mp instanceof Cash) {
			return toDto((Cash) mp);
		} else {
			throw new RuntimeException("Unexpected type of payment mean");
		}
	}

	public static WorkOrderDto toDto(WorkOrder a) {
		WorkOrderDto dto = new WorkOrderDto();
		dto.id = a.getId();
		dto.version = a.getVersion();

		dto.vehicleId = a.getVehicle().getId();
		dto.description = a.getDescription();
		dto.date = a.getDate();
		dto.total = a.getAmount();
		dto.state = a.getState().toString();

		dto.invoiceId = a.getInvoice() == null ? null : a.getInvoice().getId();

		return dto;
	}

	public static InvoicingWorkOrderDto toInvoicingDto(WorkOrder a) {
		InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
		dto.id = a.getId();

		dto.description = a.getDescription();
		dto.date = a.getDate().toLocalDate();
		dto.total = a.getAmount();
		dto.state = a.getState().toString();

		return dto;
	}

	public static VehicleDto toDto(Vehicle v) {
		VehicleDto dto = new VehicleDto();
		dto.id = v.getId();
		dto.version = v.getVersion();

		dto.plate = v.getPlateNumber();
		dto.clientId = v.getClient().getId();
		dto.make = v.getMake();
		dto.vehicleTypeId = v.getVehicleType().getId();
		dto.model = v.getModel();

		return dto;
	}

	public static List<WorkOrderDto> toWorkOrderDtoList(List<WorkOrder> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static List<InvoicingWorkOrderDto> toInvoicingWorkOrderDtoList(
			List<WorkOrder> list) {
		List<InvoicingWorkOrderDto> result = new ArrayList<InvoicingWorkOrderDto>();
		for (WorkOrder w : list)
			result.add(toInvoicingDto(w));
		return result;
	}

	public static VehicleTypeDto toDto(VehicleType vt) {
		VehicleTypeDto dto = new VehicleTypeDto();

		dto.id = vt.getId();
		dto.version = vt.getVersion();

		dto.name = vt.getName();
		dto.pricePerHour = vt.getPricePerHour();

		return dto;
	}

	public static List<VehicleTypeDto> toVehicleTypeDtoList(
			List<VehicleType> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static ProfessionalGroupBLDto toDto(ProfessionalGroup a) {
		ProfessionalGroupBLDto dto = new ProfessionalGroupBLDto();
		dto.id = a.getId();
		dto.version = a.getVersion();

		dto.name = a.getName();
		dto.trieniumSalary = a.getTrieniumSalary();
		dto.productivityRate = a.getProductivityBonusPercentage();

		return dto;
	}

	public static List<PayrollSummaryBLDto> toPayrollDtoList(
			List<Payroll> res) {
		return res.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static PayrollSummaryBLDto toDto(Payroll a) {
		PayrollSummaryBLDto dto = new PayrollSummaryBLDto();
		dto.id = a.getId();
		dto.version = a.getVersion();

		dto.date = a.getDate();
		return dto;
	}

	public static PayrollBLDto toFullDto(Payroll a) {
		PayrollBLDto dto = new PayrollBLDto();
		dto.id = a.getId();
		dto.version = a.getVersion();

		dto.date = a.getDate();
		dto.bonus = a.getBonus();
		dto.contractId = a.getContractId();
		dto.incomeTax = a.getIncomeTax();
		dto.monthlyWage = a.getMonthlyWage();
		dto.nic = a.getNIC();
		dto.productivityBonus = a.getProductivityBonus();
		dto.trienniumPayment = a.getTrienniumPayment();
		return dto;
	}

	public static List<PayrollBLDto> toPayrollSummaryDtoList(
			List<Payroll> res) {
		return res.stream().map(a -> toDtoSummary(a))
				.collect(Collectors.toList());
	}

	private static PayrollBLDto toDtoSummary(Payroll a) {
		PayrollBLDto dto = new PayrollBLDto();
		dto.id = a.getId();
		dto.version = a.getVersion();

		dto.bonus = a.getBonus();
		dto.contractId = a.getContractId();
		dto.date = a.getDate();
		dto.incomeTax = a.getIncomeTax();
		dto.monthlyWage = a.getMonthlyWage();
		dto.nic = a.getNIC();
		dto.productivityBonus = a.getProductivityBonus();
		dto.trienniumPayment = a.getTrienniumPayment();
		return dto;
	}

}
