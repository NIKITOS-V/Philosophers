import Formating.Logger;
import Philosopher.Philosopher;
import Settings.Settings;
import Tableware.Fork;

import java.util.Collections;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<Philosopher> philosophers = new LinkedList<>();
        Logger logger = new Logger();

        // Создание списка философов

        Fork firstFork = new Fork();
        Fork leftFork = firstFork;
        Fork rightFork = new Fork();

        for (int index = 0; index < Settings.numberPhilosopher - 1; index++){
            philosophers.add(
                    new Philosopher(leftFork, rightFork, logger)
            );

            leftFork = rightFork;
            rightFork = new Fork();
        }

        philosophers.add(
                new Philosopher(leftFork, firstFork, logger)
        );

        // конец


        // перетасовка списка
        Collections.shuffle(philosophers);


        // Запуск потоков
        for (Philosopher philosopher: philosophers){
            Thread thread = new Thread(philosopher);

            thread.start();
        }
        // Конец
    }
}