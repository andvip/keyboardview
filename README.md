Step 1. Add it in your root build.gradle at the end of repositories:
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
Step 2. Add the dependency
```java 
        dependencies {
	        compile 'com.github.andvip:keyboardview:1.0.1'
	}
  ```
使用方法
```java
final EditText editText = findViewById(R.id.edt);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardView keyboardView = new KeyboardView(MainActivity.this);
                keyboardView.setEditText(editText);
                keyboardView.show();
            }
        });
         ```
