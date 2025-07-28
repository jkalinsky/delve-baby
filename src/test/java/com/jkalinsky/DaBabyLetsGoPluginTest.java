package com.jkalinsky;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DaBabyLetsGoPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DaBabyLetsGoPlugin.class);
		RuneLite.main(args);
	}
}