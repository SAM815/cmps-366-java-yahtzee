package com.example.yahtzeegame.model;

import java.util.List;

public class DiceAnalysis {

    public final List<Integer> remainingDice;
    public final Category category;
    public final int score;
    public final List<Integer> diceThatCanBeKept;


    public DiceAnalysis(List<Integer> remainingDice, Category category, int score, List<Integer> diceThatCanBeKept) {
        this.remainingDice = remainingDice;
        this.category = category;
        this.score = score;
        this.diceThatCanBeKept = diceThatCanBeKept;
    }

}
