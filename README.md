Check [our wiki](https://github.com/BetterPluginsSpigot/BetterYaml/wiki) for more info & all documentation!

Javadocs available [here](https://betterpluginsspigot.github.io/BetterYaml/)

# BetterYaml [![](https://jitpack.io/v/betterpluginsspigot/betteryaml.svg)](https://jitpack.io/#betterpluginsspigot/betteryaml)
Easily add/remove config options or contents without overriding the server's settings. 
This tool is written specifically for Spigot plugins but may be adapted to work in any environment, feel free to fork this repo.
We advise using a Maven project for easy dependency management.

## What can BetterYaml do?
BetterYaml uses a template file to handle your config files, this allows you to **change comments or config contents very easily** (adding or removing options) between plugin updates. It also supports fixing faulty configurations (eg. specify minimum/maximum values and never get values outside of this range).
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

## Using the library
Using BetterYaml is very simple once our project is set up. 
BetterYaml can be utilised in two different ways, through `BetterYaml` or `OptionalBetterYaml`. The former is our legacy approach and is left in to prevent breaking code when upgrading. And the latter is a new addition, which does not require catching Exceptions.

Our [wiki](https://github.com/BetterPluginsSpigot/BetterYaml/wiki) contains documentation on how to use this library and all its features.

## Limitations
There is no guaranteed support for lists or multi-line values, but nested keys are considered valid.
