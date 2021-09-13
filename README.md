# Meshkraft-Android
[![Version](https://img.shields.io/badge/Version-v0.0.1-orange)](https://cocoapods.org/pods/Meshkraft)
[![License](https://img.shields.io/cocoapods/l/Meshkraft.svg?style=flat)](https://cocoapods.org/pods/Meshkraft)
[![Platform](https://img.shields.io/badge/Platform-Android-green)](https://cocoapods.org/pods/Meshkraft)

ART Labs introduces tailor-made AR-commerce. A specially designed, effortless, 3D-powered boost for eCommerce.

## Installation
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
        implementation 'com.github.ARTLabs-Engineering.Meshkraft-Android:meshkraft:1.2.0'
	}
```

## Usage

1. Add The Following yo Your `Application``onCreate` method.
```Kotlin
Meshkraft.setApiKey("TOKEN")
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