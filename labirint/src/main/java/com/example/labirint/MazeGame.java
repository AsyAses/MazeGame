package com.example.labirint; // Указываем, что класс находится в пакете com.example.labirint

import javafx.application.Application; // Импортируем основной класс приложения JavaFX
import javafx.geometry.Pos; // Импортируем класс для выравнивания компонентов
import javafx.scene.Scene; // Импортируем класс для создания сцен
import javafx.scene.canvas.Canvas; // Импортируем класс для рисования на холсте
import javafx.scene.canvas.GraphicsContext; // Импортируем класс для графического контекста
import javafx.scene.control.Alert; // Импортируем класс для создания всплывающих окон
import javafx.scene.control.Button; // Импортируем класс для создания кнопок
import javafx.scene.input.KeyCode; // Импортируем класс для обработки нажатий клавиш
import javafx.scene.layout.BorderPane; // Импортируем класс для управления расположением элементов
import javafx.scene.layout.HBox; // Импортируем класс для горизонтального расположения элементов
import javafx.stage.Stage; // Импортируем класс для создания окон приложения

public class MazeGame extends Application {
    private static final int CELL_SIZE = 15; // Размер каждой клетки лабиринта
    private static final int WIDTH = 40; // Ширина лабиринта в клетках
    private static final int HEIGHT = 40; // Высота лабиринта в клетках

    private Maze maze; // Объект лабиринта
    private Player player; // Объект игрока
    private int exitX = WIDTH - 1; // Координата X выхода
    private int exitY = HEIGHT - 1; // Координата Y выхода
    private Canvas canvas; // Холст для рисования лабиринта

    public static void main(String[] args) {
        launch(args); // Запускаем JavaFX приложение
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Maze Game"); // Устанавливаем заголовок окна
        startNewGame(primaryStage); // Запускаем новую игру
    }

    private void startNewGame(Stage primaryStage) {
        maze = new Maze(WIDTH, HEIGHT); // Генерируем новый лабиринт
        player = new Player(0, 0); // Перемещаем игрока в начальную позицию

        canvas = new Canvas(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE); // Создаём холст
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Получаем контекст для рисования
        drawMaze(gc); // Рисуем лабиринт

        HBox header = new HBox(); // Создаем верхнюю панель
        header.setAlignment(Pos.CENTER); // Выравниваем панель по центру
        header.setSpacing(10); // Устанавливаем отступы между кнопками

        // Создаем кнопку для генерации нового лабиринта
        Button newGameButton = new Button("Сгенерировать лабиринт");
        newGameButton.setFocusTraversable(false); // Убираем фокус с кнопки
        newGameButton.setOnAction(e -> startNewGame(primaryStage)); // Генерация нового лабиринта

        // Создаем кнопку для создания собственного лабиринта
        Button editMazeButton = new Button("Создать собственный лабиринт");
        editMazeButton.setFocusTraversable(false); // Убираем фокус с кнопки
        editMazeButton.setOnAction(e -> startMazeEditor(primaryStage)); // Переход к редактору лабиринта

        // Создаем кнопку для отображения управления
        Button controlsButton = new Button("Управление");
        controlsButton.setFocusTraversable(false); // Убираем фокус с кнопки
        controlsButton.setOnAction(e -> showControls()); // Показать управление

        // Создаем кнопку "О программе"
        Button aboutButton = new Button("О программе");
        aboutButton.setFocusTraversable(false); // Убираем фокус с кнопки
        aboutButton.setOnAction(e -> showAbout()); // Показать информацию о программе

        // Добавляем кнопки на панель
        header.getChildren().addAll(newGameButton, editMazeButton, controlsButton, aboutButton);

        // Создаем корневой элемент с верхней панелью и холстом
        BorderPane root = new BorderPane();
        root.setTop(header); // Устанавливаем панель сверху
        root.setCenter(canvas); // Устанавливаем холст в центре

        // Создаем сцену и отображаем её
        Scene scene = new Scene(root, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE + 50);
        primaryStage.setScene(scene); // Устанавливаем сцену в окно
        primaryStage.show(); // Показываем окно

        // Обрабатываем нажатия клавиш для перемещения игрока
        canvas.setOnKeyPressed(event -> handleKeyPress(event.getCode(), gc));
        canvas.requestFocus(); // Запрашиваем фокус на холст
    }

