# Обновление статуса кредитной заявки с отправкой уведомлений

## Проблема

В процессе обработки кредитной заявки, необходимо обновлять её статус. Если статус заявки изменяется на — например, «Одобрено» («Approved»), нужно гарантированно отправить сообщение в сервис уведомлений («Notification Service»).

**Основная проблема:**

- Сообщение должно быть отправлено **гарантированно**, **только после успешного завершения транзакции** обновления статуса в базе данных.
- Если транзакция по обновлению статуса откатится, то отправка сообщения не должна произойти.

Эта проблема требует корректной реализации механизма синхронизации между обновлением данных в базе данных и отправкой событий в Kafka (или другой мессенджер).

## Решение

Реализованно три возможных решения этой проблемы в отдельных ветках:

1. **Решение через Event Publisher:**
    - Использование доменных событий и механизма публикации событий внутри приложения (например, Spring ApplicationEventPublisher).
      [Реализация через ApplicationEventPublisher](https://github.com/DmBalaev/cdc-outbox/tree/spring-boot-event-publisher)
2. **Решение через Outbox Pattern:**
    - Использование таблицы Outbox для записи событий в базу данных в рамках той же транзакции, что и обновление статуса. Позже события из Outbox передаются в Kafka.
      [Реализация через outbox паттерн](https://github.com/DmBalaev/outbox-cdc/tree/outbox)

3. **Решение через Change Data Capture (CDC):**
    - Использование CDC-инструментов (например, Debezium) для мониторинга изменений в таблице статусов в базе данных и последующей публикации событий в Kafka.
      [Реализация через CDC паттерн (Debezium)](https://github.com/DmBalaev/outbox-cdc/tree/cdc-debezium-embedded)

----
### Заключение
* `Event Publisher`: Подходит для небольших приложений, где критичность доставки сообщений низкая.
* `Outbox Pattern`: Оптимальное решение для большинства приложений, где важна надежность и транзакционность.
* `CDC`: Идеально для сложных высоконагруженных систем, где требуется масштабируемость и полная изоляция логики событий.
