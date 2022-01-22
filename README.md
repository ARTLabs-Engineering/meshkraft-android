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
        implementation 'com.github.ARTLabs-Engineering.Meshkraft-Android:meshkraft:1.3.0'
	}
```

## Usage

### Kotlin
1. Add The Following Application class.
```Kotlin
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Mashkraft.setApiKey("TOKEN_HERE")
    }

}
```
Make sure to replace `YOUR_API_KEY` with your application token.


#### AR Session

```Kotlin
Meshkraft.startArSession(context,"PRODUCT_SKU")
```


```Kotlin
 MeshKraft.apply {
                onFail={message ->
                    // product fail state
                }
                onFinish={
                    // start activity state
                }
                onLoading={
                    // activity on loading
                }
            }
```

#### Device Support

You can check if AR is supported on the device:

```Kotlin
Meshkraft.isARSupported(context)
```

### Java
 !coming soon...