# BubbleImageView

## Introduction
*BubbleImageView* was created to provide a simple way to crop an image to show an "arrow" in one side of it.
Below you can see the possibilities
![Screenshot](https://raw.githubusercontent.com/AgnaldoNP/BubbleImageView/master/screenshot/screenshot.png)

## Install

**Step 1**. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
**Step 2.** Add the dependency
```
dependencies {
  implementation 'com.github.AgnaldoNP:BubbleImageView:1.5'
}
```
[![](https://jitpack.io/v/AgnaldoNP/BubbleImageView.svg)](https://jitpack.io/#AgnaldoNP/BubbleImageView)


## Usage

Sample of usage
```xml
<pereira.agnaldo.library.BubbleImageView
  android:layout_width="0dp"
  android:layout_height="70dp"
  android:layout_marginBottom="10dp"
  android:layout_marginEnd="15dp"
  android:layout_marginRight="15dp"
  android:layout_weight="1"
  android:scaleType="centerCrop"
  android:src="@drawable/music"
  app:arrowAnchor="top"
  app:arrowDirection="left"
  app:arrowSize="12dp"
  app:baseArrowSize="12dp"
  app:round="4dp"/>
```
### Options
| Property       | Values                           | Default |
|----------------|----------------------------------|---------|
| arrowAnchor    | right\|left\|top\|bottom\|middle | bottom  |
| arrowDirection | right\|left\|top\|bottom\|middle | left    |
| arrowVisible   | true\|false                      | true    |
| baseArrowSize  | dimension value                  | 2.5dp   |
| arrowSize      | dimension value                  | 5dp     |
| round          | dimension value                  | 4dp     |


## Contributions and Support
Contributions are welcome. Create a new pull request in order to submit your fixes and they shall be merged after moderation. In case of any issues, bugs or any suggestions, either create a new issue or post comments in already active relevant issues

## Please consider supporting me
Bitcoin URI: bitcoin:BC1Q4RT2KNSX28CA4H5YA08VF0SXMG3JPHKS6GWDXV?label=Consider%20support%20me

Bitcoin Address: bc1q4rt2knsx28ca4h5ya08vf0sxmg3jphks6gwdxv