    private void handleKeyPress(KeyCode keyCode, GraphicsContext gc) {
        // Обрабатываем нажатия клавиш для перемещения игрока
        switch (keyCode) {
            case UP:
                player.move(player.getX(), player.getY() - 1, maze.getMaze()); // Движение вверх
                break;
            case DOWN:
                player.move(player.getX(), player.getY() + 1, maze.getMaze()); // Движение вниз
                break;
            case LEFT:
                player.move(player.getX() - 1, player.getY(), maze.getMaze()); // Движение влево
                break;
            case RIGHT:
                player.move(player.getX() + 1, player.getY(), maze.getMaze()); // Движение вправо
                break;
        }
        drawMaze(gc); // Перерисовываем лабиринт
        checkVictory(); // Проверка на выход из лабиринта
    }

    private void drawMaze(GraphicsContext gc) {
        int[][] mazeArray = maze.getMaze(); // Получаем массив лабиринта
        // Рисуем лабиринт
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                // Заполняем клетку черным (стена) или белым (путь)
                gc.setFill(mazeArray[x][y] == 1 ? javafx.scene.paint.Color.BLACK : javafx.scene.paint.Color.WHITE);
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Рисуем клетку
            }
        }

        // Отрисовка выхода
        gc.setFill(javafx.scene.paint.Color.GREEN); // Цвет выхода
        gc.fillRect(exitX * CELL_SIZE, exitY * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Рисуем выход

        // Отрисовка игрока
        gc.setFill(javafx.scene.paint.Color.RED); // Цвет игрока
        gc.fillRect(player.getX() * CELL_SIZE, player.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Рисуем игрока
    }

    private void checkVictory() {
        // Проверяем, достиг ли игрок выхода
        if (player.getX() == exitX && player.getY() == exitY) {
            showVictoryMessage(); // Показать сообщение о победе
        }
    }

    private void showVictoryMessage() {
        // Создаем всплывающее окно с сообщением о победе
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Победа!");
        alert.setHeaderText(null);
        alert.setContentText("Поздравляем, вы прошли лабиринт!");
        alert.showAndWait(); // Показываем сообщение
        startNewGame((Stage) canvas.getScene().getWindow()); // Перезапуск игры после закрытия сообщения
    }

    private void showControls() {
        // Создаем всплывающее окно с управлением
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Управление");
        alert.setHeaderText("Управление игроком:");
        alert.setContentText("Используйте стрелки на клавиатуре для перемещения:\n" +
                "↑ - вверх\n" +
                "↓ - вниз\n" +
                "← - влево\n" +
                "→ - вправо");
        alert.showAndWait(); // Показываем сообщение
    }

    private void showAbout() {
        // Создаем всплывающее окно с информацией о программе
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Maze Game");
        alert.setContentText("Это простая игра-лабиринт, разработанная с использованием JavaFX.\n" +
                "Версия: 1.0\n" +
                "Автор: Retro_Vader\n\n");
        alert.showAndWait(); // Показываем сообщение
    }

    private void startMazeEditor(Stage primaryStage) {
        // Создаем экземпляр MazeEditor и передаем текущее состояние игры
        MazeEditor mazeEditor = new MazeEditor(primaryStage, WIDTH, HEIGHT);
        mazeEditor.display(); // Отображаем редактор лабиринта
    }

    // Метод для обновления лабиринта из редактора
    public void setCustomMaze(int[][] customMaze) {
        this.maze = new Maze(customMaze); // Обновляем лабиринт
        this.player = new Player(0, 0); // Перемещение игрока в начальную позицию

        // Обновляем Canvas и GraphicsContext
        if (canvas == null) {
            canvas = new Canvas(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE); // Создаём новый холст
        }
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Получаем контекст для рисования
        drawMaze(gc); // Отображаем новый лабиринт

        // Обновляем сцену с новым canvas
        BorderPane root = (BorderPane) canvas.getScene().getRoot(); // Получаем корневой элемент
        root.setCenter(canvas); // Устанавливаем новый холст
        canvas.requestFocus(); // Запрашиваем фокус на canvas
    }
}
