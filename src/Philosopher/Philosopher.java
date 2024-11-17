package Philosopher;

import Formating.Logger;
import Tableware.Fork;
import Settings.Settings;


public class Philosopher implements Runnable{
    private static long lastID = 0;

    private final Logger logger;

    private final Fork rightFork;
    private final Fork leftFork;;

    private int currentNumberMeal;
    private final long ID;
    private Status status;
    private boolean thought;

    public Philosopher(Fork leftFork, Fork rightFork, Logger logger){
        this.logger = logger;

        this.leftFork = leftFork;
        this.rightFork = rightFork;

        this.currentNumberMeal = 0;
        this.ID = lastID++;

        this.thought = true;
    }

    private void eat(){
         if (this.currentNumberMeal + Settings.maxNumberMealsAtTime < Settings.maxNumberMeals){

             this.currentNumberMeal += Settings.maxNumberMealsAtTime;

        } else {
            this.currentNumberMeal = Settings.maxNumberMeals;
        }
    }

    private void think(){
        changeStatus(Status.thinks);
        this.thought = true;
    }


    private void changeStatus(Status status){
        this.status = status;
        this.logger.writeLog(this.toString());
    }

    private void borrowForks(){
         this.leftFork.setBusy(true, this.ID);
         this.rightFork.setBusy(true, this.ID);
    }

    private void releaseForks(){
         this.leftFork.setBusy(false, null);
         this.rightFork.setBusy(false, null);
    }

    @Override
    public void run() {
        while (this.currentNumberMeal < Settings.maxNumberMeals){
            synchronized (this.rightFork){
                synchronized (this.leftFork){
                    if (!this.rightFork.isBusy() && !this.leftFork.isBusy() && thought){

                        changeStatus(Status.borrow_forks);

                        borrowForks();

                        changeStatus(Status.starts_eating);

                        eat();

                        changeStatus(Status.finished_eating);

                        releaseForks();

                        changeStatus(Status.release_forks);

                        this.thought = false;
                    }
                }
            }
            think();
        }

        changeStatus(Status.full);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d, status: %s, left fork: { %s }, right fork: { %s }, current number meals: %d",
                this.ID,
                this.status,
                this.leftFork,
                this.rightFork,
                this.currentNumberMeal
        );
    }
}
