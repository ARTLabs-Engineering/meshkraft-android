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

Basic VTO session:
```kotlin
Meshkraft.startVTOSession(
    context,
    "productSKU"
)
```

#### VTO with Add to Cart Feature

To enable add to cart functionality, you need to:

1. **Set up the VTO delegate** to handle add to cart events:

```kotlin
import com.artlabs.meshkraft.MeshkraftVTODelegate
import com.artlabs.meshkraft.data.model.MeshkraftVTOConfig

class MainActivity : AppCompatActivity(), MeshkraftVTODelegate {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set the VTO delegate
        Meshkraft.vtoDelegate = this
    }
    
    // Handle add to cart events
    override fun vtoAddToCartClicked(productSKU: String) {
        // Called when user clicks "Add to Cart" in VTO
        // VTO session automatically closes after this callback
        showAddToCartConfirmation(productSKU)
    }
}
```

2. **Configure VTO with add to cart options**:

```kotlin
val vtoConfig = MeshkraftVTOConfig(
    showBanner = true,                    // Show add to cart button
    bannerButtonText = "Add to Cart",     // Custom button text
    accentColor = "#007AFF",              // Custom accent color
    useWatermark = false,                 // Hide/show watermark
    logoUrl = "https://your-logo.png",    // Custom logo URL
    fontFamily = "Arial",                 // Custom font
    disableUI = false                     // Enable/disable UI elements
)

Meshkraft.startVTOSession(
    context = this,
    sku = "productSKU",
    config = vtoConfig
)
```

#### VTO Configuration Options

| Parameter | Type | Description |
|-----------|------|-------------|
| `showBanner` | `Boolean?` | Whether to show the add to cart banner/button |
| `bannerButtonText` | `String?` | Custom text for the add to cart button |
| `accentColor` | `String?` | Accent color for the VTO interface (hex format, e.g., "#007AFF") |
| `useWatermark` | `Boolean?` | Whether to show watermark in the VTO experience |
| `logoUrl` | `String?` | URL for custom logo to display in the VTO interface |
| `fontFamily` | `String?` | Custom font family for the VTO interface |
| `disableUI` | `Boolean?` | Whether to disable the default VTO UI elements |

#### Important Notes

- The VTO session **automatically closes** when the user clicks "Add to Cart"
- The `vtoAddToCartClicked` callback is triggered on the main thread
- Set `showBanner = true` in the configuration to enable the add to cart button
- The delegate must be set before starting the VTO session
- All configuration parameters are optional and will use default values if not specified

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

Basic VTO session:
```java
Meshkraft.INSTANCE.startVTOSession(
    context,
    "productSKU"
);
```

#### VTO with Add to Cart Feature (Java)

1. **Implement the VTO delegate**:

```java
import com.artlabs.meshkraft.MeshkraftVTODelegate;
import com.artlabs.meshkraft.data.model.MeshkraftVTOConfig;

public class MainActivity extends AppCompatActivity implements MeshkraftVTODelegate {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Meshkraft.INSTANCE.setApiKey("YOUR_API_KEY");
        
        // Set the VTO delegate
        Meshkraft.INSTANCE.vtoDelegate = this;
    }
    
    @Override
    public void vtoAddToCartClicked(@NonNull String productSKU) {
        // Handle add to cart event
        // VTO session automatically closes after this callback
        runOnUiThread(() -> {
            showAddToCartConfirmation(productSKU);
        });
    }
}
```

2. **Configure VTO with add to cart options**:

```java
MeshkraftVTOConfig vtoConfig = new MeshkraftVTOConfig(
    null,                      // fontFamily
    null,                      // disableUI
    true,                      // showBanner
    "Add to Cart",             // bannerButtonText
    "https://your-logo.png",   // logoUrl
    false,                     // useWatermark
    "#007AFF"                  // accentColor
);

Meshkraft.INSTANCE.startVTOSession(
    this,
    "productSKU",
    vtoConfig
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