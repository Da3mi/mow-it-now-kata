package com.daami.kata.mowitnow.port;

import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Position;

public abstract class ControlMower {
	protected Mower mower;

	public ControlMower(Mower mower) {
		this.mower = mower;
		this.initializePosition();
	};
	
	private void initializePosition() {
		if(this.mower.getPosition() == null) {
			this.mower = Mower.builder().position(new Position(0,0)).garden(this.mower.getGarden())
					.orientation(this.mower.getOrientation()).build();
		}
		
	}
	
	protected Position buildActualPosition() {
		return Position.builder().x(this.mower.getPosition().getX())
				.y(this.mower.getPosition().getY()).build();
	}
	
	

	public abstract Mower execute();

}
