package org.fransis.game.words;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements LevelRepository {

    private List<Level> levels = null;
    private int level;

    public MemoryRepository (){
        level = 0;
        levels = new ArrayList<>();
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Chucrut".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Amor".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Auto".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Perro".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Musica".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Pajaro".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Expensa".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Paisaje".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Televisor".toUpperCase()));
        levels.add(new Level(Level.DEFAULT_ALPHABET, "Matematica".toUpperCase()));

    }

    @Override
    public Level getLevel() {
        return levels.get(level);
    }

    @Override
    public Level getNextLevel() {
        Level level = levels.get(++this.level);
        if(this.level == levels.size())
            this.level = 0;
        return level;
    }
}
