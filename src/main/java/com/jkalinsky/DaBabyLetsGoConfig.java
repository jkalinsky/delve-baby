package com.jkalinsky;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("dababyletsgo")
public interface DaBabyLetsGoConfig extends Config
{
	@ConfigItem(
		keyName = "enableSound",
		name = "Enable DaBaby Sound",
		description = "Enable playing the DaBaby 'Let's go!' sound effect"
	)
	default boolean enableSound()
	{
		return true;
	}
}