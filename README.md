
ReadMe (English Version)
Maze Game
This project is a simple maze game implemented in Java. The game generates a random maze and allows a player to navigate through it to reach the exit. The game uses JavaFX for rendering the maze and player movements.

Features
Random Maze Generation: Every game generates a unique maze.
Player Movement: The player can move in four directions within the maze.
Exit Point: The goal is to reach the green exit point.
Collision Detection: The player cannot pass through walls.
Project Structure
Player.java: Represents the player and handles player movement.
Maze.java: Responsible for generating and storing the maze structure.
MazeRenderer.java: Handles rendering the maze and the player on the screen.
How to Run
Prerequisites:

Ensure you have Java installed on your machine.
JavaFX must be set up to render the graphics.
Running the Game:

Compile the Java files using javac.
Run the application using java.
Example commands:

sh
Копировать код
javac -cp .:path_to_javafx/lib Maze.java Player.java MazeRenderer.java
java -cp .:path_to_javafx/lib com.example.labirint.Main
Replace path_to_javafx with the actual path to your JavaFX library.

Customizing the Maze
You can customize the maze's size by modifying the parameters in the Maze class constructor. Additionally, you can modify the player's starting position by adjusting the parameters in the Player class constructor.

Future Improvements
Multiple Levels: Add different levels with increasing difficulty.
Timer: Implement a timer to track how long it takes the player to complete the maze.
Score System: Add a scoring system based on time and number of moves.
License
This project is open-source and available under the MIT License. Feel free to modify and use it as you like.

ReadMe (Русская версия)
Игра Лабиринт
Этот проект представляет собой простую игру в лабиринт, реализованную на Java. Игра генерирует случайный лабиринт и позволяет игроку перемещаться по нему, чтобы достичь выхода. Для отображения лабиринта и перемещений игрока используется JavaFX.

Особенности
Генерация случайного лабиринта: Каждый раз игра генерирует уникальный лабиринт.
Перемещение игрока: Игрок может двигаться в четырех направлениях внутри лабиринта.
Точка выхода: Цель игры — достичь зеленой точки выхода.
Обнаружение столкновений: Игрок не может проходить сквозь стены.
Структура проекта
Player.java: Представляет игрока и управляет его перемещениями.
Maze.java: Отвечает за генерацию и хранение структуры лабиринта.
MazeRenderer.java: Обрабатывает отрисовку лабиринта и игрока на экране.
Как запустить
Предварительные требования:

Убедитесь, что у вас установлена Java.
JavaFX должен быть настроен для отображения графики.
Запуск игры:

Скомпилируйте файлы Java с помощью javac.
Запустите приложение с помощью java.
Пример команд:

sh
Копировать код
javac -cp .:path_to_javafx/lib Maze.java Player.java MazeRenderer.java
java -cp .:path_to_javafx/lib com.example.labirint.Main
Замените path_to_javafx на фактический путь к вашей библиотеке JavaFX.

Настройка лабиринта
Вы можете настроить размер лабиринта, изменив параметры в конструкторе класса Maze. Также можно изменить начальную позицию игрока, отредактировав параметры в конструкторе класса Player.

Возможные улучшения
Многоуровневая игра: Добавьте разные уровни с увеличивающейся сложностью.
Таймер: Реализуйте таймер для отслеживания времени прохождения лабиринта.
Система очков: Добавьте систему очков, основанную на времени и количестве перемещений.
Лицензия
Этот проект является open-source и доступен по лицензии MIT. Вы можете свободно изменять и использовать его по своему усмотрению.
