package model;

import java.io.Serializable;

public record Record(String nickname, long score) implements Serializable {
    @Override
    public String toString() {
        return nickname + ": " + score;
    }
}