package com.pizzeria;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderDataManagerTest {

    private OrderDataManager dataManager;

    // Создаем моки для потоков ввода-вывода с помощью аннотации Mockito
    @Mock
    private Writer mockWriter;

    @Mock
    private Reader mockReader;

    @BeforeEach
    void setUp() {
        dataManager = new OrderDataManager();
    }

    @Test
    void testExportOrder_ShouldSortPizzasBeforeWriting() throws IOException {
        // 1. Готовим моки для наших сущностей
        Order mockOrder = mock(Order.class);
        Customer mockCustomer = mock(Customer.class);
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza("Margherita", 5));

        // 2. Обучаем моки возвращать нужные данные (Stubbing)
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockCustomer.getName()).thenReturn("Klim");
        when(mockOrder.getPizzas()).thenReturn(pizzas);

        // 3. Вызываем тестируемый метод экспорта
        dataManager.exportOrder(mockOrder, mockWriter);

        // 4. ПРОВЕРКА INTERACTION (Самая суть Mockito):
        // Убеждаемся, что метод сортировки БЫЛ вызван внутри exportOrder
        verify(mockOrder).sortPizzasByPrice();
        
        // Убеждаемся, что во writer была произведена запись данных
        verify(mockWriter, atLeastOnce()).write(any(char[].class), anyInt(), anyInt());
    }

    @Test
    void testImportOrder_ShouldThrowIOException_WhenReaderFails() throws IOException {
        // Симулируем «битый диск» или ошибку чтения:
        // Когда BufferedReader внутри попытается прочитать данные из нашего mockReader,
        // мок выбросит контролируемый IOException.
        when(mockReader.read(any(char[].class), anyInt(), anyInt()))
                .thenThrow(new IOException("Simulated hardware disk error"));

        // Проверяем покрытие исключительной ситуации (Пункт 5 лабы)
        assertThrows(IOException.class, () -> {
            dataManager.importOrder(mockReader);
        });
    }

    @Test
    void testExportOrder_ShouldThrowIllegalArgumentException_WhenOrderIsNull() {
        // Проверка валидации на null данные (невалидные входные данные)
        assertThrows(IllegalArgumentException.class, () -> {
            dataManager.exportOrder(null, mockWriter);
        });
    }
}