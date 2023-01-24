package me.afatcookie.customitemevents.customitemevents.abstractevents;

public interface CustomItemEvent<T> {

    void execute(T e);
    String getID();


}
