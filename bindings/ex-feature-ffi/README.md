# `ex-feature-ffi`

**TODO**
- [ ] Check all the file paths are correct after refactoring

Rust code that can be used to generate Kotlin and Swift bindings for ex-feature-rust.

## Setting up Rust

### 1. Install Rust
See the [installation guide](https://www.rust-lang.org/tools/install).

### 2. Install `uniffi-bindgen`
`uniffi-bindgen` is used to generate the Kotlin and Swift bindings.

It's important that the version of `uniffi-bindgen` that you install globally matches the version of `uniffi` declared in this project.

Find the version of `uniffi` in `bindings/ex-feature-ffi/Cargo.toml` and install `uniffi-bindgen` using the following command:

```bash
cargo install uniffi_bindgen --version <uniffi-version>
```

### 3. Configure cross compilation
Configure Rust for [cross compilation](https://rust-lang.github.io/rustup/cross-compilation.html) to any target platforms you'll need.

```bash
# Android targets
rustup target add aarch64-linux-android # for most physical Android devices
rustup target add x86_64-linux-android # for Android emulator on PC
rustup target add i686-linux-android # for 32-bit Android emulators
rustup target add armv7-linux-androideabi # for older devices

# iOS targets
rustup target add aarch64-apple-ios
rustup target add aarch64-apple-ios-sim
rustup target add x86_64-apple-ios
```


## Building for Android

Ensure you have first completed the steps to [install and configure Rust](#setting-up-rust).

### 1. Install the Android SDK
The simplest way to do this is by [installing Android Studio](https://android-doc.github.io/sdk/installing/index.html?pkg=studio).

### 2. Install the Android NDK
Find the required NDK version by searching for `ndkVersion` in `platforms/android/library/build.gradle`.

Now install the NDK, the simplest way being to use the Android SDK manager from within Android Studio.

_Note: If you decide to install the NDK manually, you must still use the "side-by-side" structure (i.e. the NDK must be inside the Android SDK directory, in a path like `~/Android/Sdk/ndk/22.1.7171670`)._

_Note: at time of writing (2022-06-28) you needed to use android-ndk-r22b or
earlier, because later versions fail with an error like `ld: error: unable to find library -lgcc`.  See [rust-lang #85806](https://github.com/rust-lang/rust/pull/85806) for more._

### 3. Point Cargo to the NDK
Create a Cargo config at `bindings/ex-feature-ffi/.cargo/config.toml` to tell Cargo where to look for the NDK during cross compilation (more details in the [Cargo reference](https://doc.rust-lang.org/cargo/reference/config.html)).

Add any platforms that you need to target to this file, replacing instances of `NDK_HOME` with the location of your NDK (for example`/home/andy/Android/Sdk/ndk/22.1.7171670`).

```toml
# bindings/ex-feature-ffi/.cargo/config.toml

[target.aarch64-linux-android]
ar = "NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin/ar"
linker = "NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin/aarch64-linux-android30-clang"

[target.x86_64-linux-android]
ar = "NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin/ar"
linker = "NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin/x86_64-linux-android30-clang"
```

_Note: You may need to add additional targets, for example `i686` if you use a 32-bit emulator._

_Note: this file is ignored in the top-level `.gitignore` file, so it won't be checked in to Git._

### 4. Building

#### Building the Android library
The following command builds the whole library and deploys it to Maven local for local development.

```bash
# Ensure ANDROID_HOME is set correctly!
make android
```

#### Building the shared object

```bash
cd bindings/ex-feature-ffi
cargo build --target x86_64-linux-android --release

```

This will create:

```
../../target/x86_64-linux-android/release/libuniffi_ex_feature.so
```

To strip the libraries and make them smaller:

```bash
NDK_HOME/toolchains/x86_64-4.9/prebuilt/linux-x86_64/bin/x86_64-linux-android-strip \
    ../../target/x86_64-linux-android/debug/libuniffi_ex_feature.so
```

## Building for iOS

Ensure you have first completed the steps to [install and configure Rust](#setting-up-rust).

* Build shared object:

```bash
cd bindings/ffi
cargo build --release --target aarch64-apple-ios
cargo build --release --target aarch64-apple-ios-sim
cargo build --release --target x86_64-apple-ios

mkdir -p ../../target/ios-simulator
lipo -create \
  ../../target/x86_64-apple-ios/release/libex-feature_ffi.a \
  ../../target/aarch64-apple-ios-sim/release/libex-feature_ffi.a \
  -output ../../target/ios-simulator/libex-feature_ffi.a
```

* This will create static libraries for both iOS devices and simulators:

```
../../target/x86_64-apple-ios/debug/libex-feature_ffi.a
../../target/ios-simulator/libex-feature_ffi.a
```

* Generate the bindings inside given output dir:

⚠️ The installed version should always match the version used by Cargo, see
`Cargo.toml` content inside this directory to retrieve it and use
`cargo install uniffi_bindgen --version <VERSION_NUMBER>` if needed.

```bash
uniffi-bindgen \
    generate src/ex_feature.udl \
    --language swift \
    --config uniffi.toml \
    --out-dir MY_OUTPUT_DIR

cd MY_OUTPUT_DIR
mv *.h         headers/
mv *.modulemap headers/module.modulemap
mv *.swift     Sources/
```

Note: The project should always have a single Swift modulemap file, and it
should be named `module.modulemap` otherwise the generated framework will not
expose symbols properly to Swift.

* Generate the .xcframework file:

```bash
xcodebuild -create-xcframework \
  -library ../../target/aarch64-apple-ios/debug/libex_feature.a" \
  -headers MY_OUTPUT_DIR/headers \
  -library MY_OUTPUT_DIR/libex_feature.a" \
  -headers MY_OUTPUT_DIR/headers \
  -output MY_OUTPUT_DIR/ExampleRustBindings.xcframework"
```

Now you can use the framework wherever you see fit, just add the framework (as
well as the generated Swift code from `MY_OUTPUT_DIR/Sources/`) as a dependency
inside an existing XCode project, or package them with your preferred dependency
manager. See
[Example Rust Bindings](https://gitlab.com/andybalaam/example-rust-bindings/)
for a full example of how it all fits together.
