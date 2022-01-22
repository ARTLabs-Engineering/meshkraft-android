# Meshkraft-Android
[![](https://jitpack.io/v/ARTLabs-Engineering/Meshkraft-Android.svg)](https://jitpack.io/#ARTLabs-Engineering/Meshkraft-Android)
[![Platform](https://img.shields.io/badge/Platform-Android-green)](https://cocoapods.org/pods/Meshkraft)

ART Labs introduces tailor-made AR-commerce. A specially designed, effortless, 3D-powered boost for eCommerce.

## Installation
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```gradle
dependencies {
        implementation 'com.github.ARTLabs-Engineering.Meshkraft-Android:meshkraft:1.2.0'
	}
```

## Usage

### Kotlin
1. Add The Following Application class.
```Kotlin
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
         Meshkraft.setApiKey("YOUR_API_KEY")
    }

}
```
Make sure to replace `YOUR_API_KEY` with your application token.


#### AR Session


```Kotlin
 Meshkraft.startArSession(
 context,
 "productSKU",
 mode, // not required
 listener=object:IMeshkraftState{
    override fun onLoading() {
        // on loading
    }

    override fun onFail(message: String) {
        // any fail on session
    }

    override fun onFinish() {
        // session done. started intent
    }
 }
```

or can use basic starter

```Kotlin
 Meshkraft.startBasicArSession(context, "productSKU")
```

### Java

1. Add The Following Application class.
```Java
public class MainActivity extends Activity  {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Meshkraft.INSTANCE.setApiKey("YOUR_API_KEY");
        }
}
```
Make sure to replace `YOUR_API_KEY` with your application token.


#### AR Session


```Java
Meshkraft.INSTANCE.startArSession(
                context,
                "productSKU",
                mode,// nullable
                new IMeshkraftState() {
                    @Override
                    public void onLoading() {
                        // Loading State
                    }

                    @Override
                    public void onFail(@NonNull String message) {
                        // onFail state
                    }

                    @Override
                    public void onFinish() {
                        // finish session
                    }
                }
        );
```

or can use basic starter

```Java
    Meshkraft.INSTANCE.startBasicArSession(context,"productSKU");
```