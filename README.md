[![](https://jitpack.io/v/followthemoney1/PasswordEditText.svg)](https://jitpack.io/#followthemoney1/PasswordEditText)

# The library is made from the [concept](https://www.uplabs.com/posts/password-strength-interaction) which is just an example of how this can be implemented. 

How it works:

<img src="https://github.com/followthemoney1/PasswordEditText/blob/master/ScreenRecording20190408at1%20(3).gif" data-canonical-src="https://github.com/followthemoney1/PasswordEditText/blob/master/ScreenRecording20190408at1%20(3).gif?raw=true?raw=true" width="400" height="290" />


<img src="https://github.com/followthemoney1/PasswordEditText/blob/master/Screen%20Recording%202019-04-08%20at%2012.26.40.gif" data-canonical-src="https://github.com/followthemoney1/PasswordEditText/blob/master/Screen%20Recording%202019-04-08%20at%2012.26.40.gif?raw=true" width="200" height="380" />




Example:
```
        <pc.dd.password_view.PasswordEditText
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/passwordEditText"
            app:showBottomTips="true"
            app:cardProgressColor="@color/green"/>
```
To use from code

``` passwordEditText.onTextChanged { t -> Log.e(javaClass.name, t) }```


## How to
**Step 1. Add the JitPack repository to your build file**

Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
**Step 2. Add the dependency**
```
dependencies {
	        implementation 'com.github.followthemoney1:PasswordEditText:{version}'
	}
```
