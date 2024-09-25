package com.example.labirint; // Указываем, что этот класс находится в пакете com.example.labirint

import javafx.geometry.Pos; // Импортируем класс для работы с геометрией (выравнивание)
import javafx.scene.Scene; // Импортируем класс для работы с сценами в JavaFX
import javafx.scene.canvas.Canvas; // Импортируем класс для создания канваса (площадки для рисования)
import javafx.scene.canvas.GraphicsContext; // Импортируем класс для рисования на канвасе
import javafx.scene.control.Alert; // Импортируем класс для отображения информационных окон
import javafx.scene.control.Button; // Импортируем класс для создания кнопок
import javafx.scene.layout.BorderPane; // Импортируем класс для создания компоновки с границами
import javafx.scene.layout.HBox; // Импортируем класс для горизонтальной компоновки
import javafx.stage.Stage; // Импортируем класс для работы с окнами
import javafx.scene.input.MouseEvent; // Импортируем класс для обработки событий мыши
import javafx.scene.input.KeyEvent; // Импортируем класс для обработки событий клавиатуры

public class MazeEditor { // Определяем класс MazeEditor, который отвечает за редактирование лабиринта
    private static final int CELL_SIZE = 15; // Размер одной клетки лабиринта (15 пикселей)
    private int[][] maze; // Двумерный массив для хранения структуры лабиринта
    private Canvas canvas; // Объект канваса для рисования
    private Stage primaryStage; // Основное окно приложения
    private Player player; // Игрок, который перемещается по лабиринту
    private int exitX; // Координата X выхода
    private int exitY; // Координата Y выхода

    // Конструктор класса MazeEditor
    public MazeEditor(Stage primaryStage, int width, int height) {
        this.primaryStage = primaryStage; // Сохраняем ссылку на основное окно
        this.maze = new int[width][height]; // Инициализируем массив лабиринта заданных ширины и высоты
        this.canvas = new Canvas(width * CELL_SIZE, height * CELL_SIZE); // Создаём канвас с нужными размерами
        this.player = new Player(1, 1); // Инициализируем игрока в позиции (1, 1)
        this.exitX = maze.length - 2; // Устанавливаем координаты выхода (второй от нижнего края)
        this.exitY = maze[0].length - 2; // Устанавливаем координаты выхода (второй от правого края)
        initMaze(); // Инициализируем лабиринт
        setupCanvas(); // Настраиваем канвас для рисования
        setupKeyListeners(); // Настраиваем слушатели событий клавиатуры
    }

    private void initMaze() { // Метод для инициализации лабиринта
        for (int x = 0; x < maze.length; x++) { // Цикл по ширине лабиринта
            for (int y = 0; y < maze[0].length; y++) { // Цикл по высоте лабиринта
                maze[x][y] = 1; // Устанавливаем все ячейки как стены (значение 1)
            }
        }

        maze[player.getX()][player.getY()] = 2; // Устанавливаем начальную позицию игрока (значение 2)

        maze[exitX][exitY] = 0; // Устанавливаем выход как путь (значение 0), а не стену
    }

    private void setupCanvas() { // Метод для настройки канваса
        drawMaze(canvas.getGraphicsContext2D()); // Рисуем лабиринт на канвасе
        canvas.setOnMousePressed(this::handleMouseEvent); // Устанавливаем обработчик событий нажатия мыши
        canvas.setOnMouseDragged(this::handleMouseEvent); // Устанавливаем обработчик событий перетаскивания мыши
    }

    private void setupKeyListeners() { // Метод для настройки слушателей событий клавиатуры
        canvas.setFocusTraversable(true); // Устанавливаем фокус на канвасе
        canvas.setOnKeyPressed(this::handleKeyPress); // Устанавливаем обработчик событий нажатия клавиш
    }

