package com.example.labirint; // Указываем, что этот класс находится в пакете com.example.labirint

public class Player { // Определяем класс Player, который будет представлять игрока в игре
    private int x; // Координата игрока по оси X
    private int y; // Координата игрока по оси Y

    // Конструктор класса Player, который принимает начальные координаты игрока
    public Player(int startX, int startY) {
        this.x = startX; // Инициализируем координату X игрока
        this.y = startY; // Инициализируем координату Y игрока
    }

    // Метод для получения координаты X игрока
    public int getX() {
        return x; // Возвращаем значение координаты X
    }

    // Метод для получения координаты Y игрока
    public int getY() {
        return y; // Возвращаем значение координаты Y
    }

    // Метод для перемещения игрока
    public void move(int newX, int newY, int[][] maze) {
        // Проверяем, находятся ли новые координаты в пределах лабиринта и является ли клетка свободной (значение 0)
        if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length && maze[newX][newY] == 0) {
            x = newX; // Обновляем координату X игрока на новые значения
            y = newY; // Обновляем координату Y игрока на новые значения
        }
    }
}
