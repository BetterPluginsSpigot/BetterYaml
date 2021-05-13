# BetterYaml
Easily add/remove config options or contents without overriding the server's settings. 
This tool is written specifically for Spigot plugins but may be adapted to work in any environment, feel free to fork this repo.
We advise using a Maven project for easy dependency management.

## What can BetterYaml do?
BetterYaml uses a template file to handle your config files, this allows you to **change comments or config contents very easily** (adding or removing options) between plugin updates.
A very important detail in this process is that _**all server settings will remain intact**_!
The best part of this that this is all handled **automatically**! You only need to provide the correct files and make an instance of BetterYaml. After this, you can retrieve a `YamlConfiguration` and do your regular config reading.
It will make sure that the server's config file is **always up to date**.

Let's say an earlier version of your software has the following YAML config:
<details>
<summary>Your current default file</summary>

```
# Your plugin name

# Link to Spigot page
# Some explanation

# This option decides whether or not to give free hot dogs
free_pizza: true

custom_name: "Steve"
```
</details>

Imagine a user has changed free_pizza to false on their server and they removed the first few lines of comments.
<details>
<summary>User's changed config</summary>

```
# This option decides whether or not to give free hot dogs
free_pizza: false

custom_name: "Steve"
```
</details>

However, after release you notice a mistake in your comments (you mentioned 'free hot dogs' instead of pizza), you want to remove the custom_name option as it is no longer supported and you want to introduce a new option max_free_pizzas.
Usually this would be a hassle, but with BetterYaml you can easily change your template without altering the server's settings. Simply update the template and default values and you're good to go.
After changing the right files and releasing a new update, your users will get the correct config file right away.
This is the new default file you have in mind:
<details>
<summary>Your new default file</summary>

```
# Your plugin name

# Link to Spigot page
# Some explanation

max_free_pizzas: 3

# This option decides whether or not to give free pizza
free_pizza: true
```
</details>

After installing your update, the user's config will look as follows:
<details>
<summary>User's config after your update</summary>

```
# Your plugin name

# Link to Spigot page
# Some explanation

max_free_pizzas: 3

# This option decides whether or not to give free pizza
free_pizza: false
```
</details>


## Setup BetterYaml to work with your project
Add the following to your pom:
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
<dependencies>
	<dependency>
	    <groupId>com.github.BetterPluginsSpigot</groupId>
	    <artifactId>BetterYaml</artifactId>
	    <version>1.0.2</version>
	</dependency>
</dependencies>
```
Preferably, use the latest version to get all features. Check [BetterYaml Packages](https://github.com/orgs/BetterPluginsSpigot/packages?repo_name=BetterYaml) to find which is the latest version.

For each config file, we need to create two files in our resources folder.
Let's assume we only have one config file; but working with multiple config files is supported.
Our config file is called `ourConfig.yml`.
```
| - .git/
| - src/
|    | - <redacted>
| - resources
|    | - ourConfig.yml
|    | - templates/
|         | - ourConfig.yml
| - pom.xml
```
In `resources/templates/ourConfig.yml` we create our template, this file contains all comments and options with placeholder values. Check the config file below for an explanation.
This is the file where you will edit what the server owner's config looks like.
<details>
<summary>resources/templates/ourConfig.yml</summary>

```
# This option is not using a placeholder, so this setting will be reverted everytime your plugin loads
# That means that if the user changes this, its changes will be undone
version: "3.1.4"

# The {} braces indicate a placeholder. If no default value is specified in resources/ourConfig.yml, the placeholder will not be replaced
max_free_pizzas: {max_free_pizzas}

# This placeholder will not be specified in the next section
free_pizza: {free_pizza}

# It is good practice to wrap placeholders that will be replaced by a String with ""
string_option: "{string_option}"
```
</details>

We also need to specify the default values in `resources/ourConfig.yml`:
<details>
<summary>resources/ourConfig.yml</summary>

```
# Comments in this file do not matter, it only serves as a storage for your default values
max_free_pizzas: 17
string_option: "This is a String!"
```
</details>

If your user now uses your plugin for the first time, the copied config file will look like the one below.
In case the user makes any configuration change, it will be maintained and that change will **not** be overwritten.
When a user edits, adds or removes comments,  they **will** revert to the default values.
<details>
<summary>Plugin config on server: ourConfig.yml</summary>

```
# This option is not using a placeholder, so this setting will be reverted everytime your plugin loads
# That means that if the user changes this, its changes will be undone
version: "3.1.4"

# The {} braces indicate a placeholder. If no default value is specified in resources/ourConfig.yml, the placeholder will not be replaced
max_free_pizzas: 17

# This placeholder will not be specified in the next section
free_pizza: {free_pizza}

# It is good practice to wrap placeholders that will be replaced by a String with ""
string_option: "This is a String!"
```
</details>

## Using the library
Using BetterYaml is very simple once our project is set up. 
BetterYaml can be utilised in two different ways, through `BetterYaml` or `OptionalBetterYaml`. The former is our legacy approach and is left in to prevent breaking code when upgrading. And the latter is a new addition, which does not require catching Exceptions.

### OptionalBetterYaml
When using the `OptionalBetterYaml` class to handle our files:
```
// Auto-updates the config on the server and loads a YamlConfiguration and File
OptionalBetterYaml ourConfig = new OptionalBetterYaml("ourConfig.yml", javaPlugin);

// Get the Optional container 
Optional<YamlConfiguration> loadResult = ourConfig.getYamlConfiguration();
// Check if the configuration was loaded correctly
boolean isReadingSuccess = loadResult.isPresent();  // True if it was loaded correctly, false if an Exception occurred. This is the other way around for Optional#isEmpty() 
// Get a YamlConfiguration to do your regular config reading. ONLY do this if `loadResult.isPresent()` returns true!
YamlConfiguration yaml = loadResult.get();

// Not enough? You can also get a File instance and handle it the same way we handled the YamlConfiguration
Optional<File> file = ourConfig.getFile();
```

### BetterYaml
When using the `BetterYaml` class to handle our files:
An `IOException` is thrown when your setup contains errors.
We are assuming that javaPlugin is an instance of your class that extends `JavaPlugin`. In an instance of that class, you can also pass `this` instead.
```
// Auto-updates the config on the server and loads a YamlConfiguration and File
BetterYaml ourConfig = new BetterYaml("ourConfig.yml", javaPlugin);

// Get a YamlConfiguration to do your regular config reading
YamlConfiguration yaml = ourConfig.getYamlConfiguration();
// Not enough? You can also get a File instance
File file = ourConfig.getFile();
```

## Limitations
There is no guaranteed support for lists or multi-line values, but nested keys are considered valid.
Does not work for Minecraft versions prior to 1.16.

