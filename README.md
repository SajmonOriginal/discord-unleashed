![Discord Unleashed Banner](https://i.imgur.com/B1QXfMt.png)
# Discord Unleashed

**Discord Unleashed** is a mod for Minecraft Beta 1.7.3 that bridges the gap between your Minecraft server and Discord. This mod relays chat messages bidirectionally, announces player join/leave events, and even broadcasts server start/stop messages — all via Discord webhooks.


## Features

- **Bidirectional Chat Integration:**  
  - **Minecraft → Discord:** When players chat in-game (excluding commands), messages are relayed to a designated Discord channel.
  - **Discord → Minecraft:** Discord messages are broadcasted to in-game players (with configurable formatting).

- **Join/Leave Announcements:**  
  - Automatically sends an embedded message to Discord when a player joins or disconnects from the server.

- **Server State Notifications:**  
  - Notifies Discord when the server starts or stops, so your community always stays in the loop.

- **Customizable Webhook Settings:**  
  - Configure server avatars, names, and more through the mod’s configuration file.

- **Lightweight & Non-Intrusive:**  
  - Built with Fabric Loader and Mixin injections, Discord Unleashed is optimized for minimal impact on your server’s performance.

## Installation

1. **Prerequisites:**
   - Minecraft Beta 1.7.3 (with official mappings).
   - Fabric Loader for Beta 1.7.3 (Babric).
   - Java 8.
   - [Discord Bot/Webhook](https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks) credentials.

2. **Download:**
   - Grab the latest release [here](https://github.com/SajmonOriginal/discord-unleashed/releases).

3. **Setup:**
   - Place the mod JAR file into your `mods` folder.
   - Ensure that your Fabric configuration (`fabric.mod.json`) is set up with the correct mappings.

4. **Configuration:**
   - Edit `discord_unleashed.json` in your config directory.
   - Fill in the following fields with your own values:
     - `discord_enable`: Set to `true`.
     - `discord_token`: Your Discord bot token (if using JDA for additional features).
     - `discord_channel`: The ID of the channel where messages should appear.
     - `discord_webhook_url`: Your Discord webhook URL.
     - `discord_serverpfp_url`: (Optional) URL for the server’s profile picture.
     - `discord_servername`: The name to display for server messages.
   - Save the file and launch your server.

## Usage

- **Minecraft Chat to Discord:**  
  When players type in Minecraft (except commands starting with `/`), their messages are sent to your configured Discord channel.

- **Discord Chat to Minecraft:**  
  Messages sent from your configured Discord channel are broadcast into Minecraft server chat.

- **Join/Leave Announcements:**  
  Watch your Discord channel get real-time notifications whenever a player joins or leaves the server.

## Development

### Setting Up Your Environment

- **IDE Configuration:**  
  Import the project as a Gradle project. Make sure your IDE uses the correct Java version (Java 8) and includes the Fabric/Babric and relevant dependencies.

- **Mixin Debugging:**  
  Launch with `-Dmixin.debug.verbose=true` to see detailed Mixin logs if you need to troubleshoot.

### Contributing

Contributions are welcome! Feel free to fork the repository, open issues, or submit pull requests. When contributing, please ensure your code follows the existing style and is thoroughly tested.

## Known Issues

- **Mixin Injection Warnings:**  
  Some warnings about `Shift.BY` values may appear in the logs. These are generally safe to ignore or can be suppressed by increasing the `maxShiftBy` value in the Mixin config.
- **Mapping Differences:**  
  The mod uses obfuscated names from Minecraft Beta 1.7.3 official mappings. Compatibility may be affected if you update to a different version.

## License

This project is licensed under the [MIT License](LICENSE).

---

Happy modding, and let Discord Unleashed bring your Minecraft community even closer together!
