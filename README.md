# DaBaby Let's Go Plugin

A RuneLite plugin that plays a custom "DaBaby 'Let's go!'" sound effect when a specific boss performs its burrow/charge attack animation.

## Features

- Detects boss (NPC ID: 14709) attack animations
- Plays custom sound effect on burrow/charge attack
- Configurable enable/disable toggle
- Debounced playback to prevent rapid repeats

## Testing Instructions

### Local Development Testing

1. Close any RuneLite instances and IDEs that might have the build directory open

2. From the project directory, run:
   ```
   ./gradlew build
   ```

3. Launch the RuneLite client with the plugin:
   ```
   ./gradlew runClient
   ```

4. In the RuneLite client:
   - Navigate to the Settings (wrench icon)
   - Search for "DaBaby" in the plugins list
   - Enable the plugin
   - Ensure "Enable DaBaby Sound" is checked

5. To test the sound effect:
   - Find the boss with NPC ID 14709
   - Wait for it to perform its burrow/charge attack (Animation ID: 12417)
   - The "DaBaby Let's go!" sound should play

### Developer Console Testing

If you have access to RuneLite's developer tools:
- Use `::anim 12417` to test the animation trigger
- Use developer tools to spawn NPC 14709 for testing

## Configuration

The plugin provides a simple configuration option:
- **Enable DaBaby Sound**: Toggle the sound effect on/off

## Technical Details

- Triggers on Animation IDs: 12417 (attack), 12421 (pose)
- Also triggers on Graphic ID: 3371
- 1-second debounce prevents rapid repeats
- Audio file: `dababy_lets_go.wav` (included in resources)

## Build Issues

If you encounter build directory lock issues on Windows:
1. Close all RuneLite instances
2. Close any IDEs (IntelliJ, Eclipse, etc.)
3. Run `./gradlew --stop`
4. Manually delete the `build` directory if needed
5. Try building again