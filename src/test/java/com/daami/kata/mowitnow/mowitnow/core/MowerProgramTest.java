package com.daami.kata.mowitnow.mowitnow.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.daami.kata.mowitnow.core.MowerProgramProcessor;
import com.daami.kata.mowitnow.core.exception.BusinessLogicException;
import com.daami.kata.mowitnow.model.Garden;
import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Orientation;
import com.daami.kata.mowitnow.model.Position;
import com.daami.kata.mowitnow.port.ProcessMowers;
import com.google.common.collect.Lists;

public class MowerProgramTest {

	String absoluteTestFilePath;
	String absoluteBadTestFilePath;
	
	
	Position postion1 = Position.builder().x(1).y(3).build();
	Position postion2 = Position.builder().x(5).y(1).build();
	Position gardenLimitPosition = Position.builder().x(5).y(5).build();
	
	Garden garden = Garden.builder().gardenLimitPosition(gardenLimitPosition).build();

	{
		String testFilePath = "/testFile.txt";
		String badTestFilePath = "/badTestFile.txt";
		Path resourceDirectory = Paths.get("src", "test", "resources");
		absoluteTestFilePath = resourceDirectory.toFile().getAbsolutePath() + testFilePath;
		absoluteBadTestFilePath = resourceDirectory.toFile().getAbsolutePath() + badTestFilePath;
	}

	ProcessMowers mowerProgram = new MowerProgramProcessor();

	@Test
	public void mowers_should_execute_program_file() {
		List<Mower> mowers = mowerProgram.processDeployedMowers(absoluteTestFilePath);

		List<Mower> expectedList = Lists.newArrayList(
				Mower.builder().position(postion1).orientation(Orientation.North).build(),
				Mower.builder().position(postion2).orientation(Orientation.East).build());

		assertEquals(expectedList, mowers);
	}

	@Test
	public void mowers_should_throw_exception_when_program_file_not_exist() {
		Exception exception = assertThrows(BusinessLogicException.class, () -> {
			mowerProgram.processDeployedMowers("NotExisitingFile.txt");
		});
		
		String expectedMessage = "Failed to read file: IOException";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}
