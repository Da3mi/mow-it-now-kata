package com.daami.kata.mowitnow.port;

import java.util.List;

import com.daami.kata.mowitnow.model.Mower;

public interface ProcessMowers {
	
	public List<Mower> processDeployedMowers(String programFilePath);

}
