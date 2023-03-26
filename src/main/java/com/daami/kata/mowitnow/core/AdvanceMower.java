package com.daami.kata.mowitnow.core;

import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Position;
import com.daami.kata.mowitnow.port.ControlMower;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdvanceMower extends ControlMower {

	public AdvanceMower(Mower mower) {
		super(mower);
	}

	@Override
	public Mower execute() {
		log.info("The mower is moving forwad: {}", this.mower.getOrientation());
		switch (this.mower.getOrientation()) {
		case North:
			if (this.mower.getPosition().getY() >= mower.getGarden().getGardenLimitPosition().getY()) {
				this.logStoppedMower();
				return this.mower;
			}
			return this.buildAdvancedMower(this.mower.getPosition().getX(), this.mower.getPosition().getY() + 1);

		case South:
			return this.buildAdvancedMower(this.mower.getPosition().getX(), this.mower.getPosition().getY() - 1);
		case East:
			if (this.mower.getPosition().getX() >= mower.getGarden().getGardenLimitPosition().getX()) {
				this.logStoppedMower();
				return this.mower;
			}
			return this.buildAdvancedMower(this.mower.getPosition().getX() + 1, this.mower.getPosition().getY());
		case West:
			return this.buildAdvancedMower(this.mower.getPosition().getX() - 1, this.mower.getPosition().getY());
		default:
			break;
		}

		log.info("The mower did not moved");

		return this.mower;
	}

	private void logStoppedMower() {
		log.info("The limit of the garden is reached to the north, mower is stopped ({} {} {})",
				this.mower.getPosition().getX(), this.mower.getPosition().getY(),
				this.mower.getOrientation().getOrientationCode());
	}

	private Mower buildAdvancedMower(int x, int y) {
		return Mower.builder().position(Position.builder().x(x).y(y).build()).orientation(this.mower.getOrientation())
				.garden(this.mower.getGarden())
				.build();
	}

}
