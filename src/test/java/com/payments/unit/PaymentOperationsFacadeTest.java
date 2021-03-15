package com.payments.unit;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.infrastructure.PaymentOperationsFacade;
import com.payments.unit.mock.PaymentOperationsOutMock;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentOperationsFacadeTest {

    PaymentOperationsFacade paymentOperationsFacade;

    @BeforeEach
    void setUp() {
        paymentOperationsFacade = new PaymentOperationsFacade(new PaymentOperationsOutMock());
    }

    @Test
    void getElements() {
        List<Payment> payments = paymentOperationsFacade.getPayments();
        assertThat(payments).isNotNull();
    }

    @Test
    void savePayment() {
        PaymentDto paymentDto = PaymentDto.builder().build();

        assertThat(paymentOperationsFacade.getPayments()).size().isEqualTo(0);
        Payment payment = paymentOperationsFacade.savePayment(paymentDto);
        assertThat(paymentOperationsFacade.getPayments()).size().isEqualTo(1);
        assertThat(payment.getId()).isEqualTo(1);
    }

    @Test
    void updatePayment() {
        PaymentDto paymentDto = PaymentDto.builder()
                .name("name before")
                .build();
        assertThat(paymentOperationsFacade.getPayments()).size().isEqualTo(0);
        Payment payment = paymentOperationsFacade.savePayment(paymentDto);

        PaymentDto paymentUpdatedDto = PaymentDto.builder()
                .name("name after")
                .build();

        Payment updatedPayment = paymentOperationsFacade.updatePayment(paymentUpdatedDto, payment.getId());

        assertThat(updatedPayment).isNotNull();
        assertThat(updatedPayment.getName()).isEqualTo("name after");
        assertThat(payment.getId()).isEqualTo(1);
    }

    @Test
    void deletePayment() {
        PaymentDto paymentDto = PaymentDto.builder().build();

        Payment payment = paymentOperationsFacade.savePayment(paymentDto);

        assertThat(paymentOperationsFacade.getPayments()).size().isEqualTo(1);
        paymentOperationsFacade.deletePayment(payment.getId());

        assertThat(paymentOperationsFacade.getPayments()).size().isEqualTo(0);
    }

    @Test
    public void givenCSVFile_whenRead_thenContentsAsExpected() throws IOException {
        Map<String, String> AUTHOR_BOOK_MAP = new HashMap<>();
        AUTHOR_BOOK_MAP.put("Dan Simmons", "Hyperion");
        AUTHOR_BOOK_MAP.put("Douglas Adams", "The Hitchhiker's Guide to the Galaxy");


        String[] HEADERS = {"author", "title"};
        Reader in = new FileReader("book.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        for (CSVRecord record : records) {
            String author = record.get("author");
            String title = record.get("title");
            assertThat(AUTHOR_BOOK_MAP.get(author)).isEqualTo(title);
        }
    }
}
