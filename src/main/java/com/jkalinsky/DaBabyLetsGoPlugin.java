package com.jkalinsky;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GraphicChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@PluginDescriptor(
	name = "DaBaby Let's Go",
	description = "Plays DaBaby 'Let's go!' sound when a specific boss does its burrow/charge attack",
	tags = {"sound", "audio", "boss", "dababy"}
)
public class DaBabyLetsGoPlugin extends Plugin
{
	private static final int BOSS_NPC_ID = 14709;
	private static final int ATTACK_ANIMATION_ID = 12417;
	private static final int POSE_ANIMATION_ID = 12421;
	private static final int ATTACK_GRAPHIC_ID = 3371;
	private static final long DEBOUNCE_TIME_MS = 1000; // 1 second debounce

	@Inject
	private Client client;

	@Inject
	private DaBabyLetsGoConfig config;

	private Clip audioClip;
	private long lastPlayTime = 0;

	@Override
	protected void startUp() throws Exception
	{
		loadAudioClip();
	}

	@Override
	protected void shutDown() throws Exception
	{
		if (audioClip != null)
		{
			audioClip.close();
		}
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		if (!config.enableSound())
		{
			return;
		}

		if (!(event.getActor() instanceof NPC))
		{
			return;
		}

		NPC npc = (NPC) event.getActor();
		
		if (npc.getId() == BOSS_NPC_ID)
		{
			int animation = npc.getAnimation();
			
			if (animation == ATTACK_ANIMATION_ID || animation == POSE_ANIMATION_ID)
			{
				playDaBabySound();
			}
		}
	}

	@Subscribe
	public void onGraphicChanged(GraphicChanged event)
	{
		if (!config.enableSound())
		{
			return;
		}

		if (!(event.getActor() instanceof NPC))
		{
			return;
		}

		NPC npc = (NPC) event.getActor();
		
		if (npc.getId() == BOSS_NPC_ID && npc.getGraphic() == ATTACK_GRAPHIC_ID)
		{
			playDaBabySound();
		}
	}

	private void loadAudioClip()
	{
		try (InputStream audioStream = getClass().getResourceAsStream("/dababy_lets_go.wav"))
		{
			if (audioStream == null)
			{
				log.error("Could not find dababy_lets_go.wav in resources");
				return;
			}

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
			audioClip = AudioSystem.getClip();
			audioClip.open(audioInputStream);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			log.error("Failed to load DaBaby audio clip", e);
		}
	}

	private void playDaBabySound()
	{
		long currentTime = System.currentTimeMillis();
		
		// Debounce check
		if (currentTime - lastPlayTime < DEBOUNCE_TIME_MS)
		{
			return;
		}
		
		lastPlayTime = currentTime;

		if (audioClip != null)
		{
			// Stop any currently playing instance
			if (audioClip.isRunning())
			{
				audioClip.stop();
			}
			
			// Reset to beginning and play
			audioClip.setFramePosition(0);
			audioClip.start();
			
			log.debug("Playing DaBaby 'Let's go!' sound");
		}
	}

	@Provides
	DaBabyLetsGoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DaBabyLetsGoConfig.class);
	}
}