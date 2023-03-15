# jMorseCoding
_A Java library for converting text into Morse code and playing it back_

## Prerequisites
 - Java JRE 8 or higher for base and all extensions ([Download the latest version here](https://www.oracle.com/java/technologies/downloads/))
 - [Extensions](https://github.com/Randall-Scharpf/jMorseCoding#list-of-extensions) may have their own prerequisites

# How do I...

## Know what version of Java I have?
 - Go to a terminal (cmd, bash, and shell are other names).
   - Details on how to do this depend on your system and platform; if you don't know how to do this, you'll have to look it up.
 - Type `java -version`
 - Read the output; it will either tell you your version or fail, which indicates you need to install Java (or fix your existing install).

## Use the library in my code?
 - Go to [Releases](https://github.com/Randall-Scharpf/jMorseCoding/releases).
 - If you are on Java 11 or higher...
   - download the latest version of `jMorseCoding-base-j11.jar`
 - If you are on Java 8 through 10...
   - download the latest version of `jMorseCoding-base-j8.jar`).
 - Download any [extensions](https://github.com/Randall-Scharpf/jMorseCoding#list-of-extensions) that you want to use with your code (select the correct java version)
 - Add the JAR files to the project classpath in your favorite development or build tool.
 - Take a look at the documentation at [randall-scharpf.github.io/jMorseCoding](https://randall-scharpf.github.io/jMorseCoding) to get started programming.
 - All source and binary versions of `jMorseCoding-base` are licenced under the [MIT Licence](https://opensource.org/licenses/MIT).

## Play Morse Code without writing any Java code?
 - Make sure you have Java installed. ([Download it here](https://www.oracle.com/java/technologies/downloads/))
 - Download the appropriate `jMorseCoding-gui.jar` from the releases page.
 - Download the appropriate `jMorseCoding-base.jar` from the releases page.
 - Download any [extensions](https://github.com/Randall-Scharpf/jMorseCoding#list-of-extensions) you want to try out.
 - Make sure all your downloaded files are in the same folder (they probably already are, but doesn't hurt to check).
 - Double-click `jMorseCoding-gui.jar` to run the program.
 
## Report a bug?
First, check [the repository issues page](https://github.com/Randall-Scharpf/jMorseCoding/issues) to see if someone else has already discovered the same bug.
If you find the issue, see if there's already a fix or if there's a fix in progress. If there is no fix, post a like on the issue ticket.
If you can't find a matching issue, post a new issue. Make sure to include...
 - Which extensions you have downloaded and where you have them downloaded
 - What platform you're using
 - Exactly what you were doing / trying to do when the bug occurred
   - If you don't provide enough detail to reproduce the bug, I can't know if I've fixed it
 - What you expected to happen
 - What happened instead
If you can generate an error log or paste terminal output that describes the bug into your issue ticket, it will be helpful in tracking down the source of the issue.
Finally, be ready for follow-ups to your bug report in the same issue thread.

## Contribute to this project...

### By fixing bugs?
First, look at the outstanding issues in the issues section.
Then, look at [the section on planned updates](https://github.com/Randall-Scharpf/jMorseCoding#what-you-can-expect-from-future-versions) below.
If you see something you want to fix, make a fork of the repository, commit your changes to it, and submit a pull request!
Make sure you've tested the code you're submitting (and show evidence of this in the pull request), that you use descriptive and grammar-appropriate variable and method names, and document any code that is complex or unintuitive. If your method contracts are complex or unintuitive, start by _rethinking them_ to see if you can make them simple, and only if it is impossible to make the contracts intuitive, then allow it to stay, but be extremely vigilant in documenting it.
Additionally, try to follow principles of good class design like interfacing and parametrization where appropriate, and minimal use of static fields.

### By adding documentation?
If you're interested in adding documentation to existing code (or if you're looking fo guidelines for documenting code that you're adding), make sure to include the following.
 - For methods
   - the version in which the method last changed, under an `@version` tag
   - the version in which the method contract last changed (typically the version in which the method was first added), under an `@since` tag
   - all parameters described briefly as nouns under `@param` tags
   - the return value described briefly as a noun under an `return` tags
   - a period-terminated first sentence that is a grammatical predicate describing what the method does as a verb
   - details of how the method behaves under edge-case parameters
   - if relevant, notes on the complexity of the implementation of the method
 - For fields
   - what the field contains, as a noun, and when / by what it should be needed
   - specifics of what the possible error states or edge case values mean (e.g, if you have an object field, what does `null` mean?)
   - the version in which the field contract last changed (typically the version in which the method was first added), under an `@since` tag
 - For classes
   - a brief description of the what the class is or does
   - a brief description of how to use the class
   - if relevant, notes on the complexity of the implementation of the class
   - the version in which the method last changed, under an `@version` tag
   - the version in which the method contract last changed (typically the version in which the method was first added), under an `@since` tag

### By improving something else?
If you want to fix something or add something, but it doesn't fall under the categories above, start by _searching the repository issues page_ and, only if you can't find a relevant one, create a new issue. It will be reviewed soon, and if I mark it as a desired fix, you can start working on it! Alterntively, if you think it's out of your abilities to make the fix, you can wait for someone who's looking for bugs (probably myself) to fix the issue.
Make sure to describe any changes desired by your issue clearly and unambiguously, including expected behavior in edge cases. If it is unclear what is the general intention of the entire issue request, it cannot be addressed, and if the specifics of the intention are unclear, they will havve to be clarified, or may even be misassumed by the implementing developer.
Finally, be ready for follow-ups to your request in the same issue thread.

### By submitting a pull request with my bug fixes or documentation?

If you are only submitting documentation,
 - Confirm that you have read and followed the guidelines above.
 - List each method, field, or class that you have documented so that it is easy for reviewers to check that your documentation is good to go.

If you are submitting code,
 - Try to provide the documentation to go with it. Any documentation you don't provide is work for someone else somewhere down the line.
 - Be sure to include test cases that demonstrate that your new feature works (in fact, you should demonstrate that it never doesn't work).
 - Make sure to state what classes, methods, and fields have been added, and note which are not yet documented.
 - Confirm that you've given anything you've added a descriptive name that matches the rest of the code base.
 - Note which paradigms are used in your code and make sure these paradigms are the same ones used throughout the rest of the code.

## Make my own extensions?
 - Add `jMorseCoding-base-VERSION.jar` to the classpath for your build. Use the version corresponding to the version supported by your build of the extension.

## Get my own extension listed below?
 - Submit a pull request on this file, adding the name of your extension, the site / repository where it is documented, a brief description, and which runtime dependencies or native dependencies your extension requires.
 - Your plugin will be briefly reviewed. THIS IS NOT A GUARANTEE THAT THE PLUGIN IS SAFE. DOWNLOADING AN EXTENSION REPRESENTS TRUST IN THE VENDOR OF THAT DEPENDENCY.
   - This does not imply that there is any warranty on any of the code in this repository.
   - If you discover a security issue in any of the code in this library, you are encouraged to submit an issue as soon as possible.
   - If you find that any third-party plugin below is unsafe, you are also encouraged to submit an issue as soon as possible.
 - Watch for responses on your pull request asking for clarifications... or accepting your extension onto the list!

# What You Can Expect from Future Versions
 - More complete documentation
 - Additional official extenions
 - More morse code standards
 - A completed GUI with which to try out the framework
 - Automated unit testing

# FAQ
_To ask a new question, create a pull request adding it. If it's a good question, expect your question to be merged in, with an answer!_
 - When is Java 17 support coming?  
 Soon! I'm waiting for the rest of the tools in my build environment to support it.
 - What licenses are used by this software? What does that mean?
 The base library is released under [The MIT License](https://choosealicense.com/licenses/mit/). Official extensions are likely to use the same or similar license, but you must check their documentation before making any assumptions. Third-party extensions might be under any license; their docs are the only place where you can learn about them.

# List of Extensions

## Official extensions
 - [Beeper](docs) — provides support for playing Morse Code as beeps through the Java Runtime Environment
 - [Beeper Mobile](docs) — provides support for playing Morse Code as beeps through the Android RunTime
 - [Blinker Mobile](docs) — provides support for playing Morse Code as blinks on a device flashlight through the Android RunTime

## Third-Party extensions
 - Nothing here yet... check back later!