    private void handleKeyPress(KeyEvent event) { // Метод для обработки нажатий клавиш
        int newX = player.getX(); // Получаем текущую координату X игрока
        int newY = player.getY(); // Получаем текущую координату Y игрока

        // Определяем новое положение игрока в зависимости от нажатой клавиши
        switch (event.getCode()) {
            case UP: // Если нажата клавиша "вверх"
                newY--; // Уменьшаем координату Y
                break;
            case DOWN: // Если нажата клавиша "вниз"
                newY++; // Увеличиваем координату Y
                break;
            case LEFT: // Если нажата клавиша "влево"
                newX--; // Уменьшаем координату X
                break;
            case RIGHT: // Если нажата клавиша "вправо"
                newX++; // Увеличиваем координату X
                break;
            default: // Если нажата другая клавиша
                return; // Выходим из метода
        }

        // Проверка границ и коллизий
        if (isMoveValid(newX, newY)) { // Если движение допустимо
            player.move(newX, newY, maze); // Двигаем игрока на новые координаты
            updateMazeWithPlayerPosition(); // Обновляем позицию игрока в массиве лабиринта
            drawMaze(canvas.getGraphicsContext2D()); // Перерисовываем лабиринт
            checkVictory(); // Проверка на выход после движения
        }
    }

