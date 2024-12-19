package com.game.entity;

import com.game.constants.Direction;

import java.awt.image.BufferedImage;

public class Entity {
    protected int x, y;
    protected int speed;

    protected BufferedImage up_1, up_2, down_1, down_2, left_1, left_2, right_1, right_2;
    protected Direction direction;

    protected int spiritCounter = 0;
    protected int spiritNum = 1;
}
