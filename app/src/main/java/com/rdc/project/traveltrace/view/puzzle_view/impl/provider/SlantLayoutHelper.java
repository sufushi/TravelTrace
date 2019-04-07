package com.rdc.project.traveltrace.view.puzzle_view.impl.provider;

import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;
import com.rdc.project.traveltrace.view.puzzle_view.impl.slant.OneSlantLayout;
import com.rdc.project.traveltrace.view.puzzle_view.impl.slant.ThreeSlantLayout;
import com.rdc.project.traveltrace.view.puzzle_view.impl.slant.TwoSlantLayout;

import java.util.ArrayList;
import java.util.List;

public class SlantLayoutHelper {

    private SlantLayoutHelper() {

    }

    public static List<PuzzleLayout> getAllThemeLayout(int pieceCount) {
        List<PuzzleLayout> puzzleLayouts = new ArrayList<>();
        switch (pieceCount) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    puzzleLayouts.add(new OneSlantLayout(i));
                }
                break;
            case 2:
                for (int i = 0; i < 2; i++) {
                    puzzleLayouts.add(new TwoSlantLayout(i));
                }
                break;
            case 3:
                for (int i = 0; i < 6; i++) {
                    puzzleLayouts.add(new ThreeSlantLayout(i));
                }
                break;
        }

        return puzzleLayouts;
    }

}