    private boolean isMoveValid(int x, int y) { // Метод для проверки, допустимо ли движение
        // Проверяем, выходит ли движение за пределы лабиринта или сталкивается ли с стеной
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] != 1;
    }

    private void updateMazeWithPlayerPosition() { // Метод для обновления позиции игрока в массиве лабиринта
        for (int x = 0; x < maze.length; x++) { // Цикл по ширине лабиринта
            for (int y = 0; y < maze[0].length; y++) { // Цикл по высоте лабиринта
                if (maze[x][y] == 2) { // Если находим ячейку с игроком
                    maze[x][y] = 0; // Удаляем игрока из предыдущей позиции (устанавливаем в путь)
                }
            }
        }
        maze[player.getX()][player.getY()] = 2; // Устанавливаем игрока на новую позицию
    }

    private void handleMouseEvent(MouseEvent event) { // Метод для обработки событий мыши
        int x = (int) (event.getX() / CELL_SIZE); // Получаем координату X клетки, на которую кликнули
        int y = (int) (event.getY() / CELL_SIZE); // Получаем координату Y клетки, на которую кликнули

        // Проверяем, находятся ли координаты в пределах лабиринта
        if (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length) {
            if (event.isPrimaryButtonDown()) { // Если нажата левая кнопка мыши
                if (maze[x][y] != 2 && maze[x][y] != 3) { // Если клетка не занята игроком и не является стеной
                    maze[x][y] = 0; // Создаём путь (устанавливаем в 0)
                }
            } else if (event.isSecondaryButtonDown()) { // Если нажата правая кнопка мыши
                if (maze[x][y] != 2 && maze[x][y] != 3) { // Если клетка не занята игроком и не является стеной
                    maze[x][y] = 1; // Создаём стену (устанавливаем в 1)
                }
            }
            drawMaze(canvas.getGraphicsContext2D()); // Перерисовываем лабиринт
        }
    }

    private void drawMaze(GraphicsContext gc) { // Метод для отрисовки лабиринта
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Очищаем канвас перед отрисовкой

        // Цикл по всем клеткам в лабиринте
        for (int x = 0; x < maze.length; x++) { // Цикл по ширине лабиринта
            for (int y = 0; y < maze[0].length; y++) { // Цикл по высоте лабиринта
                if (maze[x][y] == 1) { // Если это стена
                    gc.setFill(javafx.scene.paint.Color.BLACK); // Устанавливаем цвет черным
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Рисуем стену
                } else if (maze[x][y] == 0) { // Если это путь
                    gc.setFill(javafx.scene.paint.Color.WHITE); // Устанавливаем цвет белым
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Рисуем путь
                } else if (maze[x][y] == 2) { // Если это игрок
                    gc.setFill(javafx.scene.paint.Color.RED); // Устанавливаем цвет красным
                    gc.fillOval(x * CELL_SIZE + 2, y * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4); // Рисуем игрока в виде овала
                }
            }
        }

        // Рисуем выход
        gc.setFill(javafx.scene.paint.Color.GREEN); // Устанавливаем цвет выхода зеленым
        gc.fillRect(exitX * CELL_SIZE, exitY * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Рисуем выход
    }

    private void checkVictory() { // Метод для проверки, достиг ли игрок выхода
        if (player.getX() == exitX && player.getY() == exitY) { // Если координаты игрока совпадают с координатами выхода
            showVictoryMessage(); // Отображаем сообщение о победе
        }
    }

    private void showVictoryMessage() { // Метод для отображения сообщения о победе
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Создаем новое информационное окно
        alert.setTitle("Победа!"); // Устанавливаем заголовок окна
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText("Поздравляем, вы прошли лабиринт!"); // Устанавливаем текст сообщения
        alert.showAndWait(); // Отображаем окно и ждем, пока его закроют
        restartEditor(); // Сбрасываем редактор после закрытия сообщения
    }

    public void display() { // Метод для отображения редактора лабиринта
        HBox header = new HBox(); // Создаём горизонтальную компоновку для заголовка
        header.setAlignment(Pos.CENTER); // Устанавливаем выравнивание по центру
        header.setSpacing(10); // Устанавливаем промежуток между элементами

        // Кнопка сброса лабиринта
        Button resetMazeButton = new Button("Сбросить лабиринт");
        resetMazeButton.setFocusTraversable(false); // Убираем возможность фокусировки на кнопке
        resetMazeButton.setOnAction(e -> restartEditor()); // Устанавливаем действие на нажатие кнопки

        // Кнопка управления
        Button controlsButton = new Button("Управление");
        controlsButton.setFocusTraversable(false);
        controlsButton.setOnAction(e -> showControls()); // Устанавливаем действие на нажатие кнопки

        // Кнопка возврата в главное меню
        Button backButton = new Button("Назад");
        backButton.setFocusTraversable(false);
        backButton.setOnAction(e -> returnToMainMenu()); // Устанавливаем действие на нажатие кнопки

        // Добавляем кнопки в заголовок
        header.getChildren().addAll(resetMazeButton, controlsButton, backButton);

        // Создаём основную компоновку
        BorderPane root = new BorderPane(); // Создаём панель с границами
        root.setTop(header); // Устанавливаем заголовок в верхнюю часть
        root.setCenter(canvas); // Устанавливаем канвас в центр

        // Создаём сцену и устанавливаем её в основное окно
        Scene scene = new Scene(root, maze.length * CELL_SIZE, maze[0].length * CELL_SIZE + 50);
        primaryStage.setScene(scene); // Устанавливаем сцену в основное окно
        primaryStage.show(); // Отображаем окно

        canvas.requestFocus(); // Запрашиваем фокус на канвас
    }

    private void restartEditor() { // Метод для сброса редактора
        // Создаем новый экземпляр MazeEditor с теми же размерами и отображаем его
        MazeEditor newEditor = new MazeEditor(primaryStage, maze.length, maze[0].length);
        newEditor.display(); // Отображаем новый редактор
    }

    private void returnToMainMenu() { // Метод для возврата в главное меню
        MazeGame mazeGame = new MazeGame(); // Создаем новый экземпляр MazeGame
        Stage stage = (Stage) canvas.getScene().getWindow(); // Получаем текущее окно
        mazeGame.start(stage); // Запускаем главное меню
    }

    private void showControls() { // Метод для отображения управления
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Создаем новое информационное окно
        alert.setTitle("Управление"); // Устанавливаем заголовок окна
        alert.setHeaderText("Как управлять лабиринтом:"); // Устанавливаем заголовок раздела
        // Устанавливаем текст управления
        alert.setContentText("Перемещение персонажа:\n" +
                "↑ - Вверх\n" +
                "↓ - Вниз\n" +
                "← - Влево\n" +
                "→ - Вправо\n\n" +
                "Создание путей и стен:\n" +
                "Левая кнопка мыши - создать путь\n" +
                "Правая кнопка мыши - создать стену");
        alert.showAndWait(); // Отображаем окно и ждем, пока его закроют
    }
}
