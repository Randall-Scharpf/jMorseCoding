# Beeper
## _Makes beeps!_
<br>

## Setup
 - Download the appropriate version of `jMorseCoding-base` for your Java installation.
 - Download the matching version of `jMorseCoding-beeper` and place it in the same folder.
 - Add both JAR files to the classpath of your project.

## Features
 - Pitch control: a `DoubleSupplier` provides live control over the frequency of beeping, in Hz.
 - Volume control: a `DoubleSupplier` provides live control over the volume of beeping, on a 0-100 scale.
 - Waveform control: a separate `Supplier` allows the tone of each discrete beep to be determined on-the-fly. Supports:
   - Triangle wave
   - Square wave
   - Sawtooth wave
   - Sine wave
 - Anti-pop: when a beep is starting or stopping, the waveform generation algorithm prevents popping sounds.
 - Works with `jMorseCoding-gui`:
   - Distributed JAR contains required manifest attributes
   - Audio resources are handled with `Openable`.

## Example Usage
```java
Beeper b = new Beeper(); // 440 Hz, 100% volume, triangle wave
MorsePlayer p = new MorsePlayer(b, b, BuiltinMorseStandard.ITU_R_M1677_1_2009);
b.open();
p.playMorseFromString("Hello World");
b.close();
```
```java
Beeper b = new Beeper(() -> 523.25 /* C5 */, () -> 50 /* % volume */, () -> WaveType.SINE);
MorsePlayer p = new MorsePlayer(b, b, BuiltinMorseStandard.ITU_R_M1677_1_2009);
b.open();
p.playMorseFromString("Hello World");
b.close();
```
```java
Beeper b = new Beeper(() -> 440 /* A4 */, new DoubleSupplier() {
    int x = 0;
    @Override public double getAsDouble() {
        return 1.0e-3*(++x) /* crescendo */;
    }
}, () -> WaveType.SAWTOOTH);
MorsePlayer p = new MorsePlayer(b, b, BuiltinMorseStandard.ITU_R_M1677_1_2009);
b.open();
p.playProsign("starting signal");
b.close();
```
Note that, for brevity, exception handling and imports are not included in these examples.