### Решение через Event Publisher
   Описание: 
   Этот подход использует механизм доменных событий с помощью Spring `ApplicationEventPublisher`. После успешного обновления статуса заявки в базе данных генерируется событие, которое публикуется и отправляется в Kafka через слушатель событий.

Основные шаги:

1. Обновление статуса заявки выполняется в рамках транзакции.
2. После успешного завершения транзакции событие публикуется через `ApplicationEventPublisher`.
3. Слушатель событий обрабатывает событие и отправляет его в Kafka.

Схема:

Плюсы:

* Простота реализации: стандартные инструменты Spring Boot.
* Быстрая обработка событий в рамках приложения.
* Не требует дополнительных инфраструктурных компонентов.

Минусы:

* Нельзя гарантировать, что событие будет отправлено только после успешного завершения транзакции (например, если транзакция откатывается, но событие уже опубликовано).
* События теряются при падении сервиса.
* Невозможность масштабирования: все события обрабатываются внутри одного экземпляра приложения.