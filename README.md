# Splat-system
Internship

Задача

Построение архитектуры и реализация системы обработки данных. 

Компоненты

	Провайдер данных;
	Сервис взаимодействия  с провайдером и клиентами-потребителями данных;
	Набор клиентов-потребителей.


Требования, предъявляемые к компонентам:

Провайдер данных
  
    Различный формат передаваемых данных (JSON/XML) – настраивается через конфигурационный файл;
    Содержание передаваемых данных состоит из 2- атрибутов:
      Идентификатор объекта – целочисленное значение;
      Значение параметра объекта  – целочисленное значение;
      
    Диапазоны идентификаторов объектов и значения параметров объекта настраивается через конфигурационный файл;
    Передача данных осуществляется через сокетное соединение, возможно одно соединение с сокетом;
    Передача данных осуществляется с временными интервалами разной длинны – настраивается через конфигурационный файл;
  Сервис
  
    Может работать с несколькими провайдерами данных;
    Основная функция – ретранслятор данных для клиентов потребителей;
  Клиенты потребители данных
  
    Desktop приложение на SWT/RCP, различное представление полученных данных (график, таблица и т.п.) ;
    Web приложение – опционально.
