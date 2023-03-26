package com.daami.kata.mowitnow.mowitnow.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.daami.kata.mowitnow.core.AdvanceMower;
import com.daami.kata.mowitnow.core.TurnLeftMower;
import com.daami.kata.mowitnow.core.TurnRightMower;
import com.daami.kata.mowitnow.model.Garden;
import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Orientation;
import com.daami.kata.mowitnow.model.Position;
import com.daami.kata.mowitnow.port.ControlMower;

@TestMethodOrder(OrderAnnotation.class)
public class MowerControlTest {

	ControlMower mowerControl;

	@Test
	@Order(1)
	public void mower_should_move_forward() {
		Position gardenLimitPosition = Position.builder().x(4).y(4).build();
		Garden garden = Garden.builder().gardenLimitPosition(gardenLimitPosition).build();
		Position mowerPosition = Position.builder().x(1).y(2).build();
		Mower mower = Mower.builder().position(mowerPosition).garden(garden).orientation(Orientation.North).build();

		mowerControl = new AdvanceMower(mower);
		Mower result = mowerControl.execute();

		Position expectedPostion = Position.builder().x(1).y(3).build();

		assertEquals(expectedPostion, result.getPosition());
	}

	@Test
	@Order(2)
	public void mower_should_stop_when_garden_limit_reached_north() {
		Position gardenLimitPosition = Position.builder().x(4).y(4).build();
		Garden garden = Garden.builder().gardenLimitPosition(gardenLimitPosition).build();
		Position mowerPosition = Position.builder().x(1).y(4).build();
		Mower mower = Mower.builder().position(mowerPosition).garden(garden).orientation(Orientation.North).build();

		mowerControl = new AdvanceMower(mower);
		Mower result = mowerControl.execute();

		Position expectedPostion = Position.builder().x(1).y(4).build();

		assertEquals(expectedPostion, result.getPosition());
	}

	@Test
	@Order(3)
	public void mower_should_stop_when_garden_limit_reached_east() {
		Position gardenLimitPosition = Position.builder().x(4).y(4).build();
		Garden garden = Garden.builder().gardenLimitPosition(gardenLimitPosition).build();
		Position mowerPosition = Position.builder().x(4).y(1).build();
		Mower mower = Mower.builder().position(mowerPosition).garden(garden).orientation(Orientation.East).build();

		mowerControl = new AdvanceMower(mower);
		Mower result = mowerControl.execute();

		Position expectedPostion = Position.builder().x(4).y(1).build();

		assertEquals(expectedPostion, result.getPosition());
	}

	@Test
	@Order(4)
	public void mower_should_turn_left() {

		Mower mower = Mower.builder().orientation(Orientation.North).build();

		mowerControl = new TurnLeftMower(mower);
		Mower result = mowerControl.execute();

		Mower expectedMower = Mower.builder().position(new Position(0, 0)).orientation(Orientation.West).build();

		assertEquals(expectedMower, result);
	}

	@Test
	@Order(5)
	public void mower_should_turn_right() {

		Mower mower = Mower.builder().orientation(Orientation.North).build();

		mowerControl = new TurnRightMower(mower);
		Mower result = mowerControl.execute();

		Mower expectedMower = Mower.builder().position(new Position(0, 0)).orientation(Orientation.East).build();

		assertEquals(expectedMower, result);
	}

}
