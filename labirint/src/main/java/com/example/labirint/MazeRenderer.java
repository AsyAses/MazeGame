package com.example.labirint; // Указываем, что этот класс находится в пакете com.example.labirint

import javafx.scene.canvas.GraphicsContext; // Импортируем класс GraphicsContext для рисования на канвасе
import javafx.scene.paint.Color; // Импортируем класс Color для работы с цветами

public class MazeRenderer { // Определяем класс MazeRenderer, который отвечает за отрисовку лабиринта

    private static final int CELL_SIZE = 15; // Размер одной клетки лабиринта (15 пикселей)

    // Статический метод для отрисовки лабиринта
    public static void drawMaze(GraphicsContext gc, Maze maze, Player player, int exitX, int exitY) {
        // Цикл по всем клеткам в ширину лабиринта
        for (int x = 0; x < maze.getWidth(); x++) {
            // Цикл по всем клеткам в высоту лабиринта
            for (int y = 0; y < maze.getHeight(); y++) {
                // Проверяем, является ли текущая клетка стеной (1) или пустым пространством (0)
                if (maze.getCell(x, y) == 1) {
                    gc.setFill(Color.BLACK); // Если это стена, устанавливаем цвет на чёрный
                } else {
                    gc.setFill(Color.WHITE); // Если это пустое пространство, устанавливаем цвет на белый
                }
                // Рисуем клетку по текущим координатам x и y, размером CELL_SIZE на CELL_SIZE
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Отображаем выход как путь, чтобы игрок мог на него наступить
        gc.setFill(Color.GREEN); // Устанавливаем цвет для выхода на зелёный
        // Рисуем выход в координатах exitX и exitY
        gc.fillRect(exitX * CELL_SIZE, exitY * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Отображаем игрока
        gc.setFill(Color.RED); // Устанавливаем цвет для игрока на красный
        // Рисуем игрока как красный круг в позиции игрока с учётом смещения
        gc.fillOval(player.getX() * CELL_SIZE + 2, player.getY() * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4);
        // Уменьшаем размеры круга на 4 пикселя для создания эффекта отступа от края клетки
    }
}
