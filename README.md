---
title: Android
---

[![](https://jitpack.io/v/ARTLabs-Engineering/Meshkraft-Android.svg)](https://jitpack.io/#ARTLabs-Engineering/Meshkraft-Android)
[![Platform](https://img.shields.io/badge/Platform-Android-green)](https://cocoapods.org/pods/Meshkraft)

artlabs introduces tailor-made AR-commerce. A specially designed, effortless, 3D-powered boost for eCommerce.
Meshkraft is the platform that enables brands to create and deploy 3D models of their products in AR.

## Installation

Make sure you add jitpack repository to your project level `build.gradle` file.

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the following dependency to your app level `build.gradle` file.

```gradle
dependencies {
        implementation 'com.github.ARTLabs-Engineering.Meshkraft-Android:meshkraft:1.4.1'
	}
```

## Usage

Following features written using Kotlin.
If you are using Java, you can use the same methods with the same parameters.

### Initialization

1. Import the library

```kotlin
import com.artlabs.meshkraft.Meshkraft
```

2. Set your API key

```kotlin
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
         Meshkraft.setApiKey("YOUR_API_KEY")
    }

}
```

:::caution

Make sure to replace `YOUR_API_KEY` with your API key.

:::



### AR Session

```kotlin

import com.artlabs.meshkraft.IMeshkraftState
import com.artlabs.meshkraft.Meshkraft
import com.artlabs.meshkraft.data.model.Mode


 Meshkraft.startArSession(
 context,
 "productSKU",
 mode, // not required. possible values are: PREFERRED_3D, ONLY_3D, AR_PREFERRED, AR_ONLY
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
)
```

or you can use a basic starter.

```kotlin
 Meshkraft.startBasicArSession(context, "productSKU")
```

### VTO Session

```kotlin

 Meshkraft.startVTOSession(
 context,
 "productSKU",
 )
```

### Java

1. Add The Following Application class.

```java
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

```java
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

or you can a use basic starter.

```java
    Meshkraft.INSTANCE.startBasicArSession(context,"productSKU");
```

#### VTO Session

```java
Meshkraft.INSTANCE.startVTOSession(
            context,
            "productSKU",
        );
```


### Availability API
You can check the availability of the Meshkraft service for specific SKUs by calling:

```kotlin
Meshkraft.checkAvailability(
    sku = "productSKU",
    token = null // Optional, uses configured API key if not provided
) { availability, errorMessage ->
    if (errorMessage != null) {
        println("Error: $errorMessage")
    } else {
        availability?.forEach { (sku, availability) ->
            println("SKU: $sku, AR: ${availability.ar}, VTO: ${availability.vto}")
        }
    }
}
```