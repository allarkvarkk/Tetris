package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Board.Board;
import com.mygdx.game.Board.BoardManager;
import com.mygdx.game.Board.Piece.Piece;
import com.mygdx.game.Player.PlayerInputHandler;
import com.mygdx.game.States.GameStates;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Engine extends Game {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public Board board;
	//public Piece piece;
	final public static int BOARD_WIDTH = 10, BOARD_HEIGHT = 20, SPACE_SIZE = 20;



	//final public static int SCREEN_HEIGHT = Gdx.graphics.getHeight(), SCREEN_WIDTH = Gdx.graphics.getWidth();

	@Override
	public void create () {
		batch = new SpriteBatch();
		PlayerInputHandler inputHandler = new PlayerInputHandler();
		Gdx.input.setInputProcessor(inputHandler);
		shapeRenderer = new ShapeRenderer();
		board = new Board(this);
		BoardManager.setBoard(board);
		setScreen(board);
	}

	@Override
	public void render () {
		super.render();
	}
}