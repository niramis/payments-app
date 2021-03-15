package com.payments.infrastructure;

import com.payments.application.exception.NotFoundPaymentException;
import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.core.ports.out.PaymentOperationsOut;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("production")
public class PaymentOperationsCsvAdapter implements PaymentOperationsOut {

    private static final String FILE_NAME = "payment.csv";
    private static long PAYMENT_COUNTER;

    PaymentOperationsCsvAdapter() {
        PAYMENT_COUNTER = readPayments().size();
    }

    @Override
    public List<Payment> getPayments() {
        List<Payment> payments = new ArrayList<>(readPayments().values());
        return payments;
    }

    @Override
    public Payment savePayment(PaymentDto paymentDto) {

        Map<Long, Payment> payments = readPayments();

        Payment payment = PaymentMapper.mapToPayment(paymentDto);

        payment.setId(Long.valueOf(++PAYMENT_COUNTER));
        payments.put(PAYMENT_COUNTER, payment);

        savePayments(payments);

        return payment;
    }

    @Override
    public Payment updatePayment(PaymentDto paymentDto, Long id) {

        Map<Long, Payment> payments = readPayments();

        Payment payment = payments.get(id);

        if (payment != null) {
            payment = PaymentMapper.mapToPayment(paymentDto);
            payment.setId(id);
            payments.put(id, payment);
            savePayments(payments);
        } else {
            throw new NotFoundPaymentException();
        }

        return payment;
    }

    @Override
    public void deletePayment(Long id) {
        Map<Long, Payment> payments = readPayments();

        Payment payment = payments.get(id);

        if (payment != null) {
            payments.remove(id);
            savePayments(payments);
        } else {
            throw new NotFoundPaymentException();
        }
    }

    private Map<Long, Payment> readPayments() {
        Map<Long, Payment> payments = new HashMap<>();
        String[] HEADERS = {"id", "name", "amount", "userId", "currency", "targetBankAccountNumber"};

        try (Reader in = new FileReader(FILE_NAME)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .parse(in);
            for (CSVRecord record : records) {
                Long id = Long.valueOf(record.get(0));
                String name = record.get(1);
                BigDecimal amount = BigDecimal.valueOf(Double.valueOf(record.get(2)));
                Long userId = Long.valueOf(record.get(3));
                String currency = record.get(4);
                Long targetBankAccountNumber = Long.valueOf(record.get(5));

                Payment payment = Payment.builder()
                        .id(id)
                        .name(name)
                        .amount(amount)
                        .userId(userId)
                        .currency(currency)
                        .targetBankAccountNumber(targetBankAccountNumber)
                        .build();

                payments.put(id, payment);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payments;
    }

    private void savePayments(Map<Long, Payment> payments) {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            for (Map.Entry<Long, Payment> entry : payments.entrySet()) {
                Payment payment = entry.getValue();
                csvPrinter.printRecord(payment.getId(), payment.getName(), payment.getAmount(), payment.getUserId(), payment.getCurrency(), payment.getTargetBankAccountNumber());
            }

            csvPrinter.flush();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
