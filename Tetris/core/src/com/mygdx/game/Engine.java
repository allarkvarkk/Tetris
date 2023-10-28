package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Board.Board;
import com.mygdx.game.States.GameStates;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Engine extends Game {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	final public static int BOARD_WIDTH = 10, BOARD_HEIGHT = 20, SPACE_SIZE = 20;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		setScreen(new Board(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
