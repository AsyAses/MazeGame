package com.example.labirint; // Указываем, что этот класс находится в пакете com.example.labirint

import java.util.ArrayList; // Импортируем класс для работы с динамическими массивами
import java.util.Collections; // Импортируем класс для работы с коллекциями
import java.util.List; // Импортируем интерфейс для списков
import java.util.Random; // Импортируем класс для генерации случайных чисел

public class Maze { // Определяем класс Maze, который отвечает за генерацию и хранение лабиринта

    private final int width; // Ширина лабиринта
    private final int height; // Высота лабиринта
    private final int[][] maze; // Двумерный массив для хранения структуры лабиринта

    // Конструктор для генерации нового лабиринта с заданными шириной и высотой
    public Maze(int width, int height) {
        this.width = width; // Сохраняем ширину
        this.height = height; // Сохраняем высоту
        this.maze = new int[width][height]; // Инициализируем массив лабиринта
        generateMaze(); // Генерируем лабиринт
    }

    // Конструктор для создания лабиринта из существующего массива
    public Maze(int[][] customMaze) {
        this.width = customMaze.length; // Устанавливаем ширину из массива
        this.height = customMaze[0].length; // Устанавливаем высоту из массива
        this.maze = customMaze; // Сохраняем массив лабиринта
    }

    // Метод для получения ширины лабиринта
    public int getWidth() {
        return width; // Возвращаем ширину
    }

    // Метод для получения высоты лабиринта
    public int getHeight() {
        return height; // Возвращаем высоту
    }

    // Метод для получения двумерного массива лабиринта
    public int[][] getMaze() {
        return maze; // Возвращаем массив лабиринта
    }

    // Метод для получения значения клетки по координатам
    public int getCell(int x, int y) {
        return maze[x][y]; // Возвращаем значение клетки
    }

    // Метод для установки значения клетки по координатам
    public void setCell(int x, int y, int value) {
        maze[x][y] = value; // Устанавливаем значение клетки
    }

    // Метод для генерации лабиринта
    private void generateMaze() {
        // Заполняем все клетки стенами (значение 1)
        for (int x = 0; x < width; x++) { // Цикл по ширине
            for (int y = 0; y < height; y++) { // Цикл по высоте
                maze[x][y] = 1;  // Все ячейки — стены
            }
        }

        generatePath(0, 0); // Генерируем путь, начиная с координат (0, 0)
        ensureExitPath();    // Обеспечиваем наличие выхода из лабиринта
        createRandomPassages(); // Добавляем случайные проходы в лабиринт
    }

    // Метод для генерации пути с использованием рекурсии
    private void generatePath(int x, int y) {
        maze[x][y] = 0; // Помечаем текущую ячейку как путь (значение 0)

        // Список направлений, в которых можно двигаться
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{1, 0}); // Вниз
        directions.add(new int[]{-1, 0}); // Вверх
        directions.add(new int[]{0, 1}); // Вправо
        directions.add(new int[]{0, -1}); // Влево
        Collections.shuffle(directions); // Перемешиваем направления, чтобы получить случайный порядок

        // Перебираем все возможные направления
        for (int[] direction : directions) {
            // Вычисляем новые координаты для следующего шага
            int newX = x + direction[0] * 2; // Шаг в X
            int newY = y + direction[1] * 2; // Шаг в Y

            // Проверяем, не выходит ли новый путь за пределы лабиринта и является ли он стеной
            if (newX >= 0 && newX < width && newY >= 0 && newY < height && maze[newX][newY] == 1) {
                maze[x + direction[0]][y + direction[1]] = 0; // Создаем проход между текущей и новой клеткой
                generatePath(newX, newY); // Рекурсивный вызов для генерации пути из новой позиции
            }
        }
    }

    // Метод для обеспечения наличия выхода из лабиринта
    private void ensureExitPath() {
        int exitX = width - 1; // Устанавливаем координату X выхода
        int exitY = height - 1; // Устанавливаем координату Y выхода
        maze[exitX][exitY] = 0; // Устанавливаем выход (значение 0)

        // Создаем дополнительные проходы вокруг выхода, чтобы игрок мог выйти
        if (exitX > 0) { // Если выход не на верхней границе
            maze[exitX - 1][exitY] = 0; // Создаем проход вверх
        }
        if (exitY > 0) { // Если выход не на левой границе
            maze[exitX][exitY - 1] = 0; // Создаем проход влево
        }
        if (exitX < width - 1) { // Если выход не на нижней границе
            maze[exitX + 1][exitY] = 0; // Создаем проход вниз
        }
        if (exitY < height - 1) { // Если выход не на правой границе
            maze[exitX][exitY + 1] = 0; // Создаем проход вправо
        }
    }

    // Метод для создания случайных проходов в лабиринте
    private void createRandomPassages() {
        Random rand = new Random(); // Создаём генератор случайных чисел
        int numberOfPassages = (width * height) / 20; // Количество случайных проходов (1/20 от всех клеток)

        // Генерация случайных проходов
        for (int i = 0; i < numberOfPassages; i++) {
            int x = rand.nextInt(width); // Случайная координата X
            int y = rand.nextInt(height); // Случайная координата Y

            if (maze[x][y] == 1) { // Если текущая клетка — стена
                maze[x][y] = 0; // Создаем проход (устанавливаем в 0)
            }
        }
    }
}